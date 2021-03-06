package pt.uc.dei.aor.project.persistence.proxy;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

import pt.uc.dei.aor.project.business.model.IAnswer;
import pt.uc.dei.aor.project.business.model.IAnswerChoice;
import pt.uc.dei.aor.project.business.model.IApplication;
import pt.uc.dei.aor.project.business.model.IColor;
import pt.uc.dei.aor.project.business.model.IDegree;
import pt.uc.dei.aor.project.business.model.IImage;
import pt.uc.dei.aor.project.business.model.IInterview;
import pt.uc.dei.aor.project.business.model.IModelFactory;
import pt.uc.dei.aor.project.business.model.INotification;
import pt.uc.dei.aor.project.business.model.IPosition;
import pt.uc.dei.aor.project.business.model.IPresentationText;
import pt.uc.dei.aor.project.business.model.IProposition;
import pt.uc.dei.aor.project.business.model.IPublicationChannel;
import pt.uc.dei.aor.project.business.model.IQualification;
import pt.uc.dei.aor.project.business.model.ISchool;
import pt.uc.dei.aor.project.business.model.IScript;
import pt.uc.dei.aor.project.business.model.IScriptEntry;
import pt.uc.dei.aor.project.business.model.IUser;
import pt.uc.dei.aor.project.business.util.Localization;
import pt.uc.dei.aor.project.business.util.PositionState;
import pt.uc.dei.aor.project.business.util.QuestionType;
import pt.uc.dei.aor.project.business.util.Role;
import pt.uc.dei.aor.project.business.util.TechnicalArea;

@Stateless
public class ModelFactory implements IModelFactory {


	@Override
	public IUser user(String login, String email, String password, String name, 
			String surname, Collection<Role> roles) {
		return new UserProxy(login, email, password, name, surname, roles);
	}

	@Override
	public IPosition position(long code, Date openingDate, String title,
			Collection<Localization> localizations, PositionState state,
			int vacancies, Date closingDate, int sla, IUser contactPerson,
			String company, Collection<TechnicalArea> technicalAreas,
			String description, List<IScript> scripts,
			Collection<IPublicationChannel> channels) {

		return new PositionProxy(code, openingDate, title,localizations, state, vacancies,closingDate, sla,
				contactPerson, company, technicalAreas, description, scripts, channels);
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
	public IInterview interview(Date date) {
		return new InterviewProxy(date);
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
	public INotification notification(String msg, IUser worker, String type) {
		return new NotificationProxy(msg, worker, type);
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

	@Override
	public IProposition proposition() {
		return new PropositionProxy();
	}

	@Override
	public IColor color(String page, String header, String content, String contentText, String contentTitle,
			String headerText, String background) {
		return new ColorProxy(page, header, content, contentText, contentTitle, headerText, background);
	}

	@Override
	public IApplication application(String coverLetter, String cv,String sourceInfo, Date date, IUser candidate, IPosition position) {
		
		return new ApplicationProxy(coverLetter, cv, sourceInfo, date, candidate, position);
	}

	@Override
	public IApplication application(String coverLetter, String cv,String sourceInfo, Date date, IUser candidate) {
		
		return new ApplicationProxy(coverLetter, cv, sourceInfo, date, candidate);
	}

	
	@Override
	public IUser user(String login, String email, String encrypt, String name, String surname, String phone, String mobilePhone, String address,
			String city, String country, List<IQualification> qualifications, String cv) {
		return new UserProxy(login, email, encrypt, name, surname, phone, mobilePhone, address, city, country, qualifications,cv);
	}

	@Override
	public IColor color() {
		return new ColorProxy();
	}

	@Override
	public IImage image(String title) {
		return new ImageProxy(title);
	}

	@Override
	public IPresentationText insertText() {
		// TODO Auto-generated method stub
		return null;
	}



}
