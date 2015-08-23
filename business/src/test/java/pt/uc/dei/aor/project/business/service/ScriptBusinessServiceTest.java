package pt.uc.dei.aor.project.business.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Ignore;
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
import pt.uc.dei.aor.project.business.util.QuestionType;

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
		QuestionType questionType = QuestionType.SHORT_ANSWER;
		ejb.addQuestion(iScript, questionText, questionType);
		verify(iScript).addQuestion(questionText, questionType);
	}
	
	@Test(expected=IllegalQuestionTypeException.class)
	public void shouldThrowExceptionWhenAddQuestionWithMultipleChoice() throws IllegalQuestionTypeException {
		String questionText = "a question text";
		QuestionType questionType = QuestionType.MULTIPLE_CHOICE;
		ejb.addQuestion(iScript, questionText, questionType);
	}
	
	@Test(expected=IllegalQuestionTypeException.class)
	public void shouldThrowExceptionWhenAddQuestionWithScale() throws IllegalQuestionTypeException {
		String questionText = "a question text";
		QuestionType questionType = QuestionType.SCALE;
		ejb.addQuestion(iScript, questionText, questionType);
	}
	
	@Test
	public void shouldCallMethodWhenAddQuestionToScriptWithScale() throws IllegalQuestionTypeException, IllegalScaleException {
		String questionText = "a question text";
		QuestionType questionType = QuestionType.SCALE;
		int min = 1;
		int max = 5;
		ejb.addQuestion(iScript, questionText, questionType, min, max);
		verify(iScript).addQuestion(questionText, questionType, min, max);
	}
	
	@Test(expected=IllegalScaleException.class)
	public void shouldThrowExceptionWhenAddQuestionToScriptWithWrongScale() throws IllegalQuestionTypeException, IllegalScaleException {
		String questionText = "a question text";
		QuestionType questionType = QuestionType.SCALE;
		int min = 1;
		int max = 1;
		ejb.addQuestion(iScript, questionText, questionType, min, max);
	}
	
	@Test(expected=IllegalQuestionTypeException.class)
	public void shouldThrowExceptionWhenAddQuestionWithoutScale() throws IllegalQuestionTypeException, IllegalScaleException {
		String questionText = "a question text";
		QuestionType questionType = QuestionType.SHORT_ANSWER;
		int min = 1;
		int max = 5;
		ejb.addQuestion(iScript, questionText, questionType, min, max);
	}
	
	
	@Test
	public void shouldCallMethodWhenAddQuestionToScriptWithMultipleChoice() throws IllegalQuestionTypeException, IllegalAnswerOptionsException {
		String questionText = "a question text";
		QuestionType questionType = QuestionType.MULTIPLE_CHOICE;
		List<String> options = Arrays.asList(new String[]{"Manhã", "Tarde", "Noite"});
		ejb.addQuestion(iScript, questionText, questionType, options);
		verify(iScript).addQuestion(questionText, questionType, options);
	}
	
	@Test(expected=IllegalQuestionTypeException.class)
	public void shouldThrowExceptionWhenAddQuestionWithoutMultipleChoice() throws IllegalQuestionTypeException, IllegalAnswerOptionsException {
		String questionText = "a question text";
		QuestionType questionType = QuestionType.SHORT_ANSWER;
		List<String> options = Arrays.asList(new String[]{"Manhã", "Tarde", "Noite"});
		ejb.addQuestion(iScript, questionText, questionType, options);
	}
	
	@Test(expected=IllegalAnswerOptionsException.class)
	public void shouldThrowExceptionWhenAddQuestionWithOneChoice() throws IllegalQuestionTypeException, IllegalAnswerOptionsException {
		String questionText = "a question text";
		QuestionType questionType = QuestionType.MULTIPLE_CHOICE;
		List<String> options = Arrays.asList(new String[]{"Manhã"});
		ejb.addQuestion(iScript, questionText, questionType, options);
	}
	
	@Test(expected=IllegalAnswerOptionsException.class)
	public void shouldThrowExceptionWhenAddQuestionWithNullChoice() throws IllegalQuestionTypeException, IllegalAnswerOptionsException {
		String questionText = "a question text";
		QuestionType questionType = QuestionType.MULTIPLE_CHOICE;
		List<String> options = null;
		ejb.addQuestion(iScript, questionText, questionType, options);
	}
	
	@Test(expected=IllegalAnswerOptionsException.class)
	public void shouldThrowExceptionWhenAddQuestionWithRepeatedChoice() throws IllegalQuestionTypeException, IllegalAnswerOptionsException {
		String questionText = "a question text";
		QuestionType questionType = QuestionType.MULTIPLE_CHOICE;
		List<String> options = Arrays.asList(new String[]{"Manhã", "Tarde", "Manhã"});
		ejb.addQuestion(iScript, questionText, questionType, options);
	}
	
	@Test
	public void shouldNotThrowExceptionWhenAddQuestionWithRepeatedChoice2() throws IllegalQuestionTypeException, IllegalAnswerOptionsException {
		String questionText = "a question text";
		QuestionType questionType = QuestionType.MULTIPLE_CHOICE;
		List<String> options = Arrays.asList(new String[]{"Manhã", "Tarde", "manhã"});
		ejb.addQuestion(iScript, questionText, questionType, options);
	}
	
	
	@Test
	public void shouldTrimAnswersWhenAddQuestion() throws IllegalQuestionTypeException, IllegalAnswerOptionsException {
		String questionText = "a question text";
		QuestionType questionType = QuestionType.MULTIPLE_CHOICE;
		List<String> options = Arrays.asList(new String[]{"  Manhã", "Tarde  ", " Noite  "});
		ejb.addQuestion(iScript, questionText, questionType, options);
		List<String> trimmed = new ArrayList<>();
		
		for (String o : options) trimmed.add(o.trim());
		verify(iScript).addQuestion(questionText, questionType, trimmed);
	}
	
	
	@Test
	public void shouldReturnSevenQuestionTypes() {
		int typesCount = 7;
		assertThat(ejb.getQuestionTypeList().size(), is(equalTo(typesCount)));
		assertThat(ejb.getQuestionTypeList().get(0), is(equalTo(QuestionType.SHORT_ANSWER)));
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
		String title = "title";
		ejb.createNewScript(title);
		
		verify(factory).script(title);
	}
	
	@Test 
	public void shouldReorderQuestionsCorrectly() throws IllegalQuestionTypeException {
		ejb.addQuestion(iScript, "question1", QuestionType.SHORT_ANSWER);
		ejb.addQuestion(iScript, "question2", QuestionType.SHORT_ANSWER);
		ejb.addQuestion(iScript, "question3", QuestionType.SHORT_ANSWER);
		ejb.addQuestion(iScript, "question4", QuestionType.SHORT_ANSWER);
		ejb.moveQuestion(iScript, 2, 0);
		
		verify(iScript).moveQuestion(2, 0);
	}
}
