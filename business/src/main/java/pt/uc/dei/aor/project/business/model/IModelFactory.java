package pt.uc.dei.aor.project.business.model;

import java.util.Collection;
import java.util.Date;

import pt.uc.dei.aor.project.business.util.Localization;
import pt.uc.dei.aor.project.business.util.PositionState;
import pt.uc.dei.aor.project.business.util.Role;
import pt.uc.dei.aor.project.business.util.TechnicalArea;

public interface IModelFactory {

	ICandidate candidate(String login, String name, String surname, String email, String encrypt);	

	IWorker worker(String login, String email, String password, String name, 
			String surname, Collection<Role> roles);	

	IPosition position(long code, Date openingDate, String title, Collection<Localization> localizations, PositionState state, int vacancies, Date closingDate, int sla, 
			String contactPerson, String company ,Collection<TechnicalArea> technicalAreas, String description, IScript script, Collection<IPublicationChannel> channels);

	IPublicationChannel publicationChannel(String channel);

	IScript script(String title);	

	IAnswerChoice answerChoice(String answer);

	IApplication application();

	IInterview interview(IApplication application, Date date, Collection<IWorker> interviewers);
}
