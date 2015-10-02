package pt.uc.dei.aor.project.business.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.Part;

import pt.uc.dei.aor.project.business.exception.DuplicatedUserException;
import pt.uc.dei.aor.project.business.exception.IllegalFormatUploadException;
import pt.uc.dei.aor.project.business.exception.IllegalRoleChangeException;
import pt.uc.dei.aor.project.business.exception.NoRoleException;
import pt.uc.dei.aor.project.business.exception.WrongPasswordException;
import pt.uc.dei.aor.project.business.filter.WorkerFilter;
import pt.uc.dei.aor.project.business.model.IQualification;
import pt.uc.dei.aor.project.business.model.IUser;
import pt.uc.dei.aor.project.business.util.Role;


public interface IUserBusinessService {
	
	IUser createNewUser(String login, String name, String surname, String email, 
			Collection<Role> roles) throws NoRoleException, DuplicatedUserException;
	
	IUser createNewCandidate(String login, String name, String surname,
			String email, String encrypt, String phone, String mobilePhone, String address, String city,
			String country, List<IQualification> qualifications, String cv, String provisoryCv) throws IOException;
	
	IUser getUserByLogin(String login);

	void deleteUser(IUser worker);

	public List<IUser> findAllUsers();

	public List<Role> getRoles();
	
	IUser getUserByEmail(String email);
	
	Collection<IUser> findAllInterviewers();
	
	Collection<IUser> findAllManagers();

	void recoverPassword(String email);

	IUser update(IUser user, String password) throws WrongPasswordException;

	IUser update(IUser user);

	void uploadUsers(InputStream inputStream) 
			throws IllegalFormatUploadException, NoRoleException, DuplicatedUserException, IOException;

	List<IUser> findUsersWithFilter(WorkerFilter filter, int offset, int limit);

	IUser uploadCV(IUser worker, Part cv) throws IOException;

	void addAdmin(IUser user);

	void removeAdmin(IUser admin, IUser user) throws IllegalRoleChangeException;

	void addManager(IUser user);

	void removeManager(IUser user) throws IllegalRoleChangeException;

	void addInterviewer(IUser user);

	void removeInterviewer(IUser user) throws IllegalRoleChangeException;

	boolean interviewerHasCandidate(IUser user, String string);

	String uploadTempCV(Part cv) throws IOException;

}
