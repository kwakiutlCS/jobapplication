package pt.uc.dei.aor.project.business.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import pt.uc.dei.aor.project.business.model.IModelFactory;
import pt.uc.dei.aor.project.business.model.IScript;
import pt.uc.dei.aor.project.business.model.IScriptEntry;
import pt.uc.dei.aor.project.business.persistence.IScriptPersistenceService;

@Stateless
public class ScriptBusinessService implements IScriptBusinessService {

	@Inject
	private IScriptPersistenceService scriptPersistence;
	
	@Inject IModelFactory factory;
	
	@Override
	public List<IScript> getScripts() {
		return scriptPersistence.findAllScripts();
	}

	@Override
	public IScript createNewScript() {
		return factory.script();
	}

	@Override
	public List<String> getQuestionTypeList() {
		return factory.script().getQuestionTypeList();
	}

	@Override
	public IScript update(IScript script) {
		return scriptPersistence.save(script);
	}


}
