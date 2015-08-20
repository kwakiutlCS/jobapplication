package pt.uc.dei.aor.project.business.service;

import java.util.Collection;
import java.util.List;

import pt.uc.dei.aor.project.business.exception.IllegalAnswerOptionsException;
import pt.uc.dei.aor.project.business.exception.IllegalQuestionTypeException;
import pt.uc.dei.aor.project.business.exception.IllegalScaleException;
import pt.uc.dei.aor.project.business.model.IScript;
import pt.uc.dei.aor.project.business.model.IScriptEntry;

public interface IScriptBusinessService {
	
	public List<IScript> getScripts();

	public IScript createNewScript();

	public List<String> getQuestionTypeList();

	public IScript update(IScript editableScript);

	void addQuestion(IScript iScript, String questionText, String questionType) throws IllegalQuestionTypeException;

	void addQuestion(IScript sript, String questionText, String questionType, int min, int max)
			throws IllegalQuestionTypeException, IllegalScaleException;

	void addQuestion(IScript script, String questionText, String questionType, Collection<String> options)
			throws IllegalQuestionTypeException, IllegalAnswerOptionsException;

	public void deleteScript(IScript script);

	public void moveQuestion(IScript script, int fromIndex, int toIndex);
}
