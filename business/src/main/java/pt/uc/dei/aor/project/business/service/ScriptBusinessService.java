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
		IScript script = factory.script();
		return scriptPersistence.save(script);
	}

	@Override
	public IScript addQuestion(IScript script, String questionText, String questionType) {
		script.addQuestion(questionText, questionType);
		return scriptPersistence.update(script);
	}

	@Override
	public List<String> getQuestionTypeList() {
		return factory.script().getQuestionTypeList();
	}


}
