package pt.uc.dei.aor.project.business.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import pt.uc.dei.aor.project.business.exception.IllegalAnswerOptionsException;
import pt.uc.dei.aor.project.business.exception.IllegalQuestionTypeException;
import pt.uc.dei.aor.project.business.exception.IllegalScaleException;
import pt.uc.dei.aor.project.business.exception.ReservedQuestionException;
import pt.uc.dei.aor.project.business.model.IAnswerChoice;
import pt.uc.dei.aor.project.business.model.IModelFactory;
import pt.uc.dei.aor.project.business.model.IScript;
import pt.uc.dei.aor.project.business.model.IScriptEntry;
import pt.uc.dei.aor.project.business.persistence.IScriptEntryPersistenceService;
import pt.uc.dei.aor.project.business.persistence.IScriptPersistenceService;
import pt.uc.dei.aor.project.business.util.QuestionType;

@RunWith(MockitoJUnitRunner.class)
public class ScriptBusinessServiceTest {

	@Mock
	private IModelFactory factory;
	
	@Mock
	private IScriptPersistenceService scriptEjb;
	
	@Mock
	private IScriptEntryPersistenceService entryEjb;
	
	@Mock
	private IScript iScript;
	
	@InjectMocks
	ScriptBusinessService ejb;
	
	
	@Test
	public void shouldCallMethodWhenAddQuestionToScriptCorrectly() throws IllegalQuestionTypeException, ReservedQuestionException {
		String questionText = "a question text";
		QuestionType questionType = QuestionType.SHORT_ANSWER;
		ejb.addQuestion(iScript, questionText, questionType);
		verify(iScript).addQuestion(questionText, questionType);
	}
	
	@Test(expected=IllegalQuestionTypeException.class)
	public void shouldThrowExceptionWhenAddQuestionWithMultipleChoice() throws IllegalQuestionTypeException, ReservedQuestionException {
		String questionText = "a question text";
		QuestionType questionType = QuestionType.MULTIPLE_CHOICE;
		ejb.addQuestion(iScript, questionText, questionType);
	}
	
	@Test(expected=ReservedQuestionException.class)
	public void shouldThrowExceptionWhenAddReservedQuestion() throws IllegalQuestionTypeException, ReservedQuestionException {
		String questionText = "Interview's Global Appreciation ";
		QuestionType questionType = QuestionType.LONG_ANSWER;
		ejb.addQuestion(iScript, questionText, questionType);
	}
	
	@Test(expected=ReservedQuestionException.class)
	public void shouldThrowExceptionWhenAddReservedQuestion2() throws IllegalQuestionTypeException, ReservedQuestionException, IllegalScaleException {
		String questionText = "Interview's Global Appreciation ";
			QuestionType questionType = QuestionType.SCALE;
		int min = 1;
		int max = 5;
		ejb.addQuestion(iScript, questionText, questionType, min, max);
	}
	
	@Test(expected=IllegalQuestionTypeException.class)
	public void shouldThrowExceptionWhenAddQuestionWithScale() throws IllegalQuestionTypeException, ReservedQuestionException {
		String questionText = "a question text";
		QuestionType questionType = QuestionType.SCALE;
		ejb.addQuestion(iScript, questionText, questionType);
	}
	
	@Test
	public void shouldCallMethodWhenAddQuestionToScriptWithScale() throws IllegalQuestionTypeException, IllegalScaleException, ReservedQuestionException {
		String questionText = "a question text";
		QuestionType questionType = QuestionType.SCALE;
		int min = 1;
		int max = 5;
		ejb.addQuestion(iScript, questionText, questionType, min, max);
		verify(iScript).addQuestion(questionText, questionType, min, max);
	}
	
	@Test(expected=IllegalScaleException.class)
	public void shouldThrowExceptionWhenAddQuestionToScriptWithWrongScale() throws IllegalQuestionTypeException, IllegalScaleException, ReservedQuestionException {
		String questionText = "a question text";
		QuestionType questionType = QuestionType.SCALE;
		int min = 1;
		int max = 1;
		ejb.addQuestion(iScript, questionText, questionType, min, max);
	}
	
	@Test(expected=IllegalQuestionTypeException.class)
	public void shouldThrowExceptionWhenAddQuestionWithoutScale() throws IllegalQuestionTypeException, IllegalScaleException, ReservedQuestionException {
		String questionText = "a question text";
		QuestionType questionType = QuestionType.SHORT_ANSWER;
		int min = 1;
		int max = 5;
		ejb.addQuestion(iScript, questionText, questionType, min, max);
	}
	
