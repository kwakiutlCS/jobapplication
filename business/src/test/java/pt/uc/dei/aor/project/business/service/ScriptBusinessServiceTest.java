package pt.uc.dei.aor.project.business.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import pt.uc.dei.aor.project.business.exception.IllegalAnswerOptionsException;
import pt.uc.dei.aor.project.business.exception.IllegalQuestionTypeException;
import pt.uc.dei.aor.project.business.exception.IllegalScaleException;
import pt.uc.dei.aor.project.business.model.IModelFactory;
import pt.uc.dei.aor.project.business.model.IScript;
import pt.uc.dei.aor.project.business.persistence.IScriptPersistenceService;

@RunWith(MockitoJUnitRunner.class)
public class ScriptBusinessServiceTest {

	@Mock
	private IModelFactory factory;
	
	@Mock
	private IScriptPersistenceService scriptEjb;
	
	@Mock
	private IScript iScript;
	
	@InjectMocks
	ScriptBusinessService ejb;
	
	
	@Test
	public void shouldCallMethodWhenAddQuestionToScriptCorrectly() throws IllegalQuestionTypeException {
		String questionText = "a question text";
		String questionType = "Resposta curta";
		ejb.addQuestion(iScript, questionText, questionType);
		verify(iScript).addQuestion(questionText, questionType);
	}
	
	@Test(expected=IllegalQuestionTypeException.class)
	public void shouldThrowExceptionWhenAddQuestionWithMultipleChoice() throws IllegalQuestionTypeException {
		String questionText = "a question text";
		String questionType = "Escolha múltipla";
		ejb.addQuestion(iScript, questionText, questionType);
	}
	
	@Test(expected=IllegalQuestionTypeException.class)
	public void shouldThrowExceptionWhenAddQuestionWithScale() throws IllegalQuestionTypeException {
		String questionText = "a question text";
		String questionType = "Escala";
		ejb.addQuestion(iScript, questionText, questionType);
	}
	
	@Test
	public void shouldCallMethodWhenAddQuestionToScriptWithScale() throws IllegalQuestionTypeException, IllegalScaleException {
		String questionText = "a question text";
		String questionType = "Escala";
		int min = 1;
		int max = 5;
		ejb.addQuestion(iScript, questionText, questionType, min, max);
		verify(iScript).addQuestion(questionText, questionType, min, max);
	}
	
	@Test(expected=IllegalScaleException.class)
	public void shouldThrowExceptionWhenAddQuestionToScriptWithWrongScale() throws IllegalQuestionTypeException, IllegalScaleException {
		String questionText = "a question text";
		String questionType = "Escala";
		int min = 1;
		int max = 1;
		ejb.addQuestion(iScript, questionText, questionType, min, max);
	}
	
	@Test(expected=IllegalQuestionTypeException.class)
	public void shouldThrowExceptionWhenAddQuestionWithoutScale() throws IllegalQuestionTypeException, IllegalScaleException {
		String questionText = "a question text";
		String questionType = "Resposta curta";
		int min = 1;
		int max = 5;
		ejb.addQuestion(iScript, questionText, questionType, min, max);
	}
	
	
	@Test
	public void shouldCallMethodWhenAddQuestionToScriptWithMultipleChoice() throws IllegalQuestionTypeException, IllegalAnswerOptionsException {
		String questionText = "a question text";
		String questionType = "Escolha múltipla";
		List<String> options = Arrays.asList(new String[]{"Manhã", "Tarde", "Noite"});
		ejb.addQuestion(iScript, questionText, questionType, options);
		verify(iScript).addQuestion(questionText, questionType, options);
	}
	
	@Test(expected=IllegalQuestionTypeException.class)
	public void shouldThrowExceptionWhenAddQuestionWithoutMultipleChoice() throws IllegalQuestionTypeException, IllegalAnswerOptionsException {
		String questionText = "a question text";
		String questionType = "Resposta curta";
		List<String> options = Arrays.asList(new String[]{"Manhã", "Tarde", "Noite"});
		ejb.addQuestion(iScript, questionText, questionType, options);
	}
	
	@Test(expected=IllegalAnswerOptionsException.class)
	public void shouldThrowExceptionWhenAddQuestionWithOneChoice() throws IllegalQuestionTypeException, IllegalAnswerOptionsException {
		String questionText = "a question text";
		String questionType = "Escolha múltipla";
		List<String> options = Arrays.asList(new String[]{"Manhã"});
		ejb.addQuestion(iScript, questionText, questionType, options);
	}
	
	@Test
	public void shouldReturnSevenQuestionTypes() {
		int typesCount = 7;
		assertThat(ejb.getQuestionTypeList().size(), is(equalTo(typesCount)));
		assertThat(ejb.getQuestionTypeList().get(0), is(equalTo("Resposta curta")));
	}
	
	@Test
	public void shouldCallCorrectFunctionToGetScripts() {
		ejb.getScripts();
		
		verify(scriptEjb).findAllScripts();
	}
	
	@Test
	public void shouldCallCorrectFunctionToUpdateScripts() {
		ejb.update(iScript);
		
		verify(scriptEjb).save(iScript);
	}
	
	@Test
	public void shouldCallCorrectFunctionToCreateScript() {
		ejb.createNewScript();
		
		verify(factory).script();
	}
	
}