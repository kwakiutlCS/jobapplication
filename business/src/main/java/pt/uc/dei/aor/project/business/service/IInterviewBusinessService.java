package pt.uc.dei.aor.project.business.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import pt.uc.dei.aor.project.business.exception.GenericIllegalParamsException;
import pt.uc.dei.aor.project.business.exception.IllegalInterviewDeletionException;
import pt.uc.dei.aor.project.business.exception.RepeatedInterviewException;
import pt.uc.dei.aor.project.business.model.IAnswer;
import pt.uc.dei.aor.project.business.model.IApplication;
import pt.uc.dei.aor.project.business.model.IInterview;
import pt.uc.dei.aor.project.business.model.IScriptEntry;
import pt.uc.dei.aor.project.business.model.IWorker;

public interface IInterviewBusinessService {
	
	List<IInterview> findActiveInterviewsByUser(IWorker user);

	IApplication addInterview(IApplication application, Date date, Collection<IWorker> interviewers) 
			throws GenericIllegalParamsException, RepeatedInterviewException;

	List<IInterview> findInterviewsByApplication(IApplication selected);

	IApplication delete(IInterview interview) throws IllegalInterviewDeletionException;

	IInterview findInterviewById(long id);

	List<IScriptEntry> getScriptEntries(IInterview interview);

	IAnswer saveAnswer(IInterview interview, String answer, IScriptEntry entry);

	String findAnswerByInterviewAndQuestion(IInterview interview, String text);

	List<IAnswer> findAnswersByInterview(IInterview interview);

	List<IScriptEntry> getCompleteScriptEntries(IInterview interview);
	
}
