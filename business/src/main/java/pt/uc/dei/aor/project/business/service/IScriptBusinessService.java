package pt.uc.dei.aor.project.business.service;

import java.util.List;

import pt.uc.dei.aor.project.business.model.IScript;
import pt.uc.dei.aor.project.business.model.IScriptEntry;

public interface IScriptBusinessService {
	
	public List<IScript> getScripts();

	public IScript createNewScript();

	public List<String> getQuestionTypeList();

	public IScript update(IScript editableScript);
}
