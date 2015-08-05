package pt.uc.dei.aor.project.persistence.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
		
		entity = em.merge(entity);
		
		return new WorkerProxy(entity);
	}

	
	@SuppressWarnings("unchecked")
    private WorkerEntity getEntity(IWorker workerProxy) {
        if (workerProxy instanceof IProxyToEntity<?>) {
            return ((IProxyToEntity<WorkerEntity>) workerProxy).getEntity();
        }

        throw new IllegalStateException();
    }
}
