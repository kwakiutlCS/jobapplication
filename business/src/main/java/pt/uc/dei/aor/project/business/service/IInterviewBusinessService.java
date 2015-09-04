package pt.uc.dei.aor.project.business.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import pt.uc.dei.aor.project.business.model.IApplication;
import pt.uc.dei.aor.project.business.model.IInterview;
import pt.uc.dei.aor.project.business.model.IScriptEntry;
import pt.uc.dei.aor.project.business.model.IWorker;

public interface IInterviewBusinessService {
	
	List<IInterview> findActiveInterviewsByUser(IWorker user);

	IApplication addInterview(IApplication application, Date date, Collection<IWorker> interviewers);

	List<IInterview> findInterviewsByApplication(IApplication selected);

	IApplication delete(IInterview interview);

	IInterview findInterviewById(long id);

	List<IScriptEntry> getScriptEntries(IInterview interview);
	
}
