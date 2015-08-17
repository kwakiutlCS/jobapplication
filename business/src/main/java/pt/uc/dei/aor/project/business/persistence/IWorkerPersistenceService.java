package pt.uc.dei.aor.project.business.persistence;

import pt.uc.dei.aor.project.business.model.IWorker;

public interface IWorkerPersistenceService {

	IWorker save(IWorker worker);

	IWorker getWorkerByLogin(String login);

	void delete(IWorker worker);

}