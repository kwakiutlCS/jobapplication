package pt.uc.dei.aor.project.business.service;

import java.util.Collection;
import java.util.List;

import pt.uc.dei.aor.project.business.exception.NoRoleException;
import pt.uc.dei.aor.project.business.model.IWorker;
import pt.uc.dei.aor.project.business.util.Role;


public interface IWorkerBusinessService {
	
	IWorker createNewWorker(String login, String name, String surname, String email, 
			String password, Collection<Role> roles) throws NoRoleException;
	
	IWorker getWorkerByLogin(String login);

	void deleteWorker(IWorker worker);

	public List<IWorker> findAllUsers();

	public List<Role> getRoles();
	
	IWorker getWorkerByEmail(String email);
	
	IWorker createSuperUser();

	Collection<IWorker> findAllInterviewers();
	
	Collection<IWorker> findAllManagers();

}
