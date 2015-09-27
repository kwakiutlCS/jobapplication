package pt.uc.dei.aor.project.business.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.uc.dei.aor.project.business.exception.AllPhasesCompletedException;
import pt.uc.dei.aor.project.business.exception.GenericIllegalParamsException;
import pt.uc.dei.aor.project.business.exception.IllegalInterviewDeletionException;
import pt.uc.dei.aor.project.business.exception.RepeatedInterviewException;
import pt.uc.dei.aor.project.business.filter.InterviewFilter;
import pt.uc.dei.aor.project.business.model.IAnswer;
import pt.uc.dei.aor.project.business.model.IApplication;
import pt.uc.dei.aor.project.business.model.ICandidate;
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
import pt.uc.dei.aor.project.business.util.EmailUtil;
import pt.uc.dei.aor.project.business.util.QuestionType;


@Stateless
public class InterviewBusinessService implements IInterviewBusinessService {

	private static Logger logger = LoggerFactory.getLogger(InterviewBusinessService.class);
	
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
	private EmailUtil emailUtil;
	
	@Inject
	private INotificationBusinessService notificationService;
	
	
	@Override
	public List<IInterview> findActiveInterviewsByUser(IWorker user) {
		return interviewPersistence.findActiveInterviewsByUser(user);
	}


	@Override
	public IApplication addInterview(IApplication application, Date date, Collection<IWorker> interviewers) 
			throws GenericIllegalParamsException, RepeatedInterviewException, AllPhasesCompletedException {
		if (date == null || application == null 
				|| interviewers == null) throw new GenericIllegalParamsException();
		
		if (application.reachedAllPhases()) {
			throw new AllPhasesCompletedException();
		}
		
		IInterview interview = factory.interview(date);
		
		application.addInterview(interview);
		
		try {
			application = applicationPersistence.save(application);
			interview = application.getInterviewByDate(date);
			
			for (IWorker w : interviewers) {
				workerPersistence.insertInterview(w.getId(), interview);
			}
			
			for (IWorker w : interviewers) {
				String company = interview.getApplication().getPosition().getCompany();

				// notify user
				String title = "Interview Scheduled";
				String msg = "Interview for position "+
						interview.getApplication().getPosition().getTitle()+" was created and assigned you as interviewer.\n\nDate: "+
						interview.getDate()+
						"\nCandidate: "+interview.getCandidate().getFullName()+
						"\nInterviewers: "+interview.getInterviewersFormatted();
				
				String msgEmail = "<h1>"+company+"</h1>"+
						"<p>Interview for position "+
						interview.getApplication().getPosition().getTitle()+" was created and assigned you as interviewer.</p>"+
						"<p>Date: "+interview.getDate()+"</p>"+
						"<p>Candidate: "+interview.getCandidate().getFullName()+"</p>"+
						"<p>Interviewers: "+interview.getInterviewersFormatted()+
						"<p><a href='http://localhost:8080/jobmanagement/interview/interview.xhtml?it="+
						interview.getId()+"'>Details</a></p>";
				
				notificationService.notify(w, msg, title);
				
				emailUtil.send(w.getEmail(), msgEmail, title);
//				, interview.getCandidate().getLogin()+"/",
//				interview.getCandidate().getCv()
			}
			
			return application;
		}
		catch (Exception e) {
			throw new GenericIllegalParamsException();
		}
	}

	@Override
	public List<IInterview> findInterviewsByApplication(IApplication application) {
		return applicationPersistence.find(application.getId()).getInterviews();
	}


	@Override
	public IApplication delete(IApplication application, IInterview interview) 
			throws IllegalInterviewDeletionException {
		List<IAnswer> answers = findAnswersByInterview(interview);
		if (!answers.isEmpty()) throw new IllegalInterviewDeletionException();
		
		application.remove(interview);
		application = applicationPersistence.save(application);
		
		for (IWorker w : interview.getInterviewers()) {
			workerPersistence.removeInterview(w.getId(), interview.getId());
		}
		
		interviewPersistence.delete(interview);
		
		return application;
	}


	@Override
	public IInterview findInterviewById(long id) {
		return interviewPersistence.findInterviewById(id);
	}


	@Override
	public List<IScriptEntry> getScriptEntries(IInterview interview) throws AllPhasesCompletedException {
		IApplication application = interview.getApplication();
		IPosition position = application.getPosition();
		int phase = interview.getInterviewPhase();
		
		List<IScript> scripts = position.getScripts();
		
		if (scripts.size() < phase || phase == -1) return null;
		IScript script = scripts.get(phase-1);
		
		return script.getEntries();
	}


	@Override
	public IAnswer saveAnswer(IInterview interview, String answer, IScriptEntry entry) throws AllPhasesCompletedException {
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
	public List<IScriptEntry> getCompleteScriptEntries(IInterview interview) throws AllPhasesCompletedException {
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


	@Override
	public IInterview saveInterview(IInterview interview) throws IllegalInterviewDeletionException {
		List<IAnswer> answers = findAnswersByInterview(interview);
		if (!answers.isEmpty()) throw new IllegalInterviewDeletionException();
		
		return interviewPersistence.save(interview);
	}


	@Override
	public IInterview addInterviewer(IInterview interview, IWorker interviewer) {
		interview.addInterviewer(interviewer);
		return interviewPersistence.save(interview);
	}


	@Override
	public IInterview removeInterviewer(IInterview interview, IWorker interviewer) {
		interview.removeInterviewer(interviewer);
		return interviewPersistence.save(interview);
	}


	@Override
	public IInterview setInterviewers(IInterview interview, Collection<IWorker> selectedInterviewers) {
		for (IWorker w : selectedInterviewers) {
			workerPersistence.insertInterview(w.getId(), interview);
		}
		for (IWorker w : interview.getInterviewers()) {
			if (!selectedInterviewers.contains(w))
				workerPersistence.removeInterview(w.getId(), interview.getId());
		}
		
		return interviewPersistence.save(interview);
	}


	@Override
	public List<IInterview> getPreviousInterviews(IInterview interview) {
		List<IInterview> interviews = new ArrayList<>();
		
		IApplication app = interview.getApplication();
		
		Calendar date = Calendar.getInstance();
		date.setTime(interview.getDateObject());
		Calendar cal = Calendar.getInstance();
		
		for (IInterview i : app.getInterviews()) {
			cal.setTime(i.getDateObject());
			if (cal.before(date)) interviews.add(i);
		}
		
		return interviews;
	}


	@Override
	public List<IInterview> getHistoricInterviews(IInterview interview) {
		return interviewPersistence.findPastInterviewsByUser(interview.getCandidate(), 
				interview.getApplication(), interview.getDateObject());
	}


	@Override
	public Set<IInterview> findPastInterviews(IApplication application) throws AllPhasesCompletedException {
		Set<IInterview> past = new TreeSet<>();
		List<IInterview> all = interviewPersistence.findInterviewsByApplication(application);
		
		for (IInterview i : all) {
			if (isCompleted(i)) past.add(i);
		}
		
		return past;
	}

	@Override
	public Set<IInterview> findFutureInterviews(IApplication application) throws AllPhasesCompletedException {
		Set<IInterview> future = new TreeSet<>();
		List<IInterview> all = interviewPersistence.findInterviewsByApplication(application);
		
		for (IInterview i : all) {
			if (!isCompleted(i)) future.add(i);
		}
		
		return future;
	}


	@Override
	public boolean isCompleted(IInterview interview) throws AllPhasesCompletedException {
		return interviewPersistence.isCompleted(interview);
	}
	
}
