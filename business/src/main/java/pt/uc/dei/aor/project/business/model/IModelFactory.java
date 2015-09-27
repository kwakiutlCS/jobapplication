package pt.uc.dei.aor.project.business.model;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import pt.uc.dei.aor.project.business.util.Localization;
import pt.uc.dei.aor.project.business.util.PositionState;
import pt.uc.dei.aor.project.business.util.QuestionType;
import pt.uc.dei.aor.project.business.util.Role;
import pt.uc.dei.aor.project.business.util.TechnicalArea;

public interface IModelFactory {

	IUser user(String login, String email, String password, String name, String surname, Collection<Role> roles);	
	
	IPublicationChannel publicationChannel(String channel);

	IScript script(String title);	

	IAnswerChoice answerChoice(String answer);

	IApplication application();

	IInterview interview(Date date);

	IAnswer answer(IInterview interview, String answer, IScriptEntry entry);

	IScriptEntry scriptEntry(QuestionType questionType, String question, int position);

	INotification workerNotification(String msg, IUser worker, String type);

	ISchool school(String school);

	IDegree degree(ISchool school, String degree, String typeDegree);

	IQualification qualification(String school, String degree);

	IPosition position(long code, Date openingDate, String title, Collection<Localization> localizations,
			PositionState state, int vacancies, Date closingDate, int sla, IUser contactPerson, String company,
			Collection<TechnicalArea> technicalAreas, String description, List<IScript> scripts,
			Collection<IPublicationChannel> channels);

	IProposition proposition();

	IColor color(String page, String header, String content, String contentText, String contentTitle, String headerText,
			String background);
}
