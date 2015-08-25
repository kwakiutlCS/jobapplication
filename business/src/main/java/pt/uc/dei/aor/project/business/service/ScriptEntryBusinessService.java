package pt.uc.dei.aor.project.business.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import pt.uc.dei.aor.project.business.exception.IllegalAnswerOptionsException;
import pt.uc.dei.aor.project.business.model.IAnswerChoice;
import pt.uc.dei.aor.project.business.model.IModelFactory;
import pt.uc.dei.aor.project.business.model.IScriptEntry;
import pt.uc.dei.aor.project.business.persistence.IScriptEntryPersistenceService;

@Stateless
public class ScriptEntryBusinessService implements IScriptEntryBusinessService {

	@Inject 
	private IScriptEntryPersistenceService persistence;
	
	@Inject
	private IModelFactory factory;
	
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
		
		IAnswerChoice answerChoice = factory.answerChoice(option.trim());
		if (entry.getAnswers().contains(answerChoice)) throw new IllegalAnswerOptionsException();
		
		entry.addAnswer(answerChoice);
		save(entry);
	}

	@Override
	public void removeAnswer(IScriptEntry entry, String answer) throws IllegalAnswerOptionsException {
		if (entry.getAnswers().size() == 2) throw new IllegalAnswerOptionsException();
		
		IAnswerChoice answerChoice = factory.answerChoice(answer.trim());
		entry.removeAnswer(answerChoice);
		
		save(entry);
	}

}
