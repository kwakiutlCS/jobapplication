package pt.uc.dei.aor.project.persistence.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import pt.uc.dei.aor.project.business.filter.WorkerFilter;
import pt.uc.dei.aor.project.business.model.IInterview;
import pt.uc.dei.aor.project.business.model.IWorker;
import pt.uc.dei.aor.project.business.model.IWorkerNotification;
import pt.uc.dei.aor.project.business.persistence.IWorkerPersistenceService;
import pt.uc.dei.aor.project.business.util.Role;
import pt.uc.dei.aor.project.persistence.entity.ApplicationEntity;
import pt.uc.dei.aor.project.persistence.entity.CandidateEntity;
import pt.uc.dei.aor.project.persistence.entity.InterviewEntity;
import pt.uc.dei.aor.project.persistence.entity.PositionEntity;
import pt.uc.dei.aor.project.persistence.entity.WorkerEntity;
import pt.uc.dei.aor.project.persistence.proxy.IProxyToEntity;
import pt.uc.dei.aor.project.persistence.proxy.InterviewProxy;
import pt.uc.dei.aor.project.persistence.proxy.WorkerProxy;

@Stateless
public class WorkerPersistenceService implements IWorkerPersistenceService {
	
	@PersistenceContext(unitName = "jobs")
    private EntityManager em;
	
	@Override
	public IWorker save(IWorker worker) {
		WorkerEntity entity = GenericPersistenceService.getEntity(worker);
		
		entity = em.merge(entity);
		
		return new WorkerProxy(entity);
	}
	
	@Override
	public void delete(IWorker worker) {
		WorkerEntity entity = GenericPersistenceService.getEntity(worker); 
		
		em.remove(em.merge(entity));
	}

	
	@Override
	public IWorker getWorkerByLogin(String login) {
		TypedQuery<WorkerEntity> query = em.createNamedQuery("Worker.findWorkerByLogin", WorkerEntity.class);
		query.setParameter("login", login);
		
		List<WorkerEntity> entities = query.getResultList();
		
		if (!entities.isEmpty()) {
			return new WorkerProxy(entities.get(0));
		}
		
		return null;
	}
	

	@Override
	public IWorker getWorkerByEmail(String email) {
		TypedQuery<WorkerEntity> query = em.createNamedQuery("Worker.findWorkerByEmail", WorkerEntity.class);
		query.setParameter("email", email);
		
		List<WorkerEntity> entities = query.getResultList();
		
		if (!entities.isEmpty()) {
			return new WorkerProxy(entities.get(0));
		}
		
		return null;
	}
	
	@Override
	public List<IWorker> findAllUsers() {
		TypedQuery<WorkerEntity> query = em.createNamedQuery("Worker.findAllWorkers", WorkerEntity.class);
		
		List<WorkerEntity> entities = query.getResultList();
		List<IWorker> proxies = new ArrayList<>();
		
		for (WorkerEntity we : entities) {
			if (!we.getEmail().equals("SU"))
				proxies.add(new WorkerProxy(we));
		}
		
		return proxies;
	}

	@Override
	public Collection<IWorker> findAllAdmins() {
		TypedQuery<WorkerEntity> query = em.createNamedQuery("Worker.findAllAdmins", WorkerEntity.class);
		
		List<WorkerEntity> entities = query.getResultList();
		List<IWorker> proxies = new ArrayList<>();
		
		for (WorkerEntity we : entities) {
			proxies.add(new WorkerProxy(we));
		}
		
		return proxies;
	}

	@Override
	public Collection<IWorker> findAllManagers() {
		TypedQuery<WorkerEntity> query = em.createNamedQuery("Worker.findAllManagers", WorkerEntity.class);
		
		List<WorkerEntity> entities = query.getResultList();
		List<IWorker> proxies = new ArrayList<>();
		
		for (WorkerEntity we : entities) {
			proxies.add(new WorkerProxy(we));
		}
		
		return proxies;
	}

	@Override
	public Collection<IWorker> findAllInterviewers() {
		TypedQuery<WorkerEntity> query = em.createNamedQuery("Worker.findAllInterviewers", WorkerEntity.class);
		
		List<WorkerEntity> entities = query.getResultList();
		List<IWorker> proxies = new ArrayList<>();
		
		for (WorkerEntity we : entities) {
			proxies.add(new WorkerProxy(we));
		}
		
		return proxies;
	}

	
	@Override
	public void insertInterview(long worker_id, IInterview interview) {
		WorkerEntity entity = em.find(WorkerEntity.class, worker_id);
		entity.addInterview(GenericPersistenceService.getEntity(interview));
		em.merge(entity);
	}
	
