package pt.uc.dei.aor.project.business.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.inject.Inject;

import pt.uc.dei.aor.project.business.exception.IllegalAnswerOptionsException;
import pt.uc.dei.aor.project.business.exception.IllegalQuestionTypeException;
import pt.uc.dei.aor.project.business.exception.IllegalScaleException;
import pt.uc.dei.aor.project.business.model.IModelFactory;
import pt.uc.dei.aor.project.business.model.IScript;
import pt.uc.dei.aor.project.business.model.IScriptEntry;
import pt.uc.dei.aor.project.business.persistence.IScriptPersistenceService;
import pt.uc.dei.aor.project.business.util.QuestionType;
import pt.uc.dei.aor.project.business.util.Role;

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
		
		Set<String> set = new HashSet<>();
		for (String s : options)
			set.add(s.toLowerCase());
		
		if (set.size() < options.size()) throw new IllegalAnswerOptionsException();
		
		script.addQuestion(questionText, questionType, options);
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


}
