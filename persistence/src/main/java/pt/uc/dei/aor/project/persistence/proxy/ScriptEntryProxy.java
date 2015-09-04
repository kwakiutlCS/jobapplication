package pt.uc.dei.aor.project.persistence.proxy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import pt.uc.dei.aor.project.business.exception.IllegalScaleException;
import pt.uc.dei.aor.project.business.model.IAnswerChoice;
import pt.uc.dei.aor.project.business.model.IScriptEntry;
import pt.uc.dei.aor.project.business.util.QuestionType;
import pt.uc.dei.aor.project.persistence.entity.AnswerChoiceEntity;
import pt.uc.dei.aor.project.persistence.entity.ScriptEntryEntity;
import pt.uc.dei.aor.project.persistence.service.GenericPersistenceService;

public class ScriptEntryProxy implements IScriptEntry, IProxyToEntity<ScriptEntryEntity> {

	private ScriptEntryEntity entity;
	
	public ScriptEntryProxy(ScriptEntryEntity entity) {
		this.entity = entity != null ? entity : new ScriptEntryEntity();
	}

	public ScriptEntryProxy() {
		this(null);
	}

	@Override
	public ScriptEntryEntity getEntity() {
		return entity;
	}


	@Override
	public String getText() {
		return entity.getQuestion().getText();
	}


	@Override
	public QuestionType getQuestionType() {
		return entity.getQuestion().getType();
	}


	@Override
	public String getMin() {
		return String.valueOf(entity.getQuestion().getMin());
	}


	@Override
	public String getMax() {
		return String.valueOf(entity.getQuestion().getMax());
	}


	@Override
	public Collection<IAnswerChoice> getAnswers() {
		List<IAnswerChoice> list = new ArrayList<>();
		for (AnswerChoiceEntity ace : entity.getQuestion().getAnswers())
			list.add(new AnswerChoiceProxy(ace));
		
		return list;
	}


	@Override
	public void setText(String text) {
		entity.getQuestion().setText(text);
		
	}


	@Override
	public void setMin(String min) throws IllegalScaleException {
		int minInt;
		
		try {
			minInt = Integer.parseInt(min);
		}
		catch(Exception e) { return; }
		
		if (minInt < Integer.parseInt(getMax()))
			entity.getQuestion().setMin(minInt);	
	}


	@Override
	public void setMax(String max) throws IllegalScaleException {
		int maxInt;
		
		try {
			maxInt = Integer.parseInt(max);
		}
		catch(Exception e) { return; }
		
		if (maxInt > Integer.parseInt(getMin()))
			entity.getQuestion().setMax(maxInt);				
	
	}
	
	public String toString() {
		return "proxy: "+entity;
	}

	@Override
	public long getId() {
		return entity.getId();
	}

	@Override
	public void addAnswer(IAnswerChoice option) {
		entity.addAnswer(GenericPersistenceService.getEntity(option));
	}

	@Override
	public void removeAnswer(IAnswerChoice answer) {
		entity.removeAnswer(GenericPersistenceService.getEntity(answer));
	}

}
