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
import pt.uc.dei.aor.project.business.service.INotificationBusinessService;

@Named
@ViewScoped
public class NotificationBean implements Serializable {
	
	private static final long serialVersionUID = 3741180061807047698L;
	
	private NotificationStatus notificationStatus = NotificationStatus.UNREAD;
	
	private int visibleNotification = -1;
	
	@Inject
	private INotificationBusinessService notificationService;
	
	
	public List<INotification> getNotifications() {
		if (notificationStatus == NotificationStatus.UNREAD) {
			return notificationService.findAllUnreadNotifications(getUser(), 0, 10);
		}
		if (notificationStatus == NotificationStatus.ALL) {
			return notificationService.findAllNotifications(getUser(), 0, 10);
		}
		if (notificationStatus == NotificationStatus.READ) {
			return notificationService.findAllReadNotifications(getUser(), 0, 10);
		}
		return null;
	}
	
	public void deleteNotification(INotification notification) {
		notificationService.deleteNotification(notification);
	}
	
	public void viewNotification(int index, INotification notification) {
		visibleNotification = index;
		notificationService.markNotificationAsViewed(notification);
	}
	
	public long countUnread() {
		return notificationService.countUnreadNotifications(getUser());
	}
	
	public void filter() {
		visibleNotification = -1;
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

	// helper functions
	public IWorker getUser() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		return (IWorker) request.getSession().getAttribute("user");	
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

