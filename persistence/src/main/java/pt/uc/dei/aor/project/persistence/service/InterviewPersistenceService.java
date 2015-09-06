package pt.uc.dei.aor.project.persistence.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import pt.uc.dei.aor.project.business.filter.InterviewFilter;
import pt.uc.dei.aor.project.business.model.IInterview;
import pt.uc.dei.aor.project.business.model.IWorker;
import pt.uc.dei.aor.project.business.persistence.IInterviewPersistenceService;
import pt.uc.dei.aor.project.persistence.entity.ApplicationEntity;
import pt.uc.dei.aor.project.persistence.entity.InterviewEntity;
import pt.uc.dei.aor.project.persistence.entity.WorkerEntity;
import pt.uc.dei.aor.project.persistence.proxy.InterviewProxy;

@Stateless
public class InterviewPersistenceService implements IInterviewPersistenceService {
	
	@PersistenceContext(unitName = "jobs")
    private EntityManager em;
	
	
	@Override
	public IInterview save(IInterview interview) {
		InterviewEntity entity = GenericPersistenceService.getEntity(interview);
		entity = em.merge(entity);
		
		return new InterviewProxy(entity);
	}


	@Override
	public void delete(IInterview interview) {
		InterviewEntity entity = GenericPersistenceService.getEntity(interview);
		em.remove(em.merge(entity));
	}


	@Override
	public List<IInterview> findAllInterviews() {
		TypedQuery<InterviewEntity> query = em.createNamedQuery("Interview.findAllInterviews", 
				InterviewEntity.class);
		
		List<InterviewEntity> entities = query.getResultList();
		List<IInterview> proxies = new ArrayList<>();
		
		for (InterviewEntity ie : entities) {
			proxies.add(new InterviewProxy(ie));
		}
		
		return proxies;
	}

	@Override
	public List<IInterview> findActiveInterviewsByUser(IWorker user) {
		TypedQuery<InterviewEntity> query = em.createNamedQuery("Interview.findActiveInterviewsByInterviewer", 
				InterviewEntity.class);
		
		WorkerEntity userEntity = GenericPersistenceService.getEntity(user);
		
		query.setParameter("user", userEntity);
		query.setParameter("date", new Date());
		
		List<InterviewEntity> entities = query.getResultList();
		List<IInterview> proxies = new ArrayList<>();
		
		for (InterviewEntity ie : entities) {
			proxies.add(new InterviewProxy(ie));
		}
		
		return proxies;
	}


	@Override
	public IInterview findInterviewById(long id) {
		InterviewEntity entity = em.find(InterviewEntity.class, id);
		if (entity == null) return null;
		
		return new InterviewProxy(entity);
	}


	@Override
	public IInterview findInterview(IInterview interview) {
		TypedQuery<InterviewEntity> query = em.createNamedQuery("Interview.findInterviewByDateAndApplication",
				InterviewEntity.class);
		query.setParameter("date", interview.getDateObject());
		
		ApplicationEntity applicationEntity = GenericPersistenceService.getEntity(interview.getApplication());
		query.setParameter("application", applicationEntity);
		
		List<InterviewEntity> entities = query.getResultList();
		
		if (entities.isEmpty()) return null;
		
		return new InterviewProxy(entities.get(0));

	}


	@Override
	public List<IInterview> findInterviews(int offset, int limit) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<InterviewEntity> q = cb.createQuery(InterviewEntity.class);
		Root<InterviewEntity> root = q.from(InterviewEntity.class);
		q.select(root);
		
		TypedQuery<InterviewEntity> query = em.createQuery(q);
		query.setFirstResult(offset);
		query.setMaxResults(limit);
		
		List<InterviewEntity> entities = query.getResultList();
		List<IInterview> proxies = new ArrayList<>();
		
		for (InterviewEntity ie : entities) {
			proxies.add(new InterviewProxy(ie));
		}
		
		return proxies;
	}


	@Override
	public List<IInterview> findInterviewsWithFilter(int offset, int limit, InterviewFilter filter) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<InterviewEntity> q = cb.createQuery(InterviewEntity.class);
		Root<InterviewEntity> root = q.from(InterviewEntity.class);
		q.select(root);
		
		// start where
		List<List<IWorker>> interviewerSets = filter.getInterviewerSets();
		if (interviewerSets.size() > 0) {
			Expression<Collection<WorkerEntity>> interviewers = root.get("interviewers");

			Predicate[] predicateOr = new Predicate[interviewerSets.size()];
			int counterOr = 0;

			for (List<IWorker> workers : interviewerSets) {
				Predicate[] predicateAnd = new Predicate[workers.size()];
				int counterAnd = 0;

				for (IWorker w : workers) {
					WorkerEntity interviewerEntity = GenericPersistenceService.getEntity(w);
					predicateAnd[counterAnd++] = cb.isMember(interviewerEntity, interviewers);
				}

				predicateOr[counterOr++] = cb.and(predicateAnd);
			}

			q.where(cb.or(predicateOr));
		}
		// finish where
		
		TypedQuery<InterviewEntity> query = em.createQuery(q);
		
		query.setFirstResult(offset);
		query.setMaxResults(limit);
		
		List<InterviewEntity> entities = query.getResultList();
		List<IInterview> proxies = new ArrayList<>();
		
		for (InterviewEntity ie : entities) {
			proxies.add(new InterviewProxy(ie));
		}
		
		return proxies;
	}

}
