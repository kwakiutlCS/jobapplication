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

import pt.uc.dei.aor.project.business.filter.ApplicationFilter;
import pt.uc.dei.aor.project.business.model.IApplication;
import pt.uc.dei.aor.project.business.model.IWorker;
import pt.uc.dei.aor.project.business.persistence.IApplicationPersistenceService;
import pt.uc.dei.aor.project.business.util.Role;
import pt.uc.dei.aor.project.persistence.entity.ApplicationEntity;
import pt.uc.dei.aor.project.persistence.entity.WorkerEntity;
import pt.uc.dei.aor.project.persistence.proxy.ApplicationProxy;
import pt.uc.dei.aor.project.persistence.proxy.IProxyToEntity;
import pt.uc.dei.aor.project.persistence.proxy.WorkerProxy;

@Stateless
public class ApplicationPersistenceService implements IApplicationPersistenceService {
	
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
	public List<IApplication> findApplicationsWithFilter(ApplicationFilter filter, int offset, int limit) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<ApplicationEntity> q = cb.createQuery(ApplicationEntity.class);
		Root<ApplicationEntity> application = q.from(ApplicationEntity.class);
		q.select(application);
		
		List<Predicate> criteriaPredicates = new ArrayList<>();
		
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
	
	
}
