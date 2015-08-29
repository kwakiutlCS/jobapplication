package pt.uc.dei.aor.project.business.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.inject.Inject;

import pt.uc.dei.aor.project.business.exception.IllegalAnswerOptionsException;
import pt.uc.dei.aor.project.business.exception.IllegalQuestionTypeException;
import pt.uc.dei.aor.project.business.exception.IllegalScaleException;
import pt.uc.dei.aor.project.business.model.IAnswerChoice;
import pt.uc.dei.aor.project.business.model.IModelFactory;
import pt.uc.dei.aor.project.business.model.IScript;
import pt.uc.dei.aor.project.business.model.IScriptEntry;
import pt.uc.dei.aor.project.business.persistence.IScriptPersistenceService;
import pt.uc.dei.aor.project.business.util.QuestionType;

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
	public IScript createNewScript(String title) {
		return factory.script(title);
	}

	@Override
	public List<QuestionType> getQuestionTypeList() {
		return new ArrayList<>(EnumSet.allOf(QuestionType.class));
	}

	@Override
	public IScript update(IScript script) {
		return scriptPersistence.save(script);
	}

	@Override
	public IScript addQuestion(IScript script, String questionText, QuestionType questionType) throws IllegalQuestionTypeException {
		if (questionType.equals(QuestionType.SCALE) || questionType.equals(QuestionType.MULTIPLE_CHOICE))
			throw new IllegalQuestionTypeException();
		script.addQuestion(questionText, questionType);
		return update(script);
	}

	@Override
	public IScript addQuestion(IScript script, String questionText, QuestionType questionType, int min, int max) 
			throws IllegalQuestionTypeException, IllegalScaleException { 
		if (!QuestionType.SCALE.equals(questionType)) throw new IllegalQuestionTypeException();
		if (max <= min) throw new IllegalScaleException();
		script.addQuestion(questionText, questionType, min, max);
		return update(script);
	}

	@Override
	public IScript addQuestion(IScript script, String questionText, QuestionType questionType, Collection<String> options) 
		throws IllegalQuestionTypeException, IllegalAnswerOptionsException {
		if (!QuestionType.MULTIPLE_CHOICE.equals(questionType)) throw new IllegalQuestionTypeException();
		if (options == null || options.size() <= 1) throw new IllegalAnswerOptionsException();
		
		Set<IAnswerChoice> answerChoices = new HashSet<>();
		
		for (String s : options) {
			if (s == null || s.equals("")) throw new IllegalAnswerOptionsException();
			answerChoices.add(factory.answerChoice(s.trim()));
		}
		if (answerChoices.size() < options.size()) throw new IllegalAnswerOptionsException();
		
		script.addQuestion(questionText, questionType, answerChoices);
		return update(script);
	}

	@Override
	public void deleteScript(IScript script) {
		scriptPersistence.delete(script);
	}

	@Override
	public IScript moveQuestion(IScript script, int fromIndex, int toIndex) {
		script.moveQuestion(fromIndex, toIndex);
		return update(script);
	}

	@Override
	public IScript delete(IScript script, IScriptEntry entry) {
		script.deleteQuestion(entry);
		return update(script);
	}

	@Override
	public IScript addAnswerToEntry(IScript script, IScriptEntry entry, String option) throws IllegalAnswerOptionsException {
		if (option == null || option.equals("")) throw new IllegalAnswerOptionsException();
		
		IAnswerChoice answer = factory.answerChoice(option.trim());
		for (IAnswerChoice ac : entry.getAnswers()) {
			if (answer.getText().equals(ac.getText())) throw new IllegalAnswerOptionsException();
		}
		
		script.addAnswerToEntry(entry, answer);
		return update(script);
	}

	@Override
	public IScript removeAnswerFromEntry(IScript script, IScriptEntry entry, String answer) throws IllegalAnswerOptionsException {
		if (answer == null || answer.equals("")) throw new IllegalAnswerOptionsException();
		String trimmed = answer.trim();
		
		int numAnswer = entry.getAnswers().size();
		if (numAnswer == 2) throw new IllegalAnswerOptionsException();
		
		script.removeAnswerFromEntry(entry, factory.answerChoice(trimmed));
		return update(script);
	}


}
