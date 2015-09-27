package pt.uc.dei.aor.project.persistence.proxy;

import java.text.SimpleDateFormat;
import java.util.Date;

import pt.uc.dei.aor.project.business.model.INotification;
import pt.uc.dei.aor.project.business.model.IUser;
import pt.uc.dei.aor.project.persistence.entity.NotificationEntity;
import pt.uc.dei.aor.project.persistence.service.GenericPersistenceService;

public class NotificationProxy implements 
	INotification, IProxyToEntity<NotificationEntity> {

	private NotificationEntity entity;
	
	public NotificationProxy(NotificationEntity entity) {
		this.entity = entity != null ? entity : new NotificationEntity();
	}

	public NotificationProxy() {
		this(null);
	}

	public NotificationProxy(String msg, IUser worker, String type) {
		entity = new NotificationEntity(msg, GenericPersistenceService.getEntity(worker),
				type);
	}

	@Override
	public NotificationEntity getEntity() {
		return entity;
	}

	@Override
	public void markAsViewed() {
		entity.setRead(true);
	}

	@Override
	public String getDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(new Date()); 
	}

	@Override
	public String getMessage() {
		return entity.getMsg();
	}

	
	@Override
	public String getType() {
		return entity.getType();
	}

	@Override
	public boolean isRead() {
		return entity.isRead();
	}

	
}
