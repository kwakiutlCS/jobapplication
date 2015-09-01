package pt.uc.dei.aor.project.persistence.proxy;

import java.util.Collection;
import java.util.Date;

import javax.ejb.Stateless;

import pt.uc.dei.aor.project.business.model.IAnswerChoice;
import pt.uc.dei.aor.project.business.model.IApplication;
import pt.uc.dei.aor.project.business.model.ICandidate;
import pt.uc.dei.aor.project.business.model.IInterview;
import pt.uc.dei.aor.project.business.model.IModelFactory;
import pt.uc.dei.aor.project.business.model.IPosition;
import pt.uc.dei.aor.project.business.model.IPublicationChannel;
import pt.uc.dei.aor.project.business.model.IScript;
import pt.uc.dei.aor.project.business.model.IWorker;
import pt.uc.dei.aor.project.business.util.Localization;
import pt.uc.dei.aor.project.business.util.PositionState;
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
	public IPosition position(String code, Date openingDate, String title,
			Collection<Localization> localizations, PositionState state,
			int vacancies, Date closingDate, String sla, String contactPerson,
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
	public ICandidate candidate(String login, String name, String surname, String email, String encrypt) {
		return new CandidateProxy(login, email, encrypt, name, surname);
	}

	@Override
	public IInterview interview(IApplication application, Date date, Collection<IWorker> interviewers) {
		return new InterviewProxy(application, date, interviewers);
	}

	@Override
	public IApplication application() {
		return new ApplicationProxy();
	}

	
}
