package pt.uc.dei.aor.project.persistence.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import pt.uc.dei.aor.project.business.model.INotification;
import pt.uc.dei.aor.project.business.model.IUser;
import pt.uc.dei.aor.project.business.persistence.INotificationPersistenceService;
import pt.uc.dei.aor.project.persistence.entity.NotificationEntity;
import pt.uc.dei.aor.project.persistence.proxy.NotificationProxy;

@Stateless
public class NotificationPersistenceService implements INotificationPersistenceService {
	
	@PersistenceContext(unitName = "jobs")
    private EntityManager em;

	@Override
	public INotification save(INotification notification) {
		NotificationEntity entity = GenericPersistenceService
					.getEntity((INotification) notification);
			
		entity = em.merge(entity);
		
		return new NotificationProxy(entity);
	}

	@Override
	public void remove(INotification notification) {
		em.remove(em.merge(GenericPersistenceService
					.getEntity((INotification) notification)));
	}

	
	@Override
	public List<INotification> findAllNotifications(IUser person, int offset, int limit) {
		return findNotifications(person, offset, limit, "Notification.findNotificationsByUser");
	}

	@Override
	public List<INotification> findAllUnreadNotifications(IUser person, int offset, int limit) {
		return findNotifications(person, offset, limit, "Notification.findUnreadByUser");
	}

	@Override
	public List<INotification> findAllReadNotifications(IUser person, int offset, int limit) {
		return findNotifications(person, offset, limit, "Notification.findReadByUser");
	}

	
	
	private List<INotification> 
	findNotifications(IUser person, int offset, int limit, String queryString) {
		TypedQuery<NotificationEntity> query = em.createNamedQuery(
				queryString, NotificationEntity.class);
		query.setParameter("user", GenericPersistenceService.getEntity(person));
		query.setFirstResult(offset);
		query.setMaxResults(limit);

		List<NotificationEntity> entities = query.getResultList();
		List<INotification> proxies = new ArrayList<>();

		for (NotificationEntity wne : entities) {
			proxies.add(new NotificationProxy(wne));
		}

		return proxies;

	}

	@Override
	public long countUnread(IUser worker) {
		TypedQuery<Long> query = em.createNamedQuery("Notification.countUnread", Long.class);
		query.setParameter("user", GenericPersistenceService.getEntity(worker));
		
		List<Long> counter = query.getResultList();
		if (counter.isEmpty()) return 0;
		
		return counter.get(0);
	}


}
