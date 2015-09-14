package pt.uc.dei.aor.project.persistence.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import pt.uc.dei.aor.project.business.model.IAnswerChoice;
import pt.uc.dei.aor.project.business.model.IDegree;
import pt.uc.dei.aor.project.business.model.ISchool;
import pt.uc.dei.aor.project.business.persistence.IAnswerChoicePersistenceService;
import pt.uc.dei.aor.project.business.persistence.IQualificationPersistenceService;
import pt.uc.dei.aor.project.persistence.entity.AnswerChoiceEntity;
import pt.uc.dei.aor.project.persistence.entity.DegreeEntity;
import pt.uc.dei.aor.project.persistence.entity.InstitutionEntity;
import pt.uc.dei.aor.project.persistence.proxy.AnswerChoiceProxy;
import pt.uc.dei.aor.project.persistence.proxy.DegreeProxy;
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
	
	
	
	
}
