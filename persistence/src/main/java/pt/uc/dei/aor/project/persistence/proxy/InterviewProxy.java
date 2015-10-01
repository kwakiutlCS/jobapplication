package pt.uc.dei.aor.project.persistence.proxy;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.uc.dei.aor.project.business.exception.AllPhasesCompletedException;
import pt.uc.dei.aor.project.business.model.IApplication;
import pt.uc.dei.aor.project.business.model.IInterview;
import pt.uc.dei.aor.project.business.model.IUser;
import pt.uc.dei.aor.project.persistence.entity.InterviewEntity;
import pt.uc.dei.aor.project.persistence.entity.UserEntity;
import pt.uc.dei.aor.project.persistence.service.GenericPersistenceService;

public class InterviewProxy implements IInterview, IProxyToEntity<InterviewEntity>, Comparable<IInterview> {
	
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
	public IUser getCandidate() {
		return getApplication().getCandidate();
	}


	@Override
	public Collection<IUser> getInterviewers() {
		List<IUser> proxies = new ArrayList<>();
		Collection<UserEntity> entities = entity.getInterviewers();
		
		for (UserEntity we : entities) 
			proxies.add(new UserProxy(we));
		
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
		for (IUser interviewer : getInterviewers()) {
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
	public int getInterviewPhase() throws AllPhasesCompletedException {
		IApplication application = this.getApplication();
		
		Set<IInterview> interviews = new TreeSet<>(application.getInterviews());
		interviews.add(this);
		
		int counter = 1;
		for (IInterview i : interviews) {
			if (i.equals(this)) return counter;
			counter++;
		}
		logger.error("Wrong phase interview");
		throw new AllPhasesCompletedException();
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


	@Override
	public void addInterviewer(IUser interviewer) {
		entity.addInterviewer(GenericPersistenceService.getEntity(interviewer));
	}


	@Override
	public void removeInterviewer(IUser interviewer) {
		entity.removeInterviewer(GenericPersistenceService.getEntity(interviewer));
	}


	@Override
	public void setDate(Date date) {
		entity.setDate(date);
	}


	@Override
	public void setInterviewers(Collection<IUser> selectedInterviewers) {
		List<UserEntity> interviewers = new ArrayList<>();
		
		for (IUser w : selectedInterviewers) {
			interviewers.add(GenericPersistenceService.getEntity(w));
		}
		
		entity.setInterviewers(interviewers);
	}


	@Override
	public int compareTo(IInterview o) {
		return entity.compareTo(GenericPersistenceService.getEntity(o));
	}



	

}
