package pt.uc.dei.aor.project.persistence.proxy;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import pt.uc.dei.aor.project.business.model.IWorker;
import pt.uc.dei.aor.project.business.util.Role;
import pt.uc.dei.aor.project.persistence.entity.WorkerEntity;

public class WorkerProxy implements IWorker, IProxyToEntity<WorkerEntity> {

	private WorkerEntity entity;
	
	public WorkerProxy(WorkerEntity entity) {
		this.entity = entity != null ? entity : new WorkerEntity();
	}

	public WorkerProxy(String login, String email, String password, String name, 
			String surname, Collection<Role> roles) {
		entity = new WorkerEntity(login, email, password, name, surname, roles);
	}

	@Override
	public WorkerEntity getEntity() {
		return entity;
	}

	@Override
	public String getFullName() {
		return entity.getName()+" "+entity.getSurname();
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
	
	@Override
	public long getId() {
		return entity.getId();
	}

	@Override
	public String getName() {
		return entity.getName();
	}

	@Override
	public String getSurname() {
		return entity.getSurname();
	}

	@Override
	public String getEmail() {
		return entity.getEmail();
	}

}
