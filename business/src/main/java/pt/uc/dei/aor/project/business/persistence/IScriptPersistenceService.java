package pt.uc.dei.aor.project.business.persistence;

import java.util.List;

import pt.uc.dei.aor.project.business.model.IScript;
import pt.uc.dei.aor.project.business.model.IScriptEntry;

public interface IScriptPersistenceService {

	List<IScript> findAllScripts();

	IScript save(IScript script);

	void delete(IScript script);
	
}
