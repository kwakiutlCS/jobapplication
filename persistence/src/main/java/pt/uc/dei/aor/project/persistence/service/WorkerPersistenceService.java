package pt.uc.dei.aor.project.persistence.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import pt.uc.dei.aor.project.business.model.IInterview;
import pt.uc.dei.aor.project.business.model.IWorker;
import pt.uc.dei.aor.project.business.model.IWorkerNotification;
import pt.uc.dei.aor.project.business.persistence.IWorkerPersistenceService;
import pt.uc.dei.aor.project.persistence.entity.InterviewEntity;
import pt.uc.dei.aor.project.persistence.entity.WorkerEntity;
import pt.uc.dei.aor.project.persistence.proxy.IProxyToEntity;
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
	public IWorker verifyUser(IWorker user, String password) {
		TypedQuery<WorkerEntity> query = em.createNamedQuery("Worker.verifyWorker", WorkerEntity.class);
		query.setParameter("login", user.getLogin());
		query.setParameter("password", password);
		
		List<WorkerEntity> entities = query.getResultList();
		if (entities.isEmpty()) return null;
		
		return new WorkerProxy(entities.get(0));
	}

		

}
