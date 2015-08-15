package pt.uc.dei.aor.project.business.service;

import java.util.List;

import pt.uc.dei.aor.project.business.model.IScript;
import pt.uc.dei.aor.project.business.model.IScriptEntry;

public interface IScriptBusinessService {
	
	public List<IScript> getScripts();

	public IScript createNewScript();

	public IScript addQuestion(IScript script, String questionText, String questionType);

	public List<String> getQuestionTypeList();
}
