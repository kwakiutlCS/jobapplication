package pt.uc.dei.aor.project.business.model;

public interface INotification {

	public void markAsViewed();
	
	public String getDate();
	
	public String getMessage();
	
	public String getType();
	
	public boolean isRead();
	
}
