package pt.uc.dei.aor.project.business.persistence;

import java.util.Collection;
import java.util.List;

import pt.uc.dei.aor.project.business.filter.WorkerFilter;
import pt.uc.dei.aor.project.business.model.IInterview;
import pt.uc.dei.aor.project.business.model.IUser;

public interface IUserPersistenceService {

	IUser save(IUser worker);

	IUser getUserByLogin(String login);

	void delete(IUser worker);

	List<IUser> findAllUsers();
	
	Collection<IUser> findAllAdmins();
	
	Collection<IUser> findAllManagers();
	
	Collection<IUser> findAllInterviewers();

	void insertInterview(long worker_id, IInterview interview);

	boolean findUserByEmailOrLogin(String email, String login);

	void removeInterview(long worker_id, long interview_id);

	IUser findUserByEmail(String email);

	IUser verifyUser(long id, String encrypt);

	List<IUser> findUsersWithFilter(WorkerFilter filter, int offset, int limit);

	long countAdmins();
		
}
