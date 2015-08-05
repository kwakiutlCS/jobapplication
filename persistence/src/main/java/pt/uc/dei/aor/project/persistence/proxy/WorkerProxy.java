package pt.uc.dei.aor.project.persistence.proxy;

import pt.uc.dei.aor.project.business.model.IWorker;
import pt.uc.dei.aor.project.persistence.entity.WorkerEntity;

public class WorkerProxy implements IWorker, IProxyToEntity<WorkerEntity> {

	private WorkerEntity entity;
	
	public WorkerProxy(WorkerEntity entity) {
		this.entity = entity != null ? entity : new WorkerEntity();
	}

	public WorkerProxy(String login, String email, String password, String name, String surname) {
		entity = new WorkerEntity(login, email, password, name, surname);
	}

	@Override
	public WorkerEntity getEntity() {
		return entity;
	}

}
