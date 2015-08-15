package pt.uc.dei.aor.project.persistence.proxy;

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


	

}
