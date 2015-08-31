package pt.uc.dei.aor.project.business.persistence;

import java.util.Collection;
import java.util.List;

import pt.uc.dei.aor.project.business.model.IWorker;

public interface IWorkerPersistenceService {

	IWorker save(IWorker worker);

	IWorker getWorkerByLogin(String login);

	void delete(IWorker worker);

	List<IWorker> findAllUsers();

	IWorker getWorkerByEmail(String email);
	
	Collection<IWorker> findAllAdmins();
	
	Collection<IWorker> findAllManagers();
	
	Collection<IWorker> findAllInterviewers();

}
