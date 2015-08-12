package pt.uc.dei.aor.project.business.model;

import java.time.LocalDate;

public interface IModelFactory {

	IWorker worker(String login, String email, String password, String name, String surname);	

	IPosition position (LocalDate openingDate, String title, int vacancies,	LocalDate closingDate, String sla, 
			String contactPerson, String company, String description);

	IPublicationChanhel publicationChannel(String channel);










}
