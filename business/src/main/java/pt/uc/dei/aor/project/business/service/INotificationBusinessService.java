package pt.uc.dei.aor.project.business.service;

import java.util.List;

import pt.uc.dei.aor.project.business.model.INotification;
import pt.uc.dei.aor.project.business.model.IWorker;


public interface INotificationBusinessService {

	public <T extends INotification ,U> T notify(U person, String msg, String type);
	
	public <T extends INotification> T markNotificationAsViewed(T notification);
	
	public <T extends INotification> void deleteNotification(T notification);
	
	public <T extends INotification,U> List<T> findAllNotifications(U person, int offset, int limit);
	
	public <T extends INotification,U> List<T> findAllUnreadNotifications(U person, int offset, int limit);

	public <T extends INotification,U> List<T> findAllReadNotifications(U person, int offset, int limit);

	long countUnreadNotifications(IWorker worker);

}
