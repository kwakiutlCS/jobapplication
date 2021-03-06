package pt.uc.dei.aor.project.persistence.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import pt.uc.dei.aor.project.business.model.IApplication;
import pt.uc.dei.aor.project.business.persistence.IReportPersistenceService;
import pt.uc.dei.aor.project.business.util.PositionState;
import pt.uc.dei.aor.project.persistence.entity.ApplicationEntity;
import pt.uc.dei.aor.project.persistence.entity.PositionEntity;
import pt.uc.dei.aor.project.persistence.proxy.ApplicationProxy;

@Stateless
public class ReportPersistenceService implements IReportPersistenceService {
	
	@PersistenceContext(unitName = "jobs")
    private EntityManager em;

	
	@Override
	public long generatePeriodicAppReport(Date startDate, Date finishDate) {
		TypedQuery<Long> query = em.createNamedQuery("Application.numberCandidatesByPeriod", Long.class);
		query.setParameter("startDate", startDate);
		query.setParameter("finishDate", finishDate);
		
		List<Long> result = query.getResultList();
		
		if (result.isEmpty()) return 0;
		return result.get(0);
	}


	@Override
	public long generateSpontaneousAppReport(Date startDate, Date finishDate) {
		TypedQuery<Long> query = em.createNamedQuery("Application.numberSpontaneousByPeriod", Long.class);
		query.setParameter("startDate", startDate);
		query.setParameter("finishDate", finishDate);
		
		List<Long> result = query.getResultList();
		
		if (result.isEmpty()) return 0;
		return result.get(0);
	}


	@Override
	public List<IApplication> findAllCloseApplicationsByDate(Date startDate) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<ApplicationEntity> q = cb.createQuery(ApplicationEntity.class);
		Root<ApplicationEntity> application = q.from(ApplicationEntity.class);
		q.select(application);
		
		// date
		Expression<Date> dateExpression = application.get("date");
		Predicate criteriaPredicate = cb.greaterThanOrEqualTo(dateExpression, startDate);
		
		// closed
		Root<PositionEntity> position = q.from(PositionEntity.class);
		Predicate positionPredicate = cb.and(cb.equal(application.get("position"), 
			position.get("id")), cb.equal(position.get("state"), 
					PositionState.CLOSED)); 
		positionPredicate = cb.or(positionPredicate, cb.isNull(application.get("position")));
		
		criteriaPredicate = cb.and(criteriaPredicate, positionPredicate);
		
		// refused
		criteriaPredicate = cb.and(criteriaPredicate, cb.equal(application.get("refused"), true));
		
		q.where(criteriaPredicate);
		
		
		TypedQuery<ApplicationEntity> query = em.createQuery(q);
		
		List<ApplicationEntity> entities = query.getResultList();
		Set<ApplicationEntity> set = new HashSet<>(entities);
		List<IApplication> proxies = new ArrayList<>();
		
		for (ApplicationEntity ie : set) {
			proxies.add(new ApplicationProxy(ie));
		}
		
		return proxies;
	}


	@Override
	public List<IApplication> findRejectedByDate(Date startDate) {
		TypedQuery<ApplicationEntity> query = em.createNamedQuery("Application.findRejectedByDate",
				ApplicationEntity.class);
		query.setParameter("startDate", startDate);
		
		List<ApplicationEntity> entities = query.getResultList();
		
		List<IApplication> proxies = new ArrayList<>();
		
		for (ApplicationEntity ae : entities) {
			proxies.add(new ApplicationProxy(ae));
		}
		
		return proxies;
	}

	


}
