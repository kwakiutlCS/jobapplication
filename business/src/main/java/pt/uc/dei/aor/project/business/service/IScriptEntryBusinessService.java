package pt.uc.dei.aor.project.business.service;

import pt.uc.dei.aor.project.business.exception.IllegalAnswerOptionsException;
import pt.uc.dei.aor.project.business.model.IScriptEntry;

public interface IScriptEntryBusinessService {
	
	public IScriptEntry save(IScriptEntry entry);
	
	public void delete(IScriptEntry entry);

	public void addAnswer(IScriptEntry entry, String option) throws IllegalAnswerOptionsException;

	public void removeAnswer(IScriptEntry entry, String answer);
}