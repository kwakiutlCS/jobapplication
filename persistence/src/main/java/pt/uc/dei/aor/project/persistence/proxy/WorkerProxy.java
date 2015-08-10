package pt.uc.dei.aor.project.persistence.proxy;

import java.util.LinkedList;
import java.util.List;

import pt.uc.dei.aor.project.business.model.IWorker;
import pt.uc.dei.aor.project.persistence.entity.WorkerEntity;
import pt.uc.dei.aor.project.persistence.util.Role;

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

	@Override
	public String getFullName() {
		return entity.getEmail()+" "+entity.getSurname();
	}

	@Override
	public List<String> getRoles() {
		List<String> roles = new LinkedList<>();
		for (Role r : entity.getRoles()) {
			roles.add(r.toString());
		}
		return roles;
	}

	@Override
	public String getLogin() {
		return entity.getLogin();
	}

}
