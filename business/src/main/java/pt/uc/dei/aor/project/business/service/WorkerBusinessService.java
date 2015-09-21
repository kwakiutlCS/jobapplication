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

import javax.ejb.Asynchronous;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.Part;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.uc.dei.aor.project.business.exception.DuplicatedUserException;
import pt.uc.dei.aor.project.business.exception.IllegalFormatUploadException;
import pt.uc.dei.aor.project.business.exception.IllegalRoleChangeException;
import pt.uc.dei.aor.project.business.exception.NoRoleException;
import pt.uc.dei.aor.project.business.exception.WrongPasswordException;
import pt.uc.dei.aor.project.business.filter.WorkerFilter;
import pt.uc.dei.aor.project.business.model.IInterview;
import pt.uc.dei.aor.project.business.model.IModelFactory;
import pt.uc.dei.aor.project.business.model.IWorker;
import pt.uc.dei.aor.project.business.persistence.IInterviewPersistenceService;
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
	private IInterviewPersistenceService interviewPersistence;
	
	@Inject
	private UploadUtil upload;
	
	@Override
	public IWorker createNewWorker(String login, String name, String surname, String email,
			Collection<Role> roles) throws NoRoleException, DuplicatedUserException {
		if (roles.isEmpty()) throw new NoRoleException();
		if (findWorkerByEmailOrLogin(email, login)) throw new DuplicatedUserException();
		
		String password = null;
		if (login.equals("admin")) {
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
	public void uploadWorkers(InputStream in) throws IllegalFormatUploadException, NoRoleException, DuplicatedUserException, IOException {
		Path path =	upload.upload("usersImport", in);
		
		BufferedReader reader = null;
		String header = null;
		
		reader = Files.newBufferedReader(path);
		header = reader.readLine();
		
		
		// header
		String[] headerFields = header.split(",");
		
		if (headerFields.length != 5) 
			throw new IllegalFormatUploadException("CSV file must contain Login, Email, First Name, Last Name and Roles fields");
		if (!(headerFields[0].toLowerCase().equals("login") && headerFields[1].toLowerCase().equals("email")
			&& headerFields[2].toLowerCase().equals("first name") && headerFields[3].toLowerCase().equals("last name") &&
			headerFields[4].toLowerCase().equals("roles"))) {
				throw new IllegalFormatUploadException("CSV file must contain Login, Email, First Name, Last Name and Roles fields in that order");
		}
		
		String line = null;
		while ((line = reader.readLine()) != null) {
			String[] lineFields = line.split(",");
			lineFields[4] = lineFields[4].toLowerCase();	
			
			List<Role> roles = new ArrayList<>();
			if (lineFields[4].indexOf("admin") != -1)
				roles.add(Role.ADMIN);
			if (lineFields[4].indexOf("manager") != -1)
				roles.add(Role.MANAGER);
			if (lineFields[4].indexOf("interviewer") != -1)
				roles.add(Role.INTERVIEWER);
			
			createNewWorker(lineFields[0], lineFields[2], lineFields[3], lineFields[1], roles);
				
		}
		
		upload.delete(path);
	}

	@Override
	public List<IWorker> findUsersWithFilter(WorkerFilter filter, int offset, int limit) {
		return workerPersistence.findUsersWithFilter(filter, offset, limit);
	}

	@Override
	public void uploadCV(IWorker worker, Part cv) throws IOException {
		String filename = cv.getSubmittedFileName();
		String dir = "cv/"+worker.getLogin();
		
		upload.delete(dir);
		upload.upload(dir, filename, cv.getInputStream());
		
		worker.setCv(filename);
		workerPersistence.save(worker);
	}

	@Override
	public void addAdmin(IWorker user) {
		user.addRole(Role.ADMIN);
		workerPersistence.save(user);
	}

	@Override
	public void removeAdmin(IWorker admin, IWorker user) throws IllegalRoleChangeException {
		if (admin.equals(user)) {
			throw new IllegalRoleChangeException(
					"It is not possible to remove admin status from yourself");
		}
		
		if (workerPersistence.countAdmins() == 1) {
			throw new IllegalRoleChangeException(
					"At least one admin is required in the application");
		}
		
		user.removeRole(Role.ADMIN);
		workerPersistence.save(user);
	}

	@Override
	public void addManager(IWorker user) {
		user.addRole(Role.MANAGER);
		workerPersistence.save(user);
	}

	@Override
	public void removeManager(IWorker user) {
		user.removeRole(Role.MANAGER);
		workerPersistence.save(user);
	}

	@Override
	public void addInterviewer(IWorker user) {
		user.addRole(Role.INTERVIEWER);
		workerPersistence.save(user);
	}

	@Override
	public void removeInterviewer(IWorker user) {
		user.removeRole(Role.INTERVIEWER);
		workerPersistence.save(user);
	}

	@Override
	public boolean interviewerHasCandidate(IWorker user, String login) {
		List<IInterview> interviews = interviewPersistence.findActiveInterviewsByUser(user);
		
		for (IInterview i : interviews) {
			if (i.getCandidate().getLogin().equals(login)) return true;
		}
		
		return false;
	}
}
