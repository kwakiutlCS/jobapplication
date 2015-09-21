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
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import pt.uc.dei.aor.project.business.filter.InterviewFilter;
import pt.uc.dei.aor.project.business.model.IApplication;
import pt.uc.dei.aor.project.business.model.ICandidate;
import pt.uc.dei.aor.project.business.model.IInterview;
import pt.uc.dei.aor.project.business.model.IWorker;
import pt.uc.dei.aor.project.business.persistence.IInterviewPersistenceService;
import pt.uc.dei.aor.project.persistence.entity.ApplicationEntity;
import pt.uc.dei.aor.project.persistence.entity.CandidateEntity;
import pt.uc.dei.aor.project.persistence.entity.InterviewEntity;
import pt.uc.dei.aor.project.persistence.entity.PositionEntity;
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
	public List<IInterview> findInterviewsWithFilter(int offset, int limit, InterviewFilter filter) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<InterviewEntity> q = cb.createQuery(InterviewEntity.class);
		Root<InterviewEntity> interview = q.from(InterviewEntity.class);
		q.select(interview);
		
		List<Predicate> criteriaPredicates = new ArrayList<>();
		// start where
		if (filter != null) {
			
			// interviewers
			List<List<IWorker>> interviewerSets = filter.getInterviewerSets();
			if (interviewerSets.size() > 0) {
				Expression<List<WorkerEntity>> interviewers = interview.get("interviewers");
				criteriaPredicates.add(GenericPersistenceService
						.andOrEntityPredicate(interviewerSets, interviewers, cb));
			}
			
			// position
			if (!filter.getPositions().isEmpty()) {
				Root<ApplicationEntity> application = q.from(ApplicationEntity.class);
				Root<PositionEntity> position = q.from(PositionEntity.class);
				criteriaPredicates.add(cb.equal(interview.get("application"), application.get("id")));
				criteriaPredicates.add(cb.equal(application.get("position"), position.get("id")));
				
				criteriaPredicates.add(GenericPersistenceService.orStringPredicate(filter.getPositions(),
						position.get("title"), cb));
			}
			
			// candidate 
			if (filter.getCandidate() != null) {
				Root<CandidateEntity> candidate = q.from(CandidateEntity.class);
				Root<ApplicationEntity> application = q.from(ApplicationEntity.class);
				criteriaPredicates.add(cb.equal(interview.get("application"), application.get("id")));
				criteriaPredicates.add(cb.equal(application.get("candidate"), candidate.get("id")));
				criteriaPredicates.add(cb.like(cb.lower(candidate.get("completeName")),
						"%"+filter.getCandidate().toLowerCase()+"%"));
			}
			
			// dates filter
			Expression<Date> date = interview.get("date");
			
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
	public List<IInterview> findPastInterviewsByUser(ICandidate candidate, IApplication application, Date date) {
		List<ApplicationEntity> appEntities =  
				em.find(CandidateEntity.class, candidate.getId()).getApplications();
		
		long appId = application.getId();
		
		
		for (ApplicationEntity ae : appEntities) {
			long aeId = ae.getId();
			if (ae.getId() == application.getId()) {
				appEntities.remove(ae);
				break;
			}
		}
		
		List<IInterview> proxies = new ArrayList<>();
		if (appEntities.isEmpty()) return proxies;
		
		TypedQuery<InterviewEntity> query = em.createNamedQuery("Interview.findPastInterviewsByCandidate",
				InterviewEntity.class);
		
		query.setParameter("date", date);
		query.setParameter("applications", appEntities);
		
		List<InterviewEntity> entities = query.getResultList();
		
		
		for (InterviewEntity ie : entities) {
			proxies.add(new InterviewProxy(ie));
		}
		
		return proxies;
	}

}
