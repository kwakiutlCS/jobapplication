package pt.uc.dei.aor.project.persistence.proxy;

import javax.ejb.Stateless;

import pt.uc.dei.aor.project.business.model.IModelFactory;
import pt.uc.dei.aor.project.business.model.IWorker;

@Stateless
public class ModelFactory implements IModelFactory {

	@Override
	public IWorker worker(String login, String email, String password, String name, String surname) {
		return new WorkerProxy(login, email, password, name, surname);
	}

}
