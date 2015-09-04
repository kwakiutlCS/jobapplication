package pt.uc.dei.aor.project.persistence.proxy;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import pt.uc.dei.aor.project.business.exception.IllegalScaleException;
import pt.uc.dei.aor.project.business.model.IScript;
import pt.uc.dei.aor.project.business.model.IScriptEntry;
import pt.uc.dei.aor.project.business.persistence.IScriptPersistenceService;
import pt.uc.dei.aor.project.business.util.QuestionType;
import pt.uc.dei.aor.project.persistence.entity.ScriptEntity;
import pt.uc.dei.aor.project.persistence.entity.ScriptEntryEntity;
import pt.uc.dei.aor.project.persistence.service.GenericPersistenceService;

public class ScriptProxyTest {

	private IScript script;
	
	@Before
	public void init() {
		script = new ScriptProxy("title");
				
		script.addQuestion("question 1", QuestionType.SCALE, 1, 5);
		script.addQuestion("question 2", QuestionType.SCALE, 1, 5);
		script.addQuestion("question 3", QuestionType.SCALE, 1, 5);
		script.addQuestion("question 4", QuestionType.SCALE, 1, 5);
		script.addQuestion("question 5", QuestionType.SCALE, 1, 5);
		script.addQuestion("question 6", QuestionType.SCALE, 1, 5);
		
	}
	
	@Test
	public void shouldMoveQuestionFromLowToHigh() throws IllegalScaleException {
		script.moveQuestion(1, 4);
		List<IScriptEntry> set = script.getEntries();
		
		assertThat(set.remove(0).getText(), is(equalTo("question 1")));
		assertThat(set.remove(0).getText(), is(equalTo("question 3")));
		assertThat(set.remove(0).getText(), is(equalTo("question 4")));
		assertThat(set.remove(0).getText(), is(equalTo("question 5")));
		assertThat(set.remove(0).getText(), is(equalTo("question 2")));
		assertThat(set.remove(0).getText(), is(equalTo("question 6")));
	}
	
	@Test
	public void shouldMoveQuestionFromLowToHighWithDelete() throws IllegalScaleException {
		List<IScriptEntry> set = script.getEntries();
		script.deleteQuestion(set.get(2));
		
		set = script.getEntries();
		script.moveQuestion(1, 3);
		set = script.getEntries();
		
		assertThat(set.remove(0).getText(), is(equalTo("question 1")));
		assertThat(set.remove(0).getText(), is(equalTo("question 4")));
		assertThat(set.remove(0).getText(), is(equalTo("question 5")));
		assertThat(set.remove(0).getText(), is(equalTo("question 2")));
		assertThat(set.remove(0).getText(), is(equalTo("question 6")));
	}
	
	@Test
	public void shouldMoveQuestionFromHighToLow() throws IllegalScaleException {
		script.moveQuestion(4, 1);
		List<IScriptEntry> set = script.getEntries();
		
		assertThat(set.remove(0).getText(), is(equalTo("question 1")));
		assertThat(set.remove(0).getText(), is(equalTo("question 5")));
		assertThat(set.remove(0).getText(), is(equalTo("question 2")));
		assertThat(set.remove(0).getText(), is(equalTo("question 3")));
		assertThat(set.remove(0).getText(), is(equalTo("question 4")));
		assertThat(set.remove(0).getText(), is(equalTo("question 6")));
		
	}
	
	@Test
	public void shouldMoveQuestionFromHighToLowWithDelete() throws IllegalScaleException {
		List<IScriptEntry> set = script.getEntries();
		script.deleteQuestion(set.get(2));
		
		set = script.getEntries();
		script.moveQuestion(3, 1);
		set = script.getEntries();
		
		assertThat(set.remove(0).getText(), is(equalTo("question 1")));
		assertThat(set.remove(0).getText(), is(equalTo("question 5")));
		assertThat(set.remove(0).getText(), is(equalTo("question 2")));
		assertThat(set.remove(0).getText(), is(equalTo("question 4")));
		assertThat(set.remove(0).getText(), is(equalTo("question 6")));
	}
	
	@Test
	public void shouldSupportMultipleMoves() {
		script.moveQuestion(0, 5);
		script.moveQuestion(4, 0);
		List<IScriptEntry> set = script.getEntries();
		
		assertThat(set.remove(0).getText(), is(equalTo("question 6")));
		assertThat(set.remove(0).getText(), is(equalTo("question 2")));
		assertThat(set.remove(0).getText(), is(equalTo("question 3")));
		assertThat(set.remove(0).getText(), is(equalTo("question 4")));
		assertThat(set.remove(0).getText(), is(equalTo("question 5")));
		assertThat(set.remove(0).getText(), is(equalTo("question 1")));
	}
	
	@Test
	public void shouldAddScriptWithCorrectTitle() {
		String title = "title";
		IScript script = new ScriptProxy(title);
		assertThat(script.getTitle(), is(equalTo(title)));
	}
	
	@Test
	public void shouldModifyScriptTitle() {
		String title = "otherTitle";
		script.setTitle(title);
		assertThat(script.getTitle(), is(equalTo(title)));
	}
	
	
	@Test
	public void shouldRetrieveCorrectEntity() {
		String title = "title";
		IScript script = new ScriptProxy(title);
		
		ScriptEntity entity = GenericPersistenceService.getEntity(script);
		
		assertThat(entity.getTitle(), is(equalTo(title)));
	}
}
