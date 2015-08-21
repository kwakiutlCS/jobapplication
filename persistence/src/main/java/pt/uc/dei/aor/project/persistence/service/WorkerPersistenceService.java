package pt.uc.dei.aor.project.persistence.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import pt.uc.dei.aor.project.business.model.IWorker;
import pt.uc.dei.aor.project.business.persistence.IWorkerPersistenceService;
import pt.uc.dei.aor.project.persistence.entity.WorkerEntity;
import pt.uc.dei.aor.project.persistence.proxy.IProxyToEntity;
import pt.uc.dei.aor.project.persistence.proxy.WorkerProxy;

@Stateless
public class WorkerPersistenceService implements IWorkerPersistenceService {
	
	@PersistenceContext(unitName = "jobs")
    private EntityManager em;
	
	@Override
	public IWorker save(IWorker worker) {
		WorkerEntity entity = getEntity(worker);
		
		em.persist(entity);
		
		return new WorkerProxy(entity);
	}
	
	@Override
	public void delete(IWorker worker) {
		WorkerEntity entity = getEntity(worker); 
		
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
	public List<IWorker> findAllUsers() {
		TypedQuery<WorkerEntity> query = em.createNamedQuery("Worker.findAllWorkers", WorkerEntity.class);
		
		List<WorkerEntity> entities = query.getResultList();
		List<IWorker> proxies = new ArrayList<>();
		
		for (WorkerEntity we : entities) {
			proxies.add(new WorkerProxy(we));
		}
		
		return proxies;
	}

	
	
	
	@SuppressWarnings("unchecked")
    private WorkerEntity getEntity(IWorker workerProxy) {
        if (workerProxy instanceof IProxyToEntity<?>) {
            return ((IProxyToEntity<WorkerEntity>) workerProxy).getEntity();
        }

        throw new IllegalStateException();
    }

	

}
