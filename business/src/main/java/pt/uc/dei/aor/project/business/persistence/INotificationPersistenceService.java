package pt.uc.dei.aor.project.business.persistence;

import java.util.List;

import pt.uc.dei.aor.project.business.model.INotification;
import pt.uc.dei.aor.project.business.model.IUser;

public interface INotificationPersistenceService {

	public INotification save(INotification notification);

	public <T extends INotification> void remove(INotification notification);

	public  List<INotification> findAllNotifications(IUser person, int offset, int limit);

	public List<INotification> findAllUnreadNotifications(IUser person, int offset, int limit);

	public List<INotification> findAllReadNotifications(IUser person, int offset, int limit);

	long countUnread(IUser person);

}
