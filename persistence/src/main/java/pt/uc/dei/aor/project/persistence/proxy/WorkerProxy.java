package pt.uc.dei.aor.project.persistence.proxy;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.resource.spi.IllegalStateException;

import pt.uc.dei.aor.project.business.exception.IllegalRoleException;
import pt.uc.dei.aor.project.business.model.IInterview;
import pt.uc.dei.aor.project.business.model.IWorker;
import pt.uc.dei.aor.project.business.util.Role;
import pt.uc.dei.aor.project.persistence.entity.InterviewEntity;
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
	
	public WorkerProxy() {
		this(null);
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

	@Override
	public String toString() {
		return entity.toString();
	}
	
	@Override
	public int hashCode() {
		return entity.hashCode();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object o) {
		WorkerEntity oe = null;
		
		if (o instanceof IProxyToEntity<?>)
			oe = ((IProxyToEntity<WorkerEntity>) o).getEntity();
		else return false;
		
		return entity.equals(oe);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addInterview(IInterview interview) throws IllegalRoleException, IllegalStateException {
		if (!isInterviewer()) throw new IllegalRoleException();
		
		InterviewEntity interviewEntity = null;
		if (interview instanceof IProxyToEntity<?>) 
			interviewEntity = ((IProxyToEntity<InterviewEntity>) interview).getEntity();
		else throw new IllegalStateException();
		
		entity.addInterview(interviewEntity);
	}
	
	
	
	// helper methods
	private boolean isInterviewer() {
		return entity.isInterviewer();
	}
}
