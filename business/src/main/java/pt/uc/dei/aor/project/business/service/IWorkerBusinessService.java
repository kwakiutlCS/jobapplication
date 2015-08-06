package pt.uc.dei.aor.project.business.service;

import pt.uc.dei.aor.project.business.model.IWorker;

public interface IWorkerBusinessService {
	
	IWorker createNewWorker(String login, String name, String surname, String email, String password);
	
	String getWorkerFullName(String login);
}
