package pt.uc.dei.aor.project.business.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.inject.Inject;

import pt.uc.dei.aor.project.business.exception.NoRoleException;
import pt.uc.dei.aor.project.business.model.IModelFactory;
import pt.uc.dei.aor.project.business.model.IWorker;
import pt.uc.dei.aor.project.business.persistence.IWorkerPersistenceService;
import pt.uc.dei.aor.project.business.util.Role;

@Stateless
public class WorkerBusinessService implements IWorkerBusinessService {
	
	@Inject
	private IModelFactory factory;
	
	@Inject
	private IWorkerPersistenceService workerPersistence;
	
	@Override
	public IWorker createNewWorker(String login, String name, String surname, String email, String password,
			Collection<Role> roles) throws NoRoleException {
		if (roles.isEmpty()) throw new NoRoleException();
		if (findWorkerByEmailOrLogin(email, login)) return null;
		
		IWorker worker = factory.worker(login, email, password, name, surname, roles);
		
		try {
			return workerPersistence.save(worker);
		}
		catch(EJBException e) {
			return null;
		}
	}

	@Override
	public IWorker getWorkerByLogin(String login) {
		return workerPersistence.getWorkerByLogin(login);
	}

	@Override
	public IWorker getWorkerByEmail(String email) {
		return workerPersistence.getWorkerByEmail(email);
	}

	
	@Override
	public void deleteWorker(IWorker worker) {
		workerPersistence.delete(worker);
	}

	@Override
	public List<IWorker> findAllUsers() {
		return workerPersistence.findAllUsers();
	}

	@Override
	public List<Role> getRoles() {
		List<Role> roles = new ArrayList<>(EnumSet.allOf(Role.class));
		roles.remove(Role.CANDIDATE);
		return roles;
	}

	@Override
	public IWorker createSuperUser() {
		return workerPersistence.createSuperUser();
	}

	@Override
	public Collection<IWorker> findAllInterviewers() {
		return workerPersistence.findAllInterviewers();
	}

	@Override
	public Collection<IWorker> findAllManagers() {
		return workerPersistence.findAllManagers();
	}
	
	private	boolean findWorkerByEmailOrLogin(String email, String login) {
		return workerPersistence.findWorkerByEmailOrLogin(email, login);
	}
}
