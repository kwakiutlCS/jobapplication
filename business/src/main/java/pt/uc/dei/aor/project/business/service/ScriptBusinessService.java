package pt.uc.dei.aor.project.business.service;

import java.util.LinkedList;
import java.util.List;

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
		List<String> list = new LinkedList<>();
		
		for (QuestionType qt : QuestionType.values()) {
			list.add(qt.toString());
		}
		
		return list;
	}

	@Override
	public IScript update(IScript script) {
		return scriptPersistence.save(script);
	}

	@Override
	public void addQuestion(IScript script, String questionText, String questionType) throws IllegalQuestionTypeException {
		if (questionType.equals("Escala") || questionType.equals("Escolha múltipla"))
			throw new IllegalQuestionTypeException();
		script.addQuestion(questionText, questionType);
	}

	@Override
	public void addQuestion(IScript script, String questionText, String questionType, int min, int max) 
			throws IllegalQuestionTypeException, IllegalScaleException { 
		if (!"Escala".equals(questionType)) throw new IllegalQuestionTypeException();
		if (max <= min) throw new IllegalScaleException();
		script.addQuestion(questionText, questionType, min, max);
	}

	@Override
	public void addQuestion(IScript script, String questionText, String questionType, List<String> options) 
		throws IllegalQuestionTypeException, IllegalAnswerOptionsException {
		if (!"Escolha múltipla".equals(questionType)) throw new IllegalQuestionTypeException();
		if (options == null || options.size() <= 1) throw new IllegalAnswerOptionsException();
		script.addQuestion(questionText, questionType, options);
	}


}
