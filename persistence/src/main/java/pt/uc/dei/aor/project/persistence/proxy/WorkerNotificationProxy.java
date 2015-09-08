package pt.uc.dei.aor.project.persistence.proxy;

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

	public WorkerNotificationProxy(String msg, IWorker worker) {
		entity = new WorkerNotificationEntity(msg, GenericPersistenceService.getEntity(worker),
				new Date());
	}

	@Override
	public WorkerNotificationEntity getEntity() {
		return entity;
	}

	@Override
	public void markAsViewed() {
		entity.setRead(true);
	}

}
