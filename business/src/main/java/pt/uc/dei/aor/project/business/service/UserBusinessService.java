package pt.uc.dei.aor.project.business.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
import pt.uc.dei.aor.project.business.model.IUser;
import pt.uc.dei.aor.project.business.persistence.IInterviewPersistenceService;
import pt.uc.dei.aor.project.business.persistence.IUserPersistenceService;
import pt.uc.dei.aor.project.business.startup.Encryptor;
import pt.uc.dei.aor.project.business.util.EmailUtil;
import pt.uc.dei.aor.project.business.util.PasswordUtil;
import pt.uc.dei.aor.project.business.util.Role;
import pt.uc.dei.aor.project.business.util.UploadUtil;

@Stateless
public class UserBusinessService implements IUserBusinessService {
	
	private static Logger logger = LoggerFactory.getLogger(UserBusinessService.class);
	
	@Inject
	private EmailUtil emailUtil;
	
	@Inject
	private IModelFactory factory;
	
	@Inject
	private IUserPersistenceService userPersistence;
	
	@Inject
	private IInterviewPersistenceService interviewPersistence;
	
	@Inject
	private UploadUtil upload;
	
	@Override
	public IUser createNewUser(String login, String name, String surname, String email,
			Collection<Role> roles) throws NoRoleException, DuplicatedUserException {
		if (roles.isEmpty()) throw new NoRoleException();
		if (findUserByEmailOrLogin(email, login)) throw new DuplicatedUserException();
		
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
		
		IUser worker = factory.user(login, email, 
				password, name, surname, roles);
		
		try {
			return userPersistence.save(worker);
		}
		catch(EJBException e) {
			return null;
		}
	}

	@Override
	public IUser getUserByLogin(String login) {
		return userPersistence.getUserByLogin(login);
	}

	@Override
	public IUser getUserByEmail(String email) {
		return userPersistence.findUserByEmail(email);
	}

	
	@Override
	public void deleteUser(IUser user) {
		userPersistence.delete(user);
	}

	@Override
	public List<IUser> findAllUsers() {
		return userPersistence.findAllUsers();
	}

	@Override
	public List<Role> getRoles() {
		List<Role> roles = new ArrayList<>();
		for (Role r : Role.values()) {
			if (r != Role.CANDIDATE)
				roles.add(r);
		}
		
		return roles;
	}

	
	@Override
	public Collection<IUser> findAllInterviewers() {
		return userPersistence.findAllInterviewers();
	}

	@Override
	public Collection<IUser> findAllManagers() {
		return userPersistence.findAllManagers();
	}
	
	private	boolean findUserByEmailOrLogin(String email, String login) {
		return userPersistence.findUserByEmailOrLogin(email, login);
	}

	@Override
	public void recoverPassword(String email) {
		IUser worker = userPersistence.findUserByEmail(email);
		if (worker == null) return;
		
		String password = PasswordUtil.generate(8);
		worker.setPassword(Encryptor.encrypt(password));
		userPersistence.save(worker);
		
		logger.trace("New password: "+password);
	}

	@Override
	public IUser update(IUser updatedUser, String password) throws WrongPasswordException {
		IUser user = userPersistence.verifyUser(updatedUser.getId(), Encryptor.encrypt(password));
		
		if (user == null) throw new WrongPasswordException(); 
		
		return userPersistence.save(updatedUser);
	}

	@Override
	public IUser update(IUser user) {
		return userPersistence.save(user);
	}

	@Override
	public void uploadUsers(InputStream in) throws IllegalFormatUploadException, NoRoleException, DuplicatedUserException, IOException {
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
			
			createNewUser(lineFields[0], lineFields[2], lineFields[3], lineFields[1], roles);
				
		}
		
		upload.delete(path);
	}

	@Override
	public List<IUser> findUsersWithFilter(WorkerFilter filter, int offset, int limit) {
		return userPersistence.findUsersWithFilter(filter, offset, limit);
	}

	@Override
	public void uploadCV(IUser user, Part cv) throws IOException {
		String filename = cv.getSubmittedFileName();
		String dir = "cv/"+user.getLogin();
		
		upload.delete(dir);
		upload.upload(dir, filename, cv.getInputStream());
		
		user.setCv(filename);
		userPersistence.save(user);
	}

	@Override
	public void addAdmin(IUser user) {
		user.addRole(Role.ADMIN);
		userPersistence.save(user);
	}

	@Override
	public void removeAdmin(IUser admin, IUser user) throws IllegalRoleChangeException {
		if (admin.equals(user)) {
			throw new IllegalRoleChangeException(
					"It is not possible to remove admin status from yourself");
		}
		
		if (userPersistence.countAdmins() == 1) {
			throw new IllegalRoleChangeException(
					"At least one admin is required in the application");
		}
		
		user.removeRole(Role.ADMIN);
		userPersistence.save(user);
	}

	@Override
	public void addManager(IUser user) {
		user.addRole(Role.MANAGER);
		userPersistence.save(user);
	}

	@Override
	public void removeManager(IUser user) {
		user.removeRole(Role.MANAGER);
		userPersistence.save(user);
	}

	@Override
	public void addInterviewer(IUser user) {
		user.addRole(Role.INTERVIEWER);
		userPersistence.save(user);
	}

	@Override
	public void removeInterviewer(IUser user) {
		user.removeRole(Role.INTERVIEWER);
		userPersistence.save(user);
	}

	@Override
	public boolean interviewerHasCandidate(IUser user, String login) {
		List<IInterview> interviews = interviewPersistence.findActiveInterviewsByUser(user);
		
		for (IInterview i : interviews) {
			if (i.getCandidate().getLogin().equals(login)) return true;
		}
		
		return false;
	}

	@Override
	public IUser createNewCandidate(String login, String name, String surname, String email, String password)
			throws DuplicatedUserException {
		IUser user = factory.user(login, email, password, name, surname, new ArrayList<Role>());
		
		return userPersistence.save(user);
	}
}
