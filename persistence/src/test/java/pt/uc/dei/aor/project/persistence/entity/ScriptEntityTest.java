package pt.uc.dei.aor.project.persistence.entity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Before;
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

public class ScriptEntityTest {

	private ScriptEntity script = new ScriptEntity();
		
	@Before
	public void init() {
		script.setEntries(new TreeSet<>());
	}
	
	@Test
	public void shouldReturnCorrectNextPosition() {
		script.getEntries().add(new ScriptEntryEntity("text", "Resposta curta", 0));
		assertThat(script.getNextPosition(), is(equalTo(1)));
	}
	
	@Test
	public void shouldReturnZeroPositionWhenEmpty() {
		assertThat(script.getNextPosition(), is(equalTo(0)));
	}
	
	
	
}
