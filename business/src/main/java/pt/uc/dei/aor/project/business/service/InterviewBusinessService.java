package pt.uc.dei.aor.project.business.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import pt.uc.dei.aor.project.business.model.IApplication;
import pt.uc.dei.aor.project.business.model.IInterview;
import pt.uc.dei.aor.project.business.model.IModelFactory;
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
	public Collection<IInterview> findActiveInterviewsByUser(IWorker user) {
		return interviewPersistence.findActiveInterviewsByUser(user);
	}


	@Override
	public IApplication addInterview(IApplication application, Date date, Collection<IWorker> interviewers) {
		IInterview interview = factory.interview(application, date);
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
	
}
