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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.uc.dei.aor.project.business.filter.ApplicationFilter;
import pt.uc.dei.aor.project.business.model.IApplication;
import pt.uc.dei.aor.project.business.model.ICandidate;
import pt.uc.dei.aor.project.business.model.IPosition;
import pt.uc.dei.aor.project.business.model.IWorker;
import pt.uc.dei.aor.project.business.persistence.IApplicationPersistenceService;
import pt.uc.dei.aor.project.business.util.PositionState;
import pt.uc.dei.aor.project.persistence.entity.ApplicationEntity;
import pt.uc.dei.aor.project.persistence.entity.CandidateEntity;
import pt.uc.dei.aor.project.persistence.entity.PositionEntity;
import pt.uc.dei.aor.project.persistence.entity.WorkerEntity;
import pt.uc.dei.aor.project.persistence.proxy.ApplicationProxy;

@Stateless
public class ApplicationPersistenceService implements IApplicationPersistenceService {
	
	private static final Logger logger = LoggerFactory.getLogger(ApplicationPersistenceService.class);
	
	@PersistenceContext(unitName = "jobs")
    private EntityManager em;

	

	@Override
	public IApplication save(IApplication application) {
		ApplicationEntity entity = GenericPersistenceService.getEntity(application);
		
		entity = em.merge(entity);
		
		return new ApplicationProxy(entity);
	}
	
	
	@Override
	public IApplication find(long id) {
		ApplicationEntity entity = em.find(ApplicationEntity.class, id);
		return new ApplicationProxy(entity);
	}


	@Override
	public List<IApplication> findApplicationsWithFilter(ApplicationFilter filter, int offset, int limit, IWorker manager) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<ApplicationEntity> q = cb.createQuery(ApplicationEntity.class);
		Root<ApplicationEntity> application = q.from(ApplicationEntity.class);
		q.select(application);
		
		List<Predicate> criteriaPredicates = new ArrayList<>();
		
		if (manager != null) {
			Root<PositionEntity> position = q.from(PositionEntity.class);
			Predicate predicate = cb.equal(position.get("id"), application.get("position"));
			WorkerEntity w = GenericPersistenceService.getEntity(manager);
			predicate = cb.and(predicate, cb.equal(position.get("contactPerson"), w));
			criteriaPredicates.add(predicate);
		}
		
		
		// start where
		if (filter != null) {
			
			// dates filter
			Expression<Date> date = application.get("date");
			
			// start date
			Date startFilter = filter.getStartDate();
			if (startFilter != null) {
				Predicate startPredicate = cb.greaterThanOrEqualTo(date, startFilter);
									
				criteriaPredicates.add(startPredicate);
			}
			
			// finish date
			Date finishFilter = filter.getFinishDate();
			if (finishFilter != null) {
				Predicate finishPredicate = cb.lessThanOrEqualTo(date, finishFilter);
									
				criteriaPredicates.add(finishPredicate);
			}
			
			// code filter
			String codeFilter = filter.getCode();
			if (codeFilter != null) {
				try {
					long longCode = Long.parseLong(codeFilter);
					Root<PositionEntity> position = q.from(PositionEntity.class);
					Predicate codePredicate = cb.equal(position.get("id"), application.get("position"));
					codePredicate = cb.and(codePredicate, cb.equal(position.get("code"), longCode));

					criteriaPredicates.add(codePredicate);
				}
				catch (Exception e) {
					// wrong code - nothing to do
					logger.error("Wrong code in filter");
				}
				
			}
			
			// candidate filter
			String candidateFilter = filter.getCandidate();
			if (candidateFilter != null) {
				Root<CandidateEntity> candidate = q.from(CandidateEntity.class);
				Predicate candidatePredicate = cb.equal(candidate.get("id"), application.get("candidate"));
				candidatePredicate = cb.and(candidatePredicate, cb.like(
						cb.lower(candidate.get("completeName")), "%"+candidateFilter.toLowerCase()+"%"));
				
				criteriaPredicates.add(candidatePredicate);
			}
			
			// state filter
			PositionState stateFilter = filter.getState();
			if (stateFilter != null) {
				Root<PositionEntity> position = q.from(PositionEntity.class);
				Predicate statePredicate = cb.equal(position.get("id"), application.get("position"));
				statePredicate = cb.and(statePredicate, cb.equal(position.get("state"), stateFilter));
				
				criteriaPredicates.add(statePredicate);
			}
			
			// type filter
			String typeFilter = filter.getType();
			if (typeFilter != null) {
				if (typeFilter.equals("Internal")) {
					criteriaPredicates.add(cb.isNull(application.get("candidate")));
				}
				else {
					criteriaPredicates.add(cb.isNull(application.get("internalCandidate")));
				}
			}
		}
		
		q.where(cb.and(criteriaPredicates.toArray(new Predicate[0])));
		// finish where
		
		TypedQuery<ApplicationEntity> query = em.createQuery(q);
		
		query.setFirstResult(offset);
		query.setMaxResults(limit);
		
		List<ApplicationEntity> entities = query.getResultList();
		List<IApplication> proxies = new ArrayList<>();
		
		for (ApplicationEntity ie : entities) {
			proxies.add(new ApplicationProxy(ie));
		}
		
		return proxies;
	}


	@Override
	public boolean findApplicationbyCandidateAndPosition(ICandidate candidate,
			IPosition position) {
		
		PositionEntity positionEntity = GenericPersistenceService.getEntity(position);
		CandidateEntity candidateEntity = GenericPersistenceService.getEntity(candidate);
		
		TypedQuery<ApplicationEntity> q = em.createNamedQuery("application.findApplicationbyCandidateAndPosition", ApplicationEntity.class);
		
		q.setParameter("position", positionEntity);
		q.setParameter("candidate", candidateEntity);
		
		List<ApplicationEntity> applied = q.getResultList();
		
		boolean result = false;
		
		if (applied.size()>0)
			result= true;
		
		return result;
	}
	
	
}
