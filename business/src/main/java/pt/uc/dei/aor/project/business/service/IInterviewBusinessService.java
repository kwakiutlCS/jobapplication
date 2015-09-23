package pt.uc.dei.aor.project.business.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import pt.uc.dei.aor.project.business.exception.AllPhasesCompletedException;
import pt.uc.dei.aor.project.business.exception.GenericIllegalParamsException;
import pt.uc.dei.aor.project.business.exception.IllegalInterviewDeletionException;
import pt.uc.dei.aor.project.business.exception.RepeatedInterviewException;
import pt.uc.dei.aor.project.business.filter.InterviewFilter;
import pt.uc.dei.aor.project.business.model.IAnswer;
import pt.uc.dei.aor.project.business.model.IApplication;
import pt.uc.dei.aor.project.business.model.ICandidate;
import pt.uc.dei.aor.project.business.model.IInterview;
import pt.uc.dei.aor.project.business.model.IScriptEntry;
import pt.uc.dei.aor.project.business.model.IWorker;

public interface IInterviewBusinessService {
	
	List<IInterview> findActiveInterviewsByUser(IWorker user);

	IApplication addInterview(IApplication application, Date date, Collection<IWorker> interviewers) 
			throws GenericIllegalParamsException, RepeatedInterviewException, AllPhasesCompletedException;

	List<IInterview> findInterviewsByApplication(IApplication selected);

	IApplication delete(IApplication selectedApplication, IInterview interview) throws IllegalInterviewDeletionException;

	IInterview findInterviewById(long id);

	List<IScriptEntry> getScriptEntries(IInterview interview) throws AllPhasesCompletedException;

	IAnswer saveAnswer(IInterview interview, String answer, IScriptEntry entry) throws AllPhasesCompletedException;

	String findAnswerByInterviewAndQuestion(IInterview interview, String text);

	List<IAnswer> findAnswersByInterview(IInterview interview);

	List<IScriptEntry> getCompleteScriptEntries(IInterview interview) throws AllPhasesCompletedException;

	List<IInterview> findInterviews(int offset, int limit);

	List<IInterview> findInterviews(int offset, int limit, InterviewFilter filter);

	IInterview saveInterview(IInterview interview) throws IllegalInterviewDeletionException;

	IInterview addInterviewer(IInterview interview, IWorker interviewer);

	IInterview removeInterviewer(IInterview interview, IWorker interviewer);

	IInterview setInterviewers(IInterview interview, Collection<IWorker> selectedInterviewers);

	List<IInterview> getPreviousInterviews(IInterview interview);

	List<IInterview> getHistoricInterviews(IInterview interview);

	Set<IInterview> findPastInterviews(IApplication selectedApplication) throws AllPhasesCompletedException;

	Set<IInterview> findFutureInterviews(IApplication selectedApplication) throws AllPhasesCompletedException;

	boolean isCompleted(IInterview interview) throws AllPhasesCompletedException;
	
}
