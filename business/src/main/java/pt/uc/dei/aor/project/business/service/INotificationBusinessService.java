package pt.uc.dei.aor.project.business.service;

import java.util.List;

import pt.uc.dei.aor.project.business.model.INotification;
import pt.uc.dei.aor.project.business.model.IUser;


public interface INotificationBusinessService {

	public INotification notify(IUser person, String msg, String type);
	
	public INotification markNotificationAsViewed(INotification notification);
	
	public void deleteNotification(INotification notification);
	
	public List<INotification> findAllNotifications(IUser person, int offset, int limit);
	
	public List<INotification> findAllUnreadNotifications(IUser person, int offset, int limit);

	public List<INotification> findAllReadNotifications(IUser person, int offset, int limit);

	long countUnreadNotifications(IUser worker);

	
}
