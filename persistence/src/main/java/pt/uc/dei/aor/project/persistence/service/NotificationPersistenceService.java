package pt.uc.dei.aor.project.persistence.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import pt.uc.dei.aor.project.business.model.INotification;
import pt.uc.dei.aor.project.business.model.IWorker;
import pt.uc.dei.aor.project.business.model.IWorkerNotification;
import pt.uc.dei.aor.project.business.persistence.INotificationPersistenceService;
import pt.uc.dei.aor.project.persistence.entity.NotificationEntity;
import pt.uc.dei.aor.project.persistence.proxy.NotificationProxy;

@Stateless
public class NotificationPersistenceService implements INotificationPersistenceService {
	
	@PersistenceContext(unitName = "jobs")
    private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public <T extends INotification> T save(T notification) {
		if (notification instanceof IWorkerNotification) {
			NotificationEntity entity = GenericPersistenceService
					.getEntity((IWorkerNotification) notification);
			
			entity = em.merge(entity);
			return (T) new NotificationProxy(entity);
		}
		
		return null;
	}

	@Override
	public <T extends INotification> void remove(T notification) {
		if (notification instanceof IWorkerNotification) {
			em.remove(em.merge(GenericPersistenceService
					.getEntity((IWorkerNotification) notification)));
		}
		else {
			
		}
	}

		@Override
	public <T extends INotification, U> List<T> findAllNotifications(U person, int offset, int limit) {
		return findNotifications(person, offset, limit, "WorkerNotification.findNotificationsByWorker");
	}

	@Override
	public <T extends INotification, U> List<T> findAllUnreadNotifications(U person, int offset, int limit) {
		return findNotifications(person, offset, limit, "WorkerNotification.findUnreadByWorker");
	}

	@Override
	public <T extends INotification, U> List<T> findAllReadNotifications(U person, int offset, int limit) {
		return findNotifications(person, offset, limit, "WorkerNotification.findReadByWorker");
	}

	
	@SuppressWarnings("unchecked")
	private <T extends INotification, U> List<T> 
		findNotifications(U person, int offset, int limit, String queryString) {
		List<INotification> proxies = new ArrayList<>();

		if (person instanceof IWorker) {
			TypedQuery<NotificationEntity> query = em.createNamedQuery(
					queryString, NotificationEntity.class);
			query.setParameter("worker", GenericPersistenceService.getEntity(person));
			query.setFirstResult(offset);
			query.setMaxResults(limit);
			
			List<NotificationEntity> entities = query.getResultList();
			System.out.println(entities);
			for (NotificationEntity wne : entities) {
				proxies.add(new NotificationProxy(wne));
			}
			
			return (List<T>) proxies;
		}
		return null;
	}

	@Override
	public long countUnread(IWorker worker) {
		TypedQuery<Long> query = em.createNamedQuery("WorkerNotification.countUnread", Long.class);
		query.setParameter("worker", GenericPersistenceService.getEntity(worker));
		
		List<Long> counter = query.getResultList();
		
		return counter.get(0);
	}


}
