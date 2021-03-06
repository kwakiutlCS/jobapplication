package pt.uc.dei.aor.project.persistence.proxy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import pt.uc.dei.aor.project.business.model.IAnswerChoice;
import pt.uc.dei.aor.project.business.model.IScript;
import pt.uc.dei.aor.project.business.model.IScriptEntry;
import pt.uc.dei.aor.project.business.util.QuestionType;
import pt.uc.dei.aor.project.persistence.entity.AnswerChoiceEntity;
import pt.uc.dei.aor.project.persistence.entity.ScriptEntity;
import pt.uc.dei.aor.project.persistence.entity.ScriptEntryEntity;
import pt.uc.dei.aor.project.persistence.service.GenericPersistenceService;


public class ScriptProxy implements IScript, IProxyToEntity<ScriptEntity> {

	private ScriptEntity entity;
	
	public ScriptProxy(ScriptEntity entity) {
		this.entity = entity != null ? entity : new ScriptEntity();
	}

	public ScriptProxy(String title) {
		this.entity = new ScriptEntity(title);
	}

	public ScriptProxy() {
		entity = new ScriptEntity();
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
	public void addQuestion(String questionText, QuestionType questionType) {
		entity.getEntries().add(new ScriptEntryEntity(questionText, questionType, entity.getNextPosition()));
	}

	@Override
	public void addQuestion(String questionText, QuestionType questionType, int min, int max) {
		entity.getEntries().add(new ScriptEntryEntity(questionText, questionType, entity.getNextPosition(),
				min, max));
	}
	
	@Override
	public void addQuestion(String questionText, QuestionType questionType, Collection<IAnswerChoice> options) {
		List<AnswerChoiceEntity> list = new ArrayList<>();
		for (IAnswerChoice ac : options) {
			list.add(GenericPersistenceService.getEntity(ac));
		}
		
		entity.getEntries().add(new ScriptEntryEntity(questionText, questionType, entity.getNextPosition(),
				list));
	}
	
	
	@Override
	public void deleteQuestion(IScriptEntry entry) {
		ScriptEntryEntity entryEntity = GenericPersistenceService.getEntity(entry);
         
		entity.getEntries().remove(entryEntity);
	}


	@Override
	public void moveQuestion(int fromIndex, int toIndex) {
		SortedSet<ScriptEntryEntity> reordered = new TreeSet<>();
		List<ScriptEntryEntity> list = new ArrayList<>();
		SortedSet<ScriptEntryEntity> set = entity.getEntries();
		
		int counter = 0;
		int basePosition = -1;
		
		if (fromIndex < toIndex) {
			while(!set.isEmpty()) {
				ScriptEntryEntity entry = set.first();
				set.remove(entry);
				
				if (counter == fromIndex) {
					basePosition = entry.getPosition();
					entry.setPosition(basePosition+(toIndex-fromIndex));
				}
				else if (counter > fromIndex && counter <= toIndex){
					entry.setPosition(basePosition+counter-fromIndex-1);
				}
				
				list.add(entry);
				counter++;
			}
		}
		else {
			while(!set.isEmpty()) {
				ScriptEntryEntity entry = set.first();
				set.remove(entry);
				
				if (counter >= toIndex && counter < fromIndex) {
					if (basePosition == -1)
						basePosition = entry.getPosition();
					entry.setPosition(basePosition+counter-toIndex+1);
				}
				else if (counter == fromIndex){
					entry.setPosition(basePosition);
				}
				
				list.add(entry);
				counter++;
			}
		}
		
		for (ScriptEntryEntity se : list) {
			reordered.add(se);
		}
		entity.setEntries(reordered);
	}

	@Override
	public String getTitle() {
		return entity.getTitle();
	}
	
	@Override
	public void setTitle(String title) {
		entity.setTitle(title);
	}

	@Override
	public void addAnswerToEntry(IScriptEntry entry, IAnswerChoice answerChoice) {
		entry.addAnswer(answerChoice);
	}

	@Override
	public void removeAnswerFromEntry(IScriptEntry entry, IAnswerChoice answerChoice) {
		entry.removeAnswer(answerChoice);
	}
	
	@Override
	public String toString() {
		return entity.toString();
	}
	
	@Override
	public int hashCode() {
		return entity.hashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		ScriptEntity oe = null;
		
		if (o instanceof IProxyToEntity<?>)
			oe = GenericPersistenceService.getEntity(o);
		else return false;
		
		return entity.equals(oe);
	}

	@Override
	public void delete() {
		entity.setDeleted(true);
	}


}
