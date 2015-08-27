package pt.uc.dei.aor.project.persistence.proxy;

import pt.uc.dei.aor.project.business.model.IInterview;
import pt.uc.dei.aor.project.persistence.entity.InterviewEntity;

public class InterviewProxy implements IInterview, IProxyToEntity<InterviewEntity> {

	private InterviewEntity entity;
	
	public InterviewProxy(InterviewEntity entity) {
		this.entity = entity != null ? entity : new InterviewEntity();
	}

	
	@Override
	public InterviewEntity getEntity() {
		return entity;
	}

	
}
