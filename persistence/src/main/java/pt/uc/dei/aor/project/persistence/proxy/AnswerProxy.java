package pt.uc.dei.aor.project.persistence.proxy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import pt.uc.dei.aor.project.business.model.IAnswer;
import pt.uc.dei.aor.project.business.model.IApplication;
import pt.uc.dei.aor.project.business.model.ICandidate;
import pt.uc.dei.aor.project.business.model.IInterview;
import pt.uc.dei.aor.project.business.model.IPosition;
import pt.uc.dei.aor.project.business.model.IScriptEntry;
import pt.uc.dei.aor.project.persistence.entity.AnswerEntity;
import pt.uc.dei.aor.project.persistence.entity.ApplicationEntity;
import pt.uc.dei.aor.project.persistence.entity.InterviewEntity;
import pt.uc.dei.aor.project.persistence.entity.PositionEntity;
import pt.uc.dei.aor.project.persistence.service.GenericPersistenceService;

public class AnswerProxy implements IAnswer, IProxyToEntity<AnswerEntity> {

	private AnswerEntity entity;
	
	public AnswerProxy(AnswerEntity entity) {
		this.entity = entity != null ? entity : new AnswerEntity();
	}


	public AnswerProxy() {
		this(null);
	}

	public AnswerProxy(IInterview interview, String answer, IScriptEntry entry) {
		InterviewEntity interviewEntity = GenericPersistenceService.getEntity(interview);
		
		entity = new AnswerEntity(interviewEntity, 
				entry.getText(), answer, entry.getPosition());
	}
		
	
	@Override
	public AnswerEntity getEntity() {
		return entity;
	}
	
}
