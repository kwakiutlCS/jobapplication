package pt.uc.dei.aor.project.persistence.proxy;

import java.util.Collection;

import pt.uc.dei.aor.project.business.model.IScriptEntry;
import pt.uc.dei.aor.project.persistence.entity.ScriptEntryEntity;

public class ScriptEntryProxy implements IScriptEntry, IProxyToEntity<ScriptEntryEntity> {

	private ScriptEntryEntity entity;
	
	public ScriptEntryProxy(ScriptEntryEntity entity) {
		this.entity = entity != null ? entity : new ScriptEntryEntity();
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
	public String getQuestionType() {
		return entity.getQuestion().getType();
	}


	@Override
	public String getMin() {
		return entity.getQuestion().getMin();
	}


	@Override
	public String getMax() {
		return entity.getQuestion().getMax();
	}


	@Override
	public Collection<String> getAnswers() {
		return entity.getQuestion().getAnswers();
	}


	@Override
	public void setText(String text) {
		entity.getQuestion().setText(text);
		
	}

}
