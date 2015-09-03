package pt.uc.dei.aor.project.business.service;

import java.util.Collection;
import java.util.List;

import pt.uc.dei.aor.project.business.exception.IllegalAnswerOptionsException;
import pt.uc.dei.aor.project.business.exception.IllegalQuestionTypeException;
import pt.uc.dei.aor.project.business.exception.IllegalScaleException;
import pt.uc.dei.aor.project.business.model.IScript;
import pt.uc.dei.aor.project.business.model.IScriptEntry;
import pt.uc.dei.aor.project.business.util.QuestionType;

public interface IScriptBusinessService {
	
	public List<IScript> getScripts();

	public IScript createNewScript(String scriptTitle);

	public List<QuestionType> getQuestionTypeList();

	public IScript update(IScript editableScript);

	public IScript addQuestion(IScript iScript, String questionText, QuestionType questionType) throws IllegalQuestionTypeException;

	public IScript addQuestion(IScript sript, String questionText, QuestionType questionType, int min, int max)
			throws IllegalQuestionTypeException, IllegalScaleException;

	public IScript addQuestion(IScript script, String questionText, QuestionType questionType, Collection<String> options)
			throws IllegalQuestionTypeException, IllegalAnswerOptionsException;

	public void deleteScript(IScript script);

	public IScript moveQuestion(IScript script, int fromIndex, int toIndex);

	public IScript delete(IScript script, IScriptEntry entry);

	public IScript addAnswerToEntry(IScript script, IScriptEntry entry, String option) throws IllegalAnswerOptionsException;

	public IScript removeAnswerFromEntry(IScript script, IScriptEntry entry, String answer) throws IllegalAnswerOptionsException;

	public void updateEntry(IScriptEntry entry);

	public IScript findScriptById(long id);

	
}
