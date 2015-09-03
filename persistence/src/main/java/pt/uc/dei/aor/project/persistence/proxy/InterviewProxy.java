package pt.uc.dei.aor.project.persistence.proxy;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

	
	@SuppressWarnings("unchecked")
	public InterviewProxy(IApplication application, Date date) {
		ApplicationEntity applicationEntity = null;
		
		if (application instanceof IProxyToEntity<?>)
			applicationEntity = ((IProxyToEntity<ApplicationEntity>) application).getEntity();
		else throw new IllegalStateException();
		
		
		this.entity = new InterviewEntity(applicationEntity, date);
	}


	@Override
	public InterviewEntity getEntity() {
		return entity;
	}


	@Override
	public String getDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		return sdf.format(entity.getDate());
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


	@Override
	public long getId() {
		return entity.getId();
	}


	@Override
	public boolean isEditable() {
		Calendar today = Calendar.getInstance();
		Calendar interviewDate = Calendar.getInstance();
		interviewDate.setTime(entity.getDate());
		
		return interviewDate.after(today);
	}


	@Override
	public String getInterviewersFormatted() {
		StringBuilder s = new StringBuilder("");
		for (IWorker interviewer : getInterviewers()) {
			if (s.length() > 0) s.append(", ");
			s.append(interviewer.getFullName());
		}
		
		return s.toString();
	}

	
}
