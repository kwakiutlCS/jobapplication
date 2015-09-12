package pt.uc.dei.aor.project.business.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.uc.dei.aor.project.business.exception.DuplicatedUserException;
import pt.uc.dei.aor.project.business.exception.NoRoleException;
import pt.uc.dei.aor.project.business.model.IModelFactory;
import pt.uc.dei.aor.project.business.model.IWorker;
import pt.uc.dei.aor.project.business.persistence.IWorkerPersistenceService;
import pt.uc.dei.aor.project.business.startup.Encryptor;
import pt.uc.dei.aor.project.business.util.PasswordUtil;
import pt.uc.dei.aor.project.business.util.Role;

@Stateless
public class WorkerBusinessService implements IWorkerBusinessService {
	
	private static Logger logger = LoggerFactory.getLogger(WorkerBusinessService.class);
	
	@Inject
	private IModelFactory factory;
	
	@Inject
	private IWorkerPersistenceService workerPersistence;
	
	@Override
	public IWorker createNewWorker(String login, String name, String surname, String email,
			Collection<Role> roles) throws NoRoleException, DuplicatedUserException {
		if (roles.isEmpty()) throw new NoRoleException();
		
		String password = null;
		if (login.equals("admin")) {
			if (findWorkerByEmailOrLogin(email, login)) throw new DuplicatedUserException();
			password = Encryptor.encrypt("admin");
		}
		else {
			String p = PasswordUtil.generate(8);
			password = Encryptor.encrypt(p);
			logger.trace("User: "+login+" with password: "+p+" created");
		}
		
		IWorker worker = factory.worker(login, email, 
				password, name, surname, roles);
		
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

	@Override
	public void recoverPassword(String email) {
		IWorker worker = workerPersistence.findWorkerByEmail(email);
		if (worker == null) return;
		
		String password = PasswordUtil.generate(8);
		worker.setPassword(Encryptor.encrypt(password));
		workerPersistence.save(worker);
		
		logger.trace("New password: "+password);
	}
}
