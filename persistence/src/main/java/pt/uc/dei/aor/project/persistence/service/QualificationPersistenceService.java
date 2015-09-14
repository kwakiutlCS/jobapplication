package pt.uc.dei.aor.project.persistence.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import pt.uc.dei.aor.project.business.filter.PositionFilter;
import pt.uc.dei.aor.project.business.model.IAnswerChoice;
import pt.uc.dei.aor.project.business.model.IDegree;
import pt.uc.dei.aor.project.business.model.IPosition;
import pt.uc.dei.aor.project.business.model.ISchool;
import pt.uc.dei.aor.project.business.persistence.IAnswerChoicePersistenceService;
import pt.uc.dei.aor.project.business.persistence.IQualificationPersistenceService;
import pt.uc.dei.aor.project.business.util.Localization;
import pt.uc.dei.aor.project.business.util.PositionState;
import pt.uc.dei.aor.project.business.util.TechnicalArea;
import pt.uc.dei.aor.project.persistence.entity.AnswerChoiceEntity;
import pt.uc.dei.aor.project.persistence.entity.DegreeEntity;
import pt.uc.dei.aor.project.persistence.entity.InstitutionEntity;
import pt.uc.dei.aor.project.persistence.entity.PositionEntity;
import pt.uc.dei.aor.project.persistence.proxy.AnswerChoiceProxy;
import pt.uc.dei.aor.project.persistence.proxy.DegreeProxy;
import pt.uc.dei.aor.project.persistence.proxy.PositionProxy;
import pt.uc.dei.aor.project.persistence.proxy.SchoolProxy;

@Stateless
public class QualificationPersistenceService implements IQualificationPersistenceService {
	
	@PersistenceContext(unitName = "jobs")
    private EntityManager em;

	@Override
	public ISchool addSchool(ISchool school) {
		InstitutionEntity entity = GenericPersistenceService.getEntity(school);
		
		entity = em.merge(entity);
		
		return new SchoolProxy(entity);
	}

	@Override
	public IDegree addDegree(IDegree degree) {
		DegreeEntity entity = GenericPersistenceService.getEntity(degree);
		
		entity = em.merge(entity);
		
		return new DegreeProxy(entity);
	}

	@Override
	public long countSchools() {
		TypedQuery<Long> query = em.createNamedQuery("Institution.countSchools", Long.class);
		
		List<Long> result = query.getResultList();
		if (result.isEmpty()) return 0L;
		
		return result.get(0);
	}

	@Override
	public ISchool findSchoolByName(String name) {
		TypedQuery<InstitutionEntity> query = em.createNamedQuery("Institution.findSchoolByName", 
				InstitutionEntity.class);
		
		query.setParameter("name", name);
		
		List<InstitutionEntity> result = query.getResultList();
		if (result.isEmpty()) return null;
		
		return new SchoolProxy(result.get(0));
	}

	@Override
	public List<ISchool> filterSchoolsByName(String text) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<InstitutionEntity> q = cb.createQuery(InstitutionEntity.class);
		Root<InstitutionEntity> school = q.from(InstitutionEntity.class);
		q.select(school);
		
		Predicate predicate = null;
		
		if (text != null) {
			text = text.toLowerCase();
			String[] fields = text.split(" ");
			
			Expression<String> name = cb.lower(school.get("name"));
			Expression<String> normalized = cb.lower(school.get("normalized"));
			
			for (String s : fields) {
				if (s.length() <= 2) continue;
				if (predicate == null) {
					predicate = cb.or(cb.like(name, "% "+s+"%"), cb.like(name, s+"%"));
					predicate = cb.or(predicate, 
							cb.or(cb.like(normalized, "% "+s+"%"), cb.like(normalized, s+"%")));
				}
				else {
					Expression<Boolean> addExpr = cb.or(cb.like(name, "% "+s+"%"), cb.like(name, s+"%"));
					addExpr = cb.or(addExpr, 
							cb.or(cb.like(normalized, "% "+s+"%"), cb.like(normalized, s+"%")));
					predicate = cb.and(predicate, addExpr);
				}
			}
		}
		
		if (predicate != null)
			q.where(predicate);
			
		TypedQuery<InstitutionEntity> query = em.createQuery(q);
		
		List<InstitutionEntity> entities = query.getResultList();
		List<ISchool> proxies = new ArrayList<>();
		
		for (InstitutionEntity ie : entities) {
			proxies.add(new SchoolProxy(ie));
		}
		
		return proxies;
	}

	@Override
	public List<IDegree> findDegreeBySchool(ISchool proxy) {
		TypedQuery<DegreeEntity> query = em.createNamedQuery("Degree.findDegreesBySchool", 
				DegreeEntity.class);
		query.setParameter("school", GenericPersistenceService.getEntity(proxy));
		
		List<DegreeEntity> entities = query.getResultList();
		
		List<IDegree> proxies = new ArrayList<>();
		
		for (DegreeEntity de : entities) {
			proxies.add(new DegreeProxy(de));
		}
		
		return proxies;
	}
	
}
