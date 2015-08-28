package pt.uc.dei.aor.project.persistence.proxy;

import pt.uc.dei.aor.project.business.model.IAnswerChoice;
import pt.uc.dei.aor.project.persistence.entity.AnswerChoiceEntity;


public class AnswerChoiceProxy implements IAnswerChoice, IProxyToEntity<AnswerChoiceEntity> {

	private AnswerChoiceEntity entity;
	
	public AnswerChoiceProxy(AnswerChoiceEntity entity) {
		this.entity = entity == null ? new AnswerChoiceEntity() : entity;
	}
	
	
	public AnswerChoiceProxy(String answer) {
		this(new AnswerChoiceEntity(answer));
	}


	@Override
	public AnswerChoiceEntity getEntity() {
		return entity;
	}

	@Override
	public String getText() {
		return entity.getAnswer();
	}
	
	@Override
	public String toString() {
		return entity.toString();
	}

}
