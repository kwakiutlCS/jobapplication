package pt.uc.dei.aor.project.business.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import pt.uc.dei.aor.project.business.model.IModelFactory;
import pt.uc.dei.aor.project.business.model.IWorker;
import pt.uc.dei.aor.project.business.persistence.IWorkerPersistenceService;

@Stateless
public class WorkerBusinessService implements IWorkerBusinessService {
	
	@Inject
	private IModelFactory factory;
	
	@Inject
	private IWorkerPersistenceService workerPersistence;
	
	@Override
	public IWorker createNewWorker(String login, String name, String surname, String email, String password) {
		IWorker worker = factory.worker(login, email, password, name, surname);
		return workerPersistence.save(worker);
	}

	@Override
	public IWorker getWorkerByLogin(String login) {
		return workerPersistence.getWorkerByLogin(login);
	}

}
