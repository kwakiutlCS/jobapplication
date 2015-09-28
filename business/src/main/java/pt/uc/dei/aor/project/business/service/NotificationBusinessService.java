package pt.uc.dei.aor.project.business.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import pt.uc.dei.aor.project.business.model.IModelFactory;
import pt.uc.dei.aor.project.business.model.INotification;
import pt.uc.dei.aor.project.business.model.IUser;
import pt.uc.dei.aor.project.business.persistence.INotificationPersistenceService;
import pt.uc.dei.aor.project.business.util.EmailUtil;

@Stateless
public class NotificationBusinessService implements INotificationBusinessService {

	@Inject
	private EmailUtil emailUtil;
	
	@Inject
	private IModelFactory factory;

	@Inject
	private INotificationPersistenceService notificationPersistence;
	
	
	@Override
	public INotification notify(IUser person, String msg, String type) {
				
		INotification notification = factory.notification(msg, person, type);
			
		return notificationPersistence.save(notification);
	}

	
	@Override
	public INotification markNotificationAsViewed(INotification notification) {
		notification.markAsViewed();
		return notificationPersistence.save(notification);
	}

	@Override
	public void deleteNotification(INotification notification) {
		notificationPersistence.remove(notification);
	}

	@Override
	public List<INotification> findAllNotifications(IUser person, int offset, int limit) {
		return notificationPersistence.findAllNotifications(person, offset, limit);
	}

	@Override
	public List<INotification> findAllUnreadNotifications(IUser person, int offset, int limit) {
		return notificationPersistence.findAllUnreadNotifications(person, offset, limit);
	}
	
	@Override
	public List<INotification> findAllReadNotifications(IUser person, int offset, int limit) {
		return notificationPersistence.findAllReadNotifications(person, offset, limit);
	}

	@Override
	public long countUnreadNotifications(IUser worker) {
		return notificationPersistence.countUnread(worker);
	}
	
}
