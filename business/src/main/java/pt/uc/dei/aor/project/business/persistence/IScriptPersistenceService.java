package pt.uc.dei.aor.project.business.persistence;

import java.util.List;

import pt.uc.dei.aor.project.business.model.IScript;
import pt.uc.dei.aor.project.business.model.IScriptEntry;

public interface IScriptPersistenceService {

	List<IScript> findAllScripts();

	List<IScriptEntry> findEntriesByScript(IScript script);

	IScript save(IScript script);

	IScript update(IScript script);

}
