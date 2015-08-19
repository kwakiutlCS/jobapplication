package pt.uc.dei.aor.project.persistence.proxy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import pt.uc.dei.aor.project.business.model.IScript;
import pt.uc.dei.aor.project.business.model.IScriptEntry;
import pt.uc.dei.aor.project.business.util.QuestionType;
import pt.uc.dei.aor.project.persistence.entity.ScriptEntity;
import pt.uc.dei.aor.project.persistence.entity.ScriptEntryEntity;


public class ScriptProxy implements IScript, IProxyToEntity<ScriptEntity> {

	private ScriptEntity entity;
	
	public ScriptProxy(ScriptEntity entity) {
		this.entity = entity != null ? entity : new ScriptEntity();
	}


	public ScriptProxy() {
		this(null);
	}


	@Override
	public ScriptEntity getEntity() {
		return entity;
	}


	@Override
	public long getId() {
		return entity.getId();
	}


	@Override
	public List<IScriptEntry> getEntries() {
		List<IScriptEntry> entries = new ArrayList<>();
		if (entity.getEntries() == null) return entries;
		
		for (ScriptEntryEntity see : entity.getEntries()) {
			entries.add(new ScriptEntryProxy(see));
		}
		
		return entries;
	}

	
	@Override
	public void addQuestion(String questionText, String questionType) {
		System.out.println(entity.getEntries());
		entity.getEntries().add(new ScriptEntryEntity(questionText, questionType, entity.getNextPosition()));
	}

	@Override
	public void addQuestion(String questionText, String questionType, int min, int max) {
		entity.getEntries().add(new ScriptEntryEntity(questionText, questionType, entity.getNextPosition(),
				min, max));
	}
	
	@Override
	public void addQuestion(String questionText, String questionType, Collection<String> options) {
		List<String> list = new ArrayList<>();
		for (String s : options) {
			list.add(s);
		}
		
		entity.getEntries().add(new ScriptEntryEntity(questionText, questionType, entity.getNextPosition(),
				list));
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public void deleteQuestion(IScriptEntry entry) {
		ScriptEntryEntity entryEntity;
		if (entry instanceof IProxyToEntity<?>) {
            entryEntity = ((IProxyToEntity<ScriptEntryEntity>) entry).getEntity();
        }
		else throw new IllegalStateException();
        
		entity.getEntries().remove(entryEntity);
	}

}
