package pt.uc.dei.aor.project.persistence.service;

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
		
		entity = em.merge(entity);
		
		return new WorkerProxy(entity);
	}

	
	@Override
	public String getWorkerFullName(String login) {
		TypedQuery<WorkerEntity> query = em.createNamedQuery("findWorkerByLogin", WorkerEntity.class);
		query.setParameter("login", login);
		
		List<WorkerEntity> entities = query.getResultList();
		
		if (!entities.isEmpty()) {
			WorkerEntity entity = entities.get(0);
			return entity.getName()+" "+entity.getSurname();
		}
		
		return null;
	}
	
	
	@SuppressWarnings("unchecked")
    private WorkerEntity getEntity(IWorker workerProxy) {
        if (workerProxy instanceof IProxyToEntity<?>) {
            return ((IProxyToEntity<WorkerEntity>) workerProxy).getEntity();
        }

        throw new IllegalStateException();
    }
}
