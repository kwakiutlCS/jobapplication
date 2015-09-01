package pt.uc.dei.aor.project.persistence.proxy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import pt.uc.dei.aor.project.business.model.IApplication;
import pt.uc.dei.aor.project.business.model.ICandidate;
import pt.uc.dei.aor.project.business.model.IInterview;
import pt.uc.dei.aor.project.business.model.IWorker;
import pt.uc.dei.aor.project.persistence.entity.ApplicationEntity;
import pt.uc.dei.aor.project.persistence.entity.InterviewEntity;
import pt.uc.dei.aor.project.persistence.entity.WorkerEntity;

public class InterviewProxy implements IInterview, IProxyToEntity<InterviewEntity> {

	private InterviewEntity entity;
	
	public InterviewProxy(InterviewEntity entity) {
		this.entity = entity != null ? entity : new InterviewEntity();
	}

	
	public InterviewProxy(IApplication application, Date date, Collection<IWorker> interviewers) {
		ApplicationEntity applicationEntity = null;
		List<WorkerEntity> workerEntities = new ArrayList<>();
		
		if (application instanceof IProxyToEntity<?>)
			applicationEntity = ((IProxyToEntity<ApplicationEntity>) application).getEntity();
		else throw new IllegalStateException();
		
		for (IWorker wp : interviewers) {
			if (wp instanceof IProxyToEntity<?>) 
				workerEntities.add(((IProxyToEntity<WorkerEntity>) wp).getEntity());
			else throw new IllegalStateException();
		}
		
		this.entity = new InterviewEntity(applicationEntity, date, workerEntities);
	}


	@Override
	public InterviewEntity getEntity() {
		return entity;
	}


	@Override
	public Date getDate() {
		return entity.getDate();
	}


	@Override
	public IApplication getApplication() {
		return new ApplicationProxy(entity.getApplication());
	}


	@Override
	public ICandidate getCandidate() {
		return getApplication().getCandidate();
	}


	@Override
	public Collection<IWorker> getInterviewers() {
		List<IWorker> proxies = new ArrayList<>();
		Collection<WorkerEntity> entities = entity.getInterviewers();
		
		for (WorkerEntity we : entities) 
			proxies.add(new WorkerProxy(we));
		
		return proxies;
	}

	
}
