package pt.uc.dei.aor.project.presentation.bean;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import pt.uc.dei.aor.project.business.model.INotification;
import pt.uc.dei.aor.project.business.model.IWorker;
import pt.uc.dei.aor.project.business.model.IWorkerNotification;
import pt.uc.dei.aor.project.business.service.INotificationBusinessService;
import pt.uc.dei.aor.project.presentation.util.MetaUtils;

@Named
@ViewScoped
public class NotificationBean implements Serializable {
	
	private static final long serialVersionUID = 3741180061807047698L;
	
	private NotificationStatus notificationStatus = NotificationStatus.UNREAD;
	
	private int visibleNotification = -1;
	
	private List<INotification> notifications;
	
	@Inject
	private INotificationBusinessService notificationService;
	
	
	public List<INotification> getNotifications() {
		if (notificationStatus == NotificationStatus.UNREAD && notifications == null) {
			notifications = notificationService.findAllUnreadNotifications(MetaUtils.getUser(), 0, 10);
		}
		if (notificationStatus == NotificationStatus.ALL) {
			notifications = notificationService.findAllNotifications(MetaUtils.getUser(), 0, 10);
		}
		if (notificationStatus == NotificationStatus.READ) {
			notifications = notificationService.findAllReadNotifications(MetaUtils.getUser(), 0, 10);
		}
		return notifications;
	}
	
	public void deleteNotification(INotification notification) {
		int deleted = notifications.indexOf(notification);
		if (deleted == visibleNotification) visibleNotification = -1;
		else if (deleted < visibleNotification) visibleNotification--;
		
		notificationService.deleteNotification(notification);
		if (notificationStatus == NotificationStatus.UNREAD)
			notifications = notificationService.findAllUnreadNotifications(MetaUtils.getUser(), 0, 10);
	}
	
	public void viewNotification(int index, INotification notification) {
		visibleNotification = index;
		notificationService.markNotificationAsViewed(notification);
	}
	
	public long countUnread() {
		return notificationService.countUnreadNotifications(MetaUtils.getUser());
	}
	
	public void filter() {
		visibleNotification = -1;
		if (notificationStatus == NotificationStatus.UNREAD) notifications = null;
	}
	
	// getters and setters
		
	public List<NotificationStatus> getAllStatus() {
		return Arrays.asList(NotificationStatus.values());
	}
	
	public NotificationStatus getNotificationStatus() {
		return notificationStatus;
	}

	public void setNotificationStatus(NotificationStatus notificationStatus) {
		this.notificationStatus = notificationStatus;
	}


	
	public int getVisibleNotification() {
		return visibleNotification;
	}

	public void setVisibleNotification(int visibleNotification) {
		this.visibleNotification = visibleNotification;
	}



	private static enum NotificationStatus {
		UNREAD("Unread"), ALL("All"), READ("Read"); 
		
		private String label;
		
		private NotificationStatus(String label) {
			this.label = label;
		}
		
		public String getLabel() {
			return label;
		}
	}

}

