package pt.uc.dei.aor.project.business.persistence;

import java.util.Collection;
import java.util.List;

import pt.uc.dei.aor.project.business.model.IInterview;
import pt.uc.dei.aor.project.business.model.IWorker;
import pt.uc.dei.aor.project.business.model.IWorkerNotification;

public interface IWorkerPersistenceService {

	IWorker save(IWorker worker);

	IWorker getWorkerByLogin(String login);

	void delete(IWorker worker);

	List<IWorker> findAllUsers();

	IWorker getWorkerByEmail(String email);
	
	Collection<IWorker> findAllAdmins();
	
	Collection<IWorker> findAllManagers();
	
	Collection<IWorker> findAllInterviewers();

	IWorker createSuperUser();

	void insertInterview(long worker_id, IInterview interview);

	boolean findWorkerByEmailOrLogin(String email, String login);

	void removeInterview(long worker_id, long interview_id);

	IWorker findWorkerByEmail(String email);

	IWorker verifyUser(long id, String encrypt);

	
}
