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
import pt.uc.dei.aor.project.business.model.IPosition;
import pt.uc.dei.aor.project.business.model.IProposition;
import pt.uc.dei.aor.project.business.model.IUser;
import pt.uc.dei.aor.project.business.persistence.IApplicationPersistenceService;
import pt.uc.dei.aor.project.business.util.PositionState;
import pt.uc.dei.aor.project.persistence.entity.ApplicationEntity;
import pt.uc.dei.aor.project.persistence.entity.PositionEntity;
import pt.uc.dei.aor.project.persistence.entity.UserEntity;
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
	public List<IApplication> findApplicationsWithFilter(ApplicationFilter filter, int offset, int limit, IUser manager) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<ApplicationEntity> q = cb.createQuery(ApplicationEntity.class);
		Root<ApplicationEntity> application = q.from(ApplicationEntity.class);
		q.select(application);
		
		List<Predicate> criteriaPredicates = new ArrayList<>();
		
		if (manager != null) {
			Root<PositionEntity> position = q.from(PositionEntity.class);
			Predicate predicate = cb.equal(position.get("id"), application.get("position"));
			UserEntity w = GenericPersistenceService.getEntity(manager);
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
				Root<UserEntity> candidate = q.from(UserEntity.class);
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
			
			
			// spontaneous filter
			Boolean spontanenousFilter = filter.getSpontaneous();
			if (spontanenousFilter != null) {
				if (spontanenousFilter.booleanValue())
					criteriaPredicates.add(cb.isNull(application.get("position")));
				else criteriaPredicates.add(cb.isNotNull(application.get("position")));
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

	public long findApplicationsByPosition(IPosition p) {
		TypedQuery<Long> query = em.createNamedQuery("Application.numberCandidatesByPosition", Long.class);
		query.setParameter("position", GenericPersistenceService.getEntity(p));
		
		List<Long> result = query.getResultList();
		
		if (result.isEmpty()) return 0;
		return result.get(0);
	}
		

	public boolean findApplicationbyCandidateAndPosition(IUser candidate,
			IPosition position) {
		
		PositionEntity positionEntity = GenericPersistenceService.getEntity(position);
		UserEntity candidateEntity = GenericPersistenceService.getEntity(candidate);
		
		TypedQuery<ApplicationEntity> q = em.createNamedQuery("application.findApplicationbyCandidateAndPosition", ApplicationEntity.class);
		
		q.setParameter("position", positionEntity);
		q.setParameter("candidate", candidateEntity);
		
		List<ApplicationEntity> applied = q.getResultList();
		
		boolean result = false;
		
		if (applied.size()>0)
			result= true;
		
		return result;
	}


	@Override
	public List<IApplication> findAllApplicationsByPosition(IPosition position) {
		TypedQuery<ApplicationEntity> query = em.createNamedQuery("Application.findApplicationsByPosition", 
				ApplicationEntity.class);
		query.setParameter("position", GenericPersistenceService.getEntity(position));
		
		List<ApplicationEntity> result = query.getResultList();
		List<IApplication> proxies = new ArrayList<>();
		
		for (ApplicationEntity ae : result) {
			proxies.add(new ApplicationProxy(ae));
		}
		
		return proxies;
	}


	@Override
	public boolean hasSpontaneous(IUser user) {
		TypedQuery<ApplicationEntity> query = em.createNamedQuery("Application.findSpontaneous", 
				ApplicationEntity.class);
		query.setParameter("user", GenericPersistenceService.getEntity(user));
		
		List<ApplicationEntity>	entity = query.getResultList();
		return !entity.isEmpty();
	}


	@Override
	public List<IApplication> findApplicationsByDate(Date startDate, Date finishDate) {
		TypedQuery<ApplicationEntity> query = em.createNamedQuery("Application.findApplicationsByDate", 
				ApplicationEntity.class);
		query.setParameter("startDate", startDate);
		query.setParameter("finishDate", finishDate);
		
		List<ApplicationEntity> result = query.getResultList();
		List<IApplication> proxies = new ArrayList<>();
		
		for (ApplicationEntity ae : result) {
			proxies.add(new ApplicationProxy(ae));
		}
		
		return proxies;
	}
	
	@Override
	public List<IApplication> findAllApplicationsByCandidate(IUser candidate) {
		
		TypedQuery<ApplicationEntity> q = em.createNamedQuery("Application.findApplicationsByCandidate", 
				ApplicationEntity.class);
		q.setParameter("candidate", GenericPersistenceService.getEntity(candidate));
		
		List<ApplicationEntity> result = q.getResultList();
		List<IApplication> proxies = new ArrayList<>();
		
		for (ApplicationEntity ae : result) {
			proxies.add(new ApplicationProxy(ae));
		}
		
		return proxies;
	}


	@Override
	public IApplication sendProposition(IApplication application, IProposition proposition) {
		ApplicationEntity entity = em.find(ApplicationEntity.class, application.getId());
		
		entity.setProposition(GenericPersistenceService.getEntity(proposition));
		entity = em.merge(entity);
		return new ApplicationProxy(entity);
	}
	
	
}
