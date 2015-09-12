package pt.uc.dei.aor.project.business.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import pt.uc.dei.aor.project.business.model.IApplication;
import pt.uc.dei.aor.project.business.model.ICandidate;
import pt.uc.dei.aor.project.business.model.IModelFactory;
import pt.uc.dei.aor.project.business.model.INotification;
import pt.uc.dei.aor.project.business.model.IWorker;
import pt.uc.dei.aor.project.business.model.IWorkerNotification;
import pt.uc.dei.aor.project.business.persistence.IApplicationPersistenceService;
import pt.uc.dei.aor.project.business.persistence.ICandidatePersistenceService;
import pt.uc.dei.aor.project.business.persistence.INotificationPersistenceService;
import pt.uc.dei.aor.project.business.persistence.IWorkerPersistenceService;
import pt.uc.dei.aor.project.business.util.EmailUtil;

@Stateless
public class NotificationBusinessService implements INotificationBusinessService {

	@Inject
	private EmailUtil emailUtil;
	
	@Inject
	private IModelFactory factory;

	@Inject
	private IWorkerPersistenceService workerPersistence;
	
	@Inject
	private ICandidatePersistenceService candidatePersistence;
	
	@Inject
	private INotificationPersistenceService notificationPersistence;
	
	
	@Override
	public <T extends INotification, U> T notify(U person, String msg, String type, 
			String subject, String content) {
		if (person instanceof IWorker) {
			IWorker worker = (IWorker) person;
			
			IWorkerNotification notification = factory.workerNotification(msg, worker, type);
			
			emailUtil.send(worker.getEmail(), subject, content);
			
			return (T) notificationPersistence.save(notification);
		}
		//if (person instanceof ICandidate) return candidatePersistence.notify((ICandidate) person, msg);
		return null;
	}

	@Override
	public <T extends INotification> T markNotificationAsViewed(T notification) {
		notification.markAsViewed();
		return notificationPersistence.save(notification);
	}

	@Override
	public <T extends INotification> void deleteNotification(T notification) {
		notificationPersistence.remove(notification);
	}

	@Override
	public <T extends INotification, U> List<T> findAllNotifications(U person, int offset, int limit) {
		return notificationPersistence.findAllNotifications(person, offset, limit);
	}

	@Override
	public <T extends INotification, U> List<T> findAllUnreadNotifications(U person, int offset, int limit) {
		return notificationPersistence.findAllUnreadNotifications(person, offset, limit);
	}
	
	@Override
	public <T extends INotification, U> List<T> findAllReadNotifications(U person, int offset, int limit) {
		return notificationPersistence.findAllReadNotifications(person, offset, limit);
	}

	@Override
	public long countUnreadNotifications(IWorker worker) {
		return notificationPersistence.countUnread(worker);
	}
	
}
