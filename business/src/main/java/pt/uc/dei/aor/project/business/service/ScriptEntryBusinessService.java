package pt.uc.dei.aor.project.business.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import pt.uc.dei.aor.project.business.exception.IllegalAnswerOptionsException;
import pt.uc.dei.aor.project.business.model.IScriptEntry;
import pt.uc.dei.aor.project.business.persistence.IScriptEntryPersistenceService;

@Stateless
public class ScriptEntryBusinessService implements IScriptEntryBusinessService {

	@Inject 
	private IScriptEntryPersistenceService persistence;
	
	@Override
	public IScriptEntry save(IScriptEntry entry) {
		return persistence.save(entry);
	}

	@Override
	public void delete(IScriptEntry entry) {
		persistence.delete(entry);
	}

	@Override
	public void addAnswer(IScriptEntry entry, String option) throws IllegalAnswerOptionsException {
		if (option == null || option.equals("")) throw new IllegalAnswerOptionsException();
		if (entry.getAnswers().contains(option)) throw new IllegalAnswerOptionsException();
		entry.addAnswer(option.trim());
		save(entry);
	}

	@Override
	public void removeAnswer(IScriptEntry entry, String answer) throws IllegalAnswerOptionsException {
		if (entry.getAnswers().size() == 2) throw new IllegalAnswerOptionsException();
		entry.removeAnswer(answer);
		save(entry);
	}

}
