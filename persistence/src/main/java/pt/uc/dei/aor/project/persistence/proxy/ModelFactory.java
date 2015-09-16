package pt.uc.dei.aor.project.persistence.proxy;

import java.util.Collection;
import java.util.Date;

import javax.ejb.Stateless;

import pt.uc.dei.aor.project.business.model.IAnswer;
import pt.uc.dei.aor.project.business.model.IAnswerChoice;
import pt.uc.dei.aor.project.business.model.IApplication;
import pt.uc.dei.aor.project.business.model.ICandidate;
import pt.uc.dei.aor.project.business.model.IDegree;
import pt.uc.dei.aor.project.business.model.IInterview;
import pt.uc.dei.aor.project.business.model.IModelFactory;
import pt.uc.dei.aor.project.business.model.IPosition;
import pt.uc.dei.aor.project.business.model.IPublicationChannel;
import pt.uc.dei.aor.project.business.model.IQualification;
import pt.uc.dei.aor.project.business.model.ISchool;
import pt.uc.dei.aor.project.business.model.IScript;
import pt.uc.dei.aor.project.business.model.IScriptEntry;
import pt.uc.dei.aor.project.business.model.IWorker;
import pt.uc.dei.aor.project.business.model.IWorkerNotification;
import pt.uc.dei.aor.project.business.util.Localization;
import pt.uc.dei.aor.project.business.util.PositionState;
import pt.uc.dei.aor.project.business.util.QuestionType;
import pt.uc.dei.aor.project.business.util.Role;
import pt.uc.dei.aor.project.business.util.TechnicalArea;

@Stateless
public class ModelFactory implements IModelFactory {


	@Override
	public IWorker worker(String login, String email, String password, String name, 
			String surname, Collection<Role> roles) {
		return new WorkerProxy(login, email, password, name, surname, roles);
	}

	@Override
	public IPosition position(long code, Date openingDate, String title,
			Collection<Localization> localizations, PositionState state,
			int vacancies, Date closingDate, int sla, String contactPerson,
			String company, Collection<TechnicalArea> technicalAreas,
			String description, IScript script,
			Collection<IPublicationChannel> channels) {

		return new PositionProxy(code, openingDate, title,localizations, state, vacancies,closingDate, sla,
				contactPerson, company, technicalAreas, description, script, channels);
	}

	@Override
	public IPublicationChannel publicationChannel(String channel) {	
		return new PublicationChannelProxy(channel);
	}

	@Override
	public IScript script(String title) {
		return new ScriptProxy(title);
	}

	@Override
	public IAnswerChoice answerChoice(String answer) {
		return new AnswerChoiceProxy(answer);
	}


	@Override
	public ICandidate candidate(String login, String name, String surname,
			String email, String password, String address, String city,
			String country, String phone, String mobilePhone,
			Collection<IQualification> qualifications, String cv,
			Collection<IApplication> applications) {

		return new CandidateProxy(login, email, password,name, surname, address, city,country, 
				phone, mobilePhone,	qualifications, cv, applications);
	}

	@Override
	public IInterview interview(Date date) {
		return new InterviewProxy(date);
	}

	@Override
	public IApplication application() {
		return new ApplicationProxy();
	}

	@Override
	public IAnswer answer(IInterview interview, String answer, IScriptEntry entry) {
		return new AnswerProxy(interview, answer, entry);
	}

	@Override
	public IScriptEntry scriptEntry(QuestionType questionType, String question, int position) {
		return new ScriptEntryProxy(questionType, question, position);
	}

	@Override
	public IWorkerNotification workerNotification(String msg, IWorker worker, String type) {
		return new WorkerNotificationProxy(msg, worker, type);
	}

	@Override
	public ISchool school(String school) {
		return new SchoolProxy(school);
	}

	@Override
	public IDegree degree(ISchool school, String degree, String typeDegree) {
		return new DegreeProxy(school, degree, typeDegree);
	}

	@Override
	public IQualification qualification(String school, String degree) {
		return new QualificationProxy(school, degree);
	}



}