	@Test
	public void shouldCallMethodWhenAddQuestionToScriptWithMultipleChoice() throws IllegalQuestionTypeException, IllegalAnswerOptionsException, ReservedQuestionException {
		String questionText = "a question text";
		QuestionType questionType = QuestionType.MULTIPLE_CHOICE;
		List<String> options = Arrays.asList(new String[]{"Manhã", "Tarde", "Noite"});
		
		IAnswerChoice ac1 = Mockito.mock(IAnswerChoice.class);
		IAnswerChoice ac2 = Mockito.mock(IAnswerChoice.class);
		IAnswerChoice ac3 = Mockito.mock(IAnswerChoice.class);
		
		Set<IAnswerChoice> set = new HashSet<>();
		set.add(ac1);
		set.add(ac2);
		set.add(ac3);
		
		Mockito.when(factory.answerChoice("Manhã")).thenReturn(ac1);
		Mockito.when(factory.answerChoice("Tarde")).thenReturn(ac2);
		Mockito.when(factory.answerChoice("Noite")).thenReturn(ac3);
		
		ejb.addQuestion(iScript, questionText, questionType, options);
		verify(iScript).addQuestion(questionText, questionType, set);
	}
	
	@Test(expected=ReservedQuestionException.class)
	public void shouldThrowExceptionWhenAddReservedQuestion3() throws IllegalQuestionTypeException, IllegalAnswerOptionsException, ReservedQuestionException {
		String questionText = "Interview's Global Appreciation";
		QuestionType questionType = QuestionType.MULTIPLE_CHOICE;
		List<String> options = Arrays.asList(new String[]{"Manhã", "Tarde", "Noite"});
		
		IAnswerChoice ac1 = Mockito.mock(IAnswerChoice.class);
		IAnswerChoice ac2 = Mockito.mock(IAnswerChoice.class);
		IAnswerChoice ac3 = Mockito.mock(IAnswerChoice.class);
		
		Set<IAnswerChoice> set = new HashSet<>();
		set.add(ac1);
		set.add(ac2);
		set.add(ac3);
		
		Mockito.when(factory.answerChoice("Manhã")).thenReturn(ac1);
		Mockito.when(factory.answerChoice("Tarde")).thenReturn(ac2);
		Mockito.when(factory.answerChoice("Noite")).thenReturn(ac3);
		
		ejb.addQuestion(iScript, questionText, questionType, options);
	}
	
	@Test(expected=IllegalQuestionTypeException.class)
	public void shouldThrowExceptionWhenAddQuestionWithoutMultipleChoice() throws IllegalQuestionTypeException, IllegalAnswerOptionsException, ReservedQuestionException {
		String questionText = "a question text";
		QuestionType questionType = QuestionType.SHORT_ANSWER;
		List<String> options = Arrays.asList(new String[]{"Manhã", "Tarde", "Noite"});
		ejb.addQuestion(iScript, questionText, questionType, options);
	}
	
	@Test(expected=IllegalAnswerOptionsException.class)
	public void shouldThrowExceptionWhenAddQuestionWithOneChoice() throws IllegalQuestionTypeException, IllegalAnswerOptionsException, ReservedQuestionException {
		String questionText = "a question text";
		QuestionType questionType = QuestionType.MULTIPLE_CHOICE;
		List<String> options = Arrays.asList(new String[]{"Manhã"});
		ejb.addQuestion(iScript, questionText, questionType, options);
	}
	
	@Test(expected=IllegalAnswerOptionsException.class)
	public void shouldThrowExceptionWhenAddQuestionWithNullChoices() throws IllegalQuestionTypeException, IllegalAnswerOptionsException, ReservedQuestionException {
		String questionText = "a question text";
		QuestionType questionType = QuestionType.MULTIPLE_CHOICE;
		List<String> options = null;
		ejb.addQuestion(iScript, questionText, questionType, options);
	}
	
	@Test(expected=IllegalAnswerOptionsException.class)
	public void shouldThrowExceptionWhenAddQuestionWithRepeatedChoice() throws IllegalQuestionTypeException, IllegalAnswerOptionsException, ReservedQuestionException {
		String questionText = "a question text";
		QuestionType questionType = QuestionType.MULTIPLE_CHOICE;
		List<String> options = Arrays.asList(new String[]{"Manhã", "Tarde", "Manhã"});
		ejb.addQuestion(iScript, questionText, questionType, options);
	}
	
	@Test(expected=IllegalAnswerOptionsException.class)
	public void shouldThrowExceptionWhenAddQuestionWithEmptyChoice() throws IllegalQuestionTypeException, IllegalAnswerOptionsException, ReservedQuestionException {
		String questionText = "a question text";
		QuestionType questionType = QuestionType.MULTIPLE_CHOICE;
		List<String> options = Arrays.asList(new String[]{"Manhã", "Tarde", ""});
		ejb.addQuestion(iScript, questionText, questionType, options);
	}
	
