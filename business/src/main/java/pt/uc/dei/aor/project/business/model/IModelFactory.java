package pt.uc.dei.aor.project.business.model;

import java.util.Date;

import pt.uc.dei.aor.project.business.util.Localization;

public interface IModelFactory {


	IWorker worker(String login, String email, String password, String name, String surname);	

	IPosition position (Date openingDate, String title, Localization localization, int vacancies, Date closingDate, String sla, 
			String contactPerson, String company ,String description);

	IPublicationChanhel publicationChannel(String channel);

	IScript script();	


}
