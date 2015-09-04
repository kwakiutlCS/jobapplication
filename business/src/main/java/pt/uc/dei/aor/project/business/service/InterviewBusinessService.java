package pt.uc.dei.aor.project.business.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import pt.uc.dei.aor.project.business.exception.GenericIllegalParamsException;
import pt.uc.dei.aor.project.business.exception.RepeatedInterviewException;
import pt.uc.dei.aor.project.business.model.IApplication;
import pt.uc.dei.aor.project.business.model.IInterview;
import pt.uc.dei.aor.project.business.model.IModelFactory;
import pt.uc.dei.aor.project.business.model.IPosition;
import pt.uc.dei.aor.project.business.model.IScript;
import pt.uc.dei.aor.project.business.model.IScriptEntry;
import pt.uc.dei.aor.project.business.model.IWorker;
import pt.uc.dei.aor.project.business.persistence.IApplicationPersistenceService;
import pt.uc.dei.aor.project.business.persistence.IInterviewPersistenceService;
import pt.uc.dei.aor.project.business.persistence.IWorkerPersistenceService;


@Stateless
public class InterviewBusinessService implements IInterviewBusinessService {

	@Inject
	private IInterviewPersistenceService interviewPersistence;
	
	@Inject
	private IWorkerPersistenceService workerPersistence;
	
	@Inject
	private IApplicationPersistenceService applicationPersistence;
	
	@Inject
	private IModelFactory factory;
	
	
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
		}
		
		return applicationPersistence.find(application.getId());
	}

	@Override
	public List<IInterview> findInterviewsByApplication(IApplication application) {
		return applicationPersistence.find(application.getId()).getInterviews();
	}


	@Override
	public IApplication delete(IInterview interview) {
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
	
}
