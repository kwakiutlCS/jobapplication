package pt.uc.dei.aor.project.business.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.uc.dei.aor.project.business.exception.DuplicatedUserException;
import pt.uc.dei.aor.project.business.exception.IllegalFormatUploadException;
import pt.uc.dei.aor.project.business.exception.NoRoleException;
import pt.uc.dei.aor.project.business.exception.WrongPasswordException;
import pt.uc.dei.aor.project.business.model.IModelFactory;
import pt.uc.dei.aor.project.business.model.IWorker;
import pt.uc.dei.aor.project.business.persistence.IWorkerPersistenceService;
import pt.uc.dei.aor.project.business.startup.Encryptor;
import pt.uc.dei.aor.project.business.util.EmailUtil;
import pt.uc.dei.aor.project.business.util.PasswordUtil;
import pt.uc.dei.aor.project.business.util.Role;
import pt.uc.dei.aor.project.business.util.UploadUtil;

@Stateless
public class WorkerBusinessService implements IWorkerBusinessService {
	
	private static Logger logger = LoggerFactory.getLogger(WorkerBusinessService.class);
	
	@Inject
	private EmailUtil emailUtil;
	
	@Inject
	private IModelFactory factory;
	
	@Inject
	private IWorkerPersistenceService workerPersistence;
	
	@Inject
	private UploadUtil upload;
	
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
			emailUtil.send(email, "test", "other");
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

	@Override
	public IWorker update(IWorker updatedUser, String password) throws WrongPasswordException {
		IWorker user = workerPersistence.verifyUser(updatedUser.getId(), Encryptor.encrypt(password));
		
		if (user == null) throw new WrongPasswordException(); 
		
		return workerPersistence.save(updatedUser);
	}

	@Override
	public IWorker update(IWorker user) {
		return workerPersistence.save(user);
	}

	@Override
	public void uploadWorkers(InputStream in) throws IllegalFormatUploadException {
		Path path =	upload.upload("usersImport", in);
		
		BufferedReader reader = null;
		String header = null;
		
		try {
			reader = Files.newBufferedReader(path);
			header = reader.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// header
		String[] headerFields = header.split(",");
		
		if (headerFields.length != 5) 
			throw new IllegalFormatUploadException("CSV file must contain Login, Email, First Name, Last Name and Roles fields");
		if (!(headerFields[0].toLowerCase().equals("login") && headerFields[1].toLowerCase().equals("email")
			&& headerFields[2].toLowerCase().equals("first name") && headerFields[3].toLowerCase().equals("last name") &&
			headerFields[4].toLowerCase().equals("roles"))) {
				throw new IllegalFormatUploadException("CSV file must contain Login, Email, First Name, Last Name and Roles fields");
		}
		
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				String[] linef = line.split(",");
				
				List<Role> roles = new ArrayList<>();
				roles.add(Role.ADMIN);
			    try {
					createNewWorker(linef[0], linef[2], linef[3], linef[1], roles);
				} catch (NoRoleException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (DuplicatedUserException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
