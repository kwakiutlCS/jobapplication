package pt.uc.dei.aor.project.business.persistence;

import pt.uc.dei.aor.project.business.model.IScriptEntry;

public interface IScriptEntryPersistenceService {

	IScriptEntry save(IScriptEntry script);

	void delete(IScriptEntry script);
	
}