	@Override
	public boolean findWorkerByEmailOrLogin(String email, String login) {
		TypedQuery<WorkerEntity> q = em.createNamedQuery("Worker.findWorkerByEmailOrLogin", WorkerEntity.class);
		q.setParameter("email", email);
		q.setParameter("login", login);
		
		return q.getResultList().size() > 0;
	}
			

	@Override
	public IWorker createSuperUser() {
		WorkerEntity worker = em.find(WorkerEntity.class, 0L);
		if (worker != null) {
			return null;
		}
		
		Query query = em.createNamedQuery("Worker.createSuperUser");
		query.executeUpdate();
		query = em.createNamedQuery("Worker.createSuperUserRole");
		query.executeUpdate();
		
		return getWorkerByLogin("SU");
	}

	@Override
	public void removeInterview(long worker_id, long interview_id) {
		WorkerEntity workerEntity = em.find(WorkerEntity.class, worker_id);
		InterviewEntity interviewEntity = em.find(InterviewEntity.class, interview_id);
		
		workerEntity.removeInterview(interviewEntity);
		
		em.merge(workerEntity);
	}

	@Override
	public IWorker findWorkerByEmail(String email) {
		TypedQuery<WorkerEntity> query = em.createNamedQuery("Worker.findWorkerByEmail", WorkerEntity.class);
		query.setParameter("email", email);
		
		List<WorkerEntity> entities = query.getResultList();
		if (entities.isEmpty()) return null;
		
		return new WorkerProxy(entities.get(0));
	}

	@Override
	public IWorker verifyUser(long id, String password) {
		TypedQuery<WorkerEntity> query = em.createNamedQuery("Worker.verifyWorker", WorkerEntity.class);
		query.setParameter("id", id);
		query.setParameter("password", password);
		
		List<WorkerEntity> entities = query.getResultList();
		if (entities.isEmpty()) return null;
		
		return new WorkerProxy(entities.get(0));
	}

	@Override
	public List<IWorker> findUsersWithFilter(WorkerFilter filter, int offset, int limit) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<WorkerEntity> q = cb.createQuery(WorkerEntity.class);
		Root<WorkerEntity> worker = q.from(WorkerEntity.class);
		q.select(worker);
		
		List<Predicate> criteriaPredicates = new ArrayList<>();
		criteriaPredicates.add(cb.notLike(worker.get("name"), "SU"));
		
		// start where
		if (filter != null) {
			
			// workers
			String workerFilter = filter.getKeyword();
			if (workerFilter != null) {
				workerFilter = workerFilter.toLowerCase();
				
				Expression<String> name = worker.get("completeName");
				Predicate or = cb.like(cb.lower(name), "%"+workerFilter+"%");
				
				Expression<String> email = worker.get("email");
				or = cb.or(or, cb.like(cb.lower(email), "%"+workerFilter+"%"));
				
				Expression<List<Role>> roles = worker.get("roles");
				if (workerFilter.equals("admin") || workerFilter.equals("administrator"))
					or = cb.or(or, cb.isMember(Role.ADMIN, roles));
				else if (workerFilter.equals("manager"))
					or = cb.or(or, cb.isMember(Role.MANAGER, roles));
				else if (workerFilter.equals("interviewer"))
					or = cb.or(or, cb.isMember(Role.INTERVIEWER, roles));
					
				criteriaPredicates.add(or);
			}
			
			// roles
			List<List<Role>> roleFilter = filter.getRoleSets();
			if (roleFilter != null && !roleFilter.isEmpty()) {
				Expression<List<Role>> roles = worker.get("roles");
				criteriaPredicates.add(GenericPersistenceService.andOrPredicate(roleFilter, roles, cb));
			}
		}
		
		q.where(cb.and(criteriaPredicates.toArray(new Predicate[0])));
		// finish where
		
		TypedQuery<WorkerEntity> query = em.createQuery(q);
		
		query.setFirstResult(offset);
		query.setMaxResults(limit);
		
		List<WorkerEntity> entities = query.getResultList();
		List<IWorker> proxies = new ArrayList<>();
		
		for (WorkerEntity ie : entities) {
			proxies.add(new WorkerProxy(ie));
		}
		
		return proxies;
	}

}
