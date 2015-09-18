package pt.uc.dei.aor.project.business.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.Part;

import pt.uc.dei.aor.project.business.exception.DuplicatedUserException;
import pt.uc.dei.aor.project.business.exception.IllegalFormatUploadException;
import pt.uc.dei.aor.project.business.exception.NoRoleException;
import pt.uc.dei.aor.project.business.exception.WrongPasswordException;
import pt.uc.dei.aor.project.business.filter.WorkerFilter;
import pt.uc.dei.aor.project.business.model.IWorker;
import pt.uc.dei.aor.project.business.util.Role;


public interface IWorkerBusinessService {
	
	IWorker createNewWorker(String login, String name, String surname, String email, 
			Collection<Role> roles) throws NoRoleException, DuplicatedUserException;
	
	IWorker getWorkerByLogin(String login);

	void deleteWorker(IWorker worker);

	public List<IWorker> findAllUsers();

	public List<Role> getRoles();
	
	IWorker getWorkerByEmail(String email);
	
	IWorker createSuperUser();

	Collection<IWorker> findAllInterviewers();
	
	Collection<IWorker> findAllManagers();

	void recoverPassword(String email);

	IWorker update(IWorker user, String password) throws WrongPasswordException;

	IWorker update(IWorker user);

	void uploadWorkers(InputStream inputStream) 
			throws IllegalFormatUploadException, NoRoleException, DuplicatedUserException, IOException;

	List<IWorker> findUsersWithFilter(WorkerFilter filter, int offset, int limit);

	void uploadCV(IWorker worker, Part cv) throws IOException;

}
