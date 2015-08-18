package pt.uc.dei.aor.project.persistence.entity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;

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
