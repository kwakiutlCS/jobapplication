package pt.uc.dei.aor.project.persistence.proxy;

import java.text.SimpleDateFormat;
import java.util.Date;

import pt.uc.dei.aor.project.business.model.INotification;
import pt.uc.dei.aor.project.business.model.IWorker;
import pt.uc.dei.aor.project.business.model.IWorkerNotification;
import pt.uc.dei.aor.project.persistence.entity.WorkerNotificationEntity;
import pt.uc.dei.aor.project.persistence.service.GenericPersistenceService;

public class WorkerNotificationProxy implements 
	IWorkerNotification, IProxyToEntity<WorkerNotificationEntity> {

	private WorkerNotificationEntity entity;
	
	public WorkerNotificationProxy(WorkerNotificationEntity entity) {
		this.entity = entity != null ? entity : new WorkerNotificationEntity();
	}

	public WorkerNotificationProxy() {
		this(null);
	}

	public WorkerNotificationProxy(String msg, IWorker worker, String type) {
		entity = new WorkerNotificationEntity(msg, GenericPersistenceService.getEntity(worker),
				type);
	}

	@Override
	public WorkerNotificationEntity getEntity() {
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
