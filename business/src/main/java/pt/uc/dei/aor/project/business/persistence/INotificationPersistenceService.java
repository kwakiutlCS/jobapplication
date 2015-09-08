package pt.uc.dei.aor.project.business.persistence;

import java.util.List;

import pt.uc.dei.aor.project.business.model.INotification;
import pt.uc.dei.aor.project.business.model.IWorker;

public interface INotificationPersistenceService {

	public <T extends INotification> T save(T notification);

	public <T extends INotification> void remove(T notification);

	public <T extends INotification, U> List<T> findAllNotifications(U person, int offset, int limit);

	public <T extends INotification, U> List<T> findAllUnreadNotifications(U person, int offset, int limit);

	public <T extends INotification, U> List<T> findAllReadNotifications(U person, int offset, int limit);

	long countUnread(IWorker worker);

}
