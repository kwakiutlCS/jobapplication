package pt.uc.dei.aor.project.business.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

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

}
