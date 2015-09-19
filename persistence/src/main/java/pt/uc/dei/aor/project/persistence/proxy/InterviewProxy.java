package pt.uc.dei.aor.project.persistence.proxy;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.uc.dei.aor.project.business.model.IApplication;
import pt.uc.dei.aor.project.business.model.ICandidate;
import pt.uc.dei.aor.project.business.model.IInterview;
import pt.uc.dei.aor.project.business.model.IWorker;
import pt.uc.dei.aor.project.persistence.entity.InterviewEntity;
import pt.uc.dei.aor.project.persistence.entity.WorkerEntity;
import pt.uc.dei.aor.project.persistence.service.GenericPersistenceService;

public class InterviewProxy implements IInterview, IProxyToEntity<InterviewEntity> {
	
	private static Logger logger = LoggerFactory.getLogger(InterviewProxy.class);
	
	private InterviewEntity entity;
	
	public InterviewProxy(InterviewEntity entity) {
		this.entity = entity != null ? entity : new InterviewEntity();
	}

	
	public InterviewProxy(Date date) {
		this.entity = new InterviewEntity(date);
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


	@Override
	public Date getDateObject() {
		return entity.getDate();
	}


	@Override
	public int getInterviewPhase() {
		IApplication application = this.getApplication();
		List<IInterview> interviews = application.getInterviews();
		
		int counter = 1;
		for (IInterview i : interviews) {
			if (i.equals(this)) return counter;
			counter++;
		}
		
		return -1;
	}

	@Override
	public int hashCode() {
		return entity.hashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null) return false;
		if (!(o instanceof IInterview)) return false;
		return entity.equals(GenericPersistenceService.getEntity(o));
	}
}
