package pt.uc.dei.aor.project.persistence.proxy;

import java.time.LocalDate;

import javax.ejb.Stateless;

import pt.uc.dei.aor.project.business.model.IModelFactory;
import pt.uc.dei.aor.project.business.model.IPosition;
import pt.uc.dei.aor.project.business.model.IPublicationChanhel;
import pt.uc.dei.aor.project.business.model.IWorker;

@Stateless
public class ModelFactory implements IModelFactory {

	@Override
	public IWorker worker(String login, String email, String password, String name, String surname) {
		return new WorkerProxy(login, email, password, name, surname);
	}

	@Override
	public IPosition position(LocalDate openingDate, String title,
			int vacancy, LocalDate closingDate, String sla,
			String responsableName, String company, String description) {
		return new PositionProxy(openingDate, title, vacancy,closingDate, sla,
				responsableName, company, description);
	}

	@Override
	public IPublicationChanhel publicationChannel(String channel) {
		// TODO Auto-generated method stub
		return null;
	}



}
