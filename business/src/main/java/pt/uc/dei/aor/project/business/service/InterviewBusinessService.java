package pt.uc.dei.aor.project.business.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import pt.uc.dei.aor.project.business.exception.GenericIllegalParamsException;
import pt.uc.dei.aor.project.business.exception.IllegalInterviewDeletionException;
import pt.uc.dei.aor.project.business.exception.RepeatedInterviewException;
import pt.uc.dei.aor.project.business.filter.InterviewFilter;
import pt.uc.dei.aor.project.business.model.IAnswer;
import pt.uc.dei.aor.project.business.model.IApplication;
import pt.uc.dei.aor.project.business.model.IInterview;
import pt.uc.dei.aor.project.business.model.IModelFactory;
import pt.uc.dei.aor.project.business.model.IPosition;
import pt.uc.dei.aor.project.business.model.IScript;
import pt.uc.dei.aor.project.business.model.IScriptEntry;
import pt.uc.dei.aor.project.business.model.IWorker;
import pt.uc.dei.aor.project.business.persistence.IAnswerPersistenceService;
import pt.uc.dei.aor.project.business.persistence.IApplicationPersistenceService;
import pt.uc.dei.aor.project.business.persistence.IInterviewPersistenceService;
import pt.uc.dei.aor.project.business.persistence.IWorkerPersistenceService;
import pt.uc.dei.aor.project.business.util.QuestionType;


@Stateless
public class InterviewBusinessService implements IInterviewBusinessService {

	@Inject
	private IInterviewPersistenceService interviewPersistence;
	
	@Inject
	private IWorkerPersistenceService workerPersistence;
	
	@Inject
	private IApplicationPersistenceService applicationPersistence;
	
	@Inject
	private IAnswerPersistenceService answerPersistence;
	
	@Inject
	private IModelFactory factory;
	
	@Inject
	private INotificationBusinessService notificationService;
	
	
	@Override
	public List<IInterview> findActiveInterviewsByUser(IWorker user) {
		return interviewPersistence.findActiveInterviewsByUser(user);
	}


	@Override
	public IApplication addInterview(IApplication application, Date date, Collection<IWorker> interviewers) 
			throws GenericIllegalParamsException, RepeatedInterviewException {
		if (date == null || application == null 
				|| interviewers == null) throw new GenericIllegalParamsException();
		
		IInterview interview = factory.interview(application, date);
		
		IInterview existingInterview = interviewPersistence.findInterview(interview);
		if (existingInterview != null) throw new RepeatedInterviewException();
		
		interview = interviewPersistence.save(interview);
		
		for (IWorker w : interviewers) {
			workerPersistence.insertInterview(w.getId(), interview);

			// notify user
			notificationService.notify(w, "Interview sheduled", "Interview Scheduled");
		}
		

		
		return applicationPersistence.find(application.getId());
	}

	@Override
	public List<IInterview> findInterviewsByApplication(IApplication application) {
		return applicationPersistence.find(application.getId()).getInterviews();
	}


	@Override
	public IApplication delete(IInterview interview) throws IllegalInterviewDeletionException {
		List<IAnswer> answers = findAnswersByInterview(interview);
		if (!answers.isEmpty()) throw new IllegalInterviewDeletionException();
		
		for (IWorker w : interview.getInterviewers()) {
			workerPersistence.removeInterview(w.getId(), interview.getId());
		}
		
		interviewPersistence.delete(interview);
		
		return applicationPersistence.find(interview.getApplication().getId());
	}


	@Override
	public IInterview findInterviewById(long id) {
		return interviewPersistence.findInterviewById(id);
	}


	@Override
	public List<IScriptEntry> getScriptEntries(IInterview interview) {
		IApplication application = interview.getApplication();
		IPosition position = application.getPosition();
		IScript script = position.getScript();
		
		if (script == null) return null;
		
		return script.getEntries();
	}


	@Override
	public IAnswer saveAnswer(IInterview interview, String answer, IScriptEntry entry) {
		IAnswer answerProxy = answerPersistence.findAnswerByInterviewAndQuestion(interview, entry.getText());
		
		if (answerProxy == null) answerProxy = factory.answer(interview, answer.trim(), entry);
		else answerProxy.setAnswer(answer);
		
		return answerPersistence.save(answerProxy);
	}


	@Override
	public String findAnswerByInterviewAndQuestion(IInterview interview, String text) {
		IAnswer answerProxy = answerPersistence.findAnswerByInterviewAndQuestion(interview, text);
		if (answerProxy == null) return null;
		return answerProxy.getAnswer();
	}


	@Override
	public List<IAnswer> findAnswersByInterview(IInterview interview) {
		return answerPersistence.findAnswersByInterview(interview);
	}


	@Override
	public List<IScriptEntry> getCompleteScriptEntries(IInterview interview) {
		List<IScriptEntry> entries = getScriptEntries(interview);
		entries.add(factory.scriptEntry(QuestionType.LONG_ANSWER, 
				"Interview's Global Appreciation", entries.size()));
		
		return entries;
	}


	@Override
	public List<IInterview> findInterviews(int offset, int limit) {
		return interviewPersistence.findInterviewsWithFilter(offset, limit, null);
	}


	@Override
	public List<IInterview> findInterviews(int offset, int limit, InterviewFilter filter) {
		return interviewPersistence.findInterviewsWithFilter(offset, limit, filter);
	}
	
}
