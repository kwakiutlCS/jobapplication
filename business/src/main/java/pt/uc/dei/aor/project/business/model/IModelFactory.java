package pt.uc.dei.aor.project.business.model;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import pt.uc.dei.aor.project.business.util.Localization;
import pt.uc.dei.aor.project.business.util.PositionState;
import pt.uc.dei.aor.project.business.util.Role;
import pt.uc.dei.aor.project.business.util.TechnicalArea;

public interface IModelFactory {


	IWorker worker(String login, String email, String password, String name, 
			String surname, Collection<Role> roles);	

	IPosition position(Date openingDate, String title, List<Localization> localizations, PositionState state, int vacancies, Date closingDate, String sla, 
			String contactPerson, String company ,List<TechnicalArea> technicalAreas, String description, IScript script);

	IPublicationChanhel publicationChannel(String channel);

	IScript script();	


}