	@Test(expected=IllegalAnswerOptionsException.class)
	public void shouldThrowExceptionWhenAddQuestionWithNullChoice() throws IllegalQuestionTypeException, IllegalAnswerOptionsException, ReservedQuestionException {
		String questionText = "a question text";
		QuestionType questionType = QuestionType.MULTIPLE_CHOICE;
		List<String> options = Arrays.asList(new String[]{"Manhã", "Tarde", null});
		ejb.addQuestion(iScript, questionText, questionType, options);
	}
	
	@Test
	public void shouldNotThrowExceptionWhenAddQuestionWithRepeatedChoice2() throws IllegalQuestionTypeException, IllegalAnswerOptionsException, ReservedQuestionException {
		String questionText = "a question text";
		QuestionType questionType = QuestionType.MULTIPLE_CHOICE;
		List<String> options = Arrays.asList(new String[]{"Manhã", "Tarde", "manhã"});
		
		IAnswerChoice ac1 = Mockito.mock(IAnswerChoice.class);
		IAnswerChoice ac2 = Mockito.mock(IAnswerChoice.class);
		IAnswerChoice ac3 = Mockito.mock(IAnswerChoice.class);
		
		Set<IAnswerChoice> set = new HashSet<>();
		set.add(ac1);
		set.add(ac2);
		set.add(ac3);
		
		Mockito.when(factory.answerChoice(options.get(0).trim())).thenReturn(ac1);
		Mockito.when(factory.answerChoice(options.get(1).trim())).thenReturn(ac2);
		Mockito.when(factory.answerChoice(options.get(2).trim())).thenReturn(ac3);
		
		ejb.addQuestion(iScript, questionText, questionType, options);
		verify(iScript).addQuestion(questionText, questionType, set);
	}
	
	
	@Test
	public void shouldTrimAnswersWhenAddQuestion() throws IllegalQuestionTypeException, IllegalAnswerOptionsException, ReservedQuestionException {
		String questionText = "a question text";
		QuestionType questionType = QuestionType.MULTIPLE_CHOICE;
		List<String> options = Arrays.asList(new String[]{"  Manhã", "Tarde  ", " Noite  "});

		IAnswerChoice ac1 = Mockito.mock(IAnswerChoice.class);
		IAnswerChoice ac2 = Mockito.mock(IAnswerChoice.class);
		IAnswerChoice ac3 = Mockito.mock(IAnswerChoice.class);
		
		Set<IAnswerChoice> set = new HashSet<>();
		set.add(ac1);
		set.add(ac2);
		set.add(ac3);
		
		Mockito.when(factory.answerChoice(options.get(0).trim())).thenReturn(ac1);
		Mockito.when(factory.answerChoice(options.get(1).trim())).thenReturn(ac2);
		Mockito.when(factory.answerChoice(options.get(2).trim())).thenReturn(ac3);
		
		ejb.addQuestion(iScript, questionText, questionType, options);
		verify(iScript).addQuestion(questionText, questionType, set);
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
	public void shouldReorderQuestionsCorrectly() throws IllegalQuestionTypeException, ReservedQuestionException {
		ejb.addQuestion(iScript, "question1", QuestionType.SHORT_ANSWER);
		ejb.addQuestion(iScript, "question2", QuestionType.SHORT_ANSWER);
		ejb.addQuestion(iScript, "question3", QuestionType.SHORT_ANSWER);
		ejb.addQuestion(iScript, "question4", QuestionType.SHORT_ANSWER);
		ejb.moveQuestion(iScript, 2, 0);
		
		verify(iScript).moveQuestion(2, 0);
	}
	
	@Test
	public void shouldAddQuestionCorrectly() throws IllegalAnswerOptionsException {
		IScriptEntry entry = Mockito.mock(IScriptEntry.class);
		IAnswerChoice ac = Mockito.mock(IAnswerChoice.class);
		String option = "a";
		
		Mockito.when(factory.answerChoice(option.trim())).thenReturn(ac);
		
		ejb.addAnswerToEntry(iScript, entry, option);
		verify(iScript).addAnswerToEntry(entry, ac);
	}
	
	@Test
	public void shouldAddQuestionCorrectlyTrimmed() throws IllegalAnswerOptionsException {
		IScriptEntry entry = Mockito.mock(IScriptEntry.class);
		IAnswerChoice ac = Mockito.mock(IAnswerChoice.class);
		String option = "  a  ";
		
		Mockito.when(factory.answerChoice(option.trim())).thenReturn(ac);
		
		ejb.addAnswerToEntry(iScript, entry, option);
		verify(iScript).addAnswerToEntry(entry, ac);
	}
	
	@Test(expected=IllegalAnswerOptionsException.class)
	public void shouldThrowExceptionWhenAddEmptyOption() throws IllegalAnswerOptionsException {
		IScriptEntry entry = Mockito.mock(IScriptEntry.class);
		String option = "";
		
		ejb.addAnswerToEntry(iScript, entry, option);
	}
	
	@Test(expected=IllegalAnswerOptionsException.class)
	public void shouldThrowExceptionWhenAddNullOption() throws IllegalAnswerOptionsException {
		IScriptEntry entry = Mockito.mock(IScriptEntry.class);
		String option = null;
		
		ejb.addAnswerToEntry(iScript, entry, option);
	}
	
	@Test(expected=IllegalAnswerOptionsException.class)
	public void shouldThrowExceptionWhenAddRepeatedOption() throws IllegalAnswerOptionsException {
		IScriptEntry entry = Mockito.mock(IScriptEntry.class);
		IAnswerChoice ac1 = Mockito.mock(IAnswerChoice.class);
		IAnswerChoice ac2 = Mockito.mock(IAnswerChoice.class);
		List<IAnswerChoice> set = new ArrayList<>();
		set.add(ac1);
		set.add(ac2);
		
		String option = "repeated answer";

		Mockito.when(entry.getAnswers()).thenReturn(set);
		Mockito.when(factory.answerChoice(option)).thenReturn(ac2);
		Mockito.when(ac2.getText()).thenReturn(option);
		Mockito.when(ac1.getText()).thenReturn("something");
		
		ejb.addAnswerToEntry(iScript, entry, option);
	}
	
	@Test
	public void shouldRemoveQuestionCorrectly() throws IllegalAnswerOptionsException {
		IScriptEntry entry = Mockito.mock(IScriptEntry.class);
		IAnswerChoice ac = Mockito.mock(IAnswerChoice.class);
		String option = "a";
		
		Mockito.when(factory.answerChoice(option.trim())).thenReturn(ac);
		
		ejb.removeAnswerFromEntry(iScript, entry, option);
		verify(iScript).removeAnswerFromEntry(entry, ac);
	}
	
	@Test
	public void shouldRemoveQuestionCorrectlyTrimmed() throws IllegalAnswerOptionsException {
		IScriptEntry entry = Mockito.mock(IScriptEntry.class);
		IAnswerChoice ac = Mockito.mock(IAnswerChoice.class);
		String option = "  a  ";
		
		Mockito.when(factory.answerChoice(option.trim())).thenReturn(ac);
		
		ejb.removeAnswerFromEntry(iScript, entry, option);
		verify(iScript).removeAnswerFromEntry(entry, ac);
	}
	
	@Test(expected=IllegalAnswerOptionsException.class)
	public void shouldThrowExceptionWhenRemoveEmptyOption() throws IllegalAnswerOptionsException {
		IScriptEntry entry = Mockito.mock(IScriptEntry.class);
		String option = "";
		
		ejb.removeAnswerFromEntry(iScript, entry, option);
	}
	
	@Test(expected=IllegalAnswerOptionsException.class)
	public void shouldThrowExceptionWhenRemoveNullOption() throws IllegalAnswerOptionsException {
		IScriptEntry entry = Mockito.mock(IScriptEntry.class);
		String option = null;
		
		ejb.removeAnswerFromEntry(iScript, entry, option);
	}
	
	@Test(expected=IllegalAnswerOptionsException.class)
	public void shouldThrowExceptionWhenRemoveOneOfTwoOptions() throws IllegalAnswerOptionsException {
		IScriptEntry entry = Mockito.mock(IScriptEntry.class);
		IAnswerChoice ac1 = Mockito.mock(IAnswerChoice.class);
		IAnswerChoice ac2 = Mockito.mock(IAnswerChoice.class);
		List<IAnswerChoice> set = new ArrayList<>();
		set.add(ac1);
		set.add(ac2);
		
		Mockito.when(entry.getAnswers()).thenReturn(set);
		String option = "valid answer";
		
		ejb.removeAnswerFromEntry(iScript, entry, option);
	}
	
	@Test
	public void shouldCallCorrectFunctionWhenDeleteScript() {
		ejb.deleteScript(iScript);
		verify(scriptEjb).delete(iScript);
	}
	
	@Test
	public void shouldCallCorrectFunctionWhenDeleteEntry() {
		IScriptEntry entry = Mockito.mock(IScriptEntry.class);
		
		ejb.delete(iScript, entry);
		verify(iScript).deleteQuestion(entry);
		verify(scriptEjb).save(iScript);
	}
	
	@Test
	public void shouldCallCorrectFunctionWhenUpdateEntry() {
		IScriptEntry entry = Mockito.mock(IScriptEntry.class);
		
		ejb.updateEntry(entry);
		verify(entryEjb).save(entry);
	}
	
	@Test
	public void shouldCallCorrectFunctionWhenFindScriptById() {
		long id = 1L;
		
		ejb.findScriptById(id);
		verify(scriptEjb).findScriptById(id);
	}
}
