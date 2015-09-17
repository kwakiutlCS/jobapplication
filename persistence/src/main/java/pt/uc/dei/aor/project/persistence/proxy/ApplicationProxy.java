package pt.uc.dei.aor.project.persistence.proxy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import pt.uc.dei.aor.project.business.model.IApplication;
import pt.uc.dei.aor.project.business.model.ICandidate;
import pt.uc.dei.aor.project.business.model.IInterview;
import pt.uc.dei.aor.project.business.model.IPosition;
import pt.uc.dei.aor.project.persistence.entity.ApplicationEntity;
import pt.uc.dei.aor.project.persistence.entity.InterviewEntity;
import pt.uc.dei.aor.project.persistence.entity.PositionEntity;
import pt.uc.dei.aor.project.persistence.service.GenericPersistenceService;

public class ApplicationProxy implements IApplication, IProxyToEntity<ApplicationEntity> {

	private ApplicationEntity entity;
	
	public ApplicationProxy(ApplicationEntity entity) {
		this.entity = entity != null ? entity : new ApplicationEntity();
	}


	public ApplicationProxy() {
		this(null);
	}

		
	@Override
	public ApplicationEntity getEntity() {
		return entity;
	}


	@Override
	public ICandidate getCandidate() {
		return new CandidateProxy(entity.getCandidate());
	}


	@Override
	public IPosition getPosition() {
		PositionEntity position = entity.getPosition();
		
		return new PositionProxy(position);
	}


	@Override
	public List<IInterview> getInterviews() {
		List<IInterview> proxies = new ArrayList<>();
		SortedSet<InterviewEntity> entities = entity.getInterviews();
		
		for (InterviewEntity ie : entities) {
			proxies.add(new InterviewProxy(ie));
		}
		
		return proxies;
	}


	@Override
	public long getId() {
		return entity.getId();
	}


	@Override
	public void addInterview(IInterview interview) {
		entity.addInterview(GenericPersistenceService.getEntity(interview));
	}


	@Override
	public IInterview getInterviewByDate(Date date) {
		for (IInterview i : getInterviews()) {
			if (i.getDateObject().equals(date)) return i;
		}
		return null;
	}


	@Override
	public void remove(IInterview interview) {
		entity.remove(GenericPersistenceService.getEntity(interview));
	}

	
}
