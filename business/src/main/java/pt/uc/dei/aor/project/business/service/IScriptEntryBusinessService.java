package pt.uc.dei.aor.project.business.service;

import pt.uc.dei.aor.project.business.model.IScriptEntry;

public interface IScriptEntryBusinessService {
	
	public IScriptEntry save(IScriptEntry entry);
	
	public void delete(IScriptEntry entry);
}