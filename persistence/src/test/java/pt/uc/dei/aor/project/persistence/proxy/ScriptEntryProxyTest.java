package pt.uc.dei.aor.project.persistence.proxy;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.TreeSet;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import pt.uc.dei.aor.project.business.exception.IllegalScaleException;
import pt.uc.dei.aor.project.business.model.IScript;
import pt.uc.dei.aor.project.business.model.IScriptEntry;
import pt.uc.dei.aor.project.business.util.QuestionType;
import pt.uc.dei.aor.project.persistence.entity.ScriptEntity;
import pt.uc.dei.aor.project.persistence.entity.ScriptEntryEntity;

public class ScriptEntryProxyTest {

	private IScript script = new ScriptProxy("title");
	private IScriptEntry entry;
		
	@Before
	public void init() {
		script.addQuestion("question 1", QuestionType.SCALE, 1, 5);
		entry = script.getEntries().get(0);
	}
	
	@Test
	public void shouldNotModifyMinScaleToIllegalValue() throws IllegalScaleException {
		entry.setMin("5");
		assertThat(entry.getMin(), is(equalTo("1")));
	}
	
	@Test
	public void shouldNotModifyMaxScaleToIllegalValue() throws IllegalScaleException {
		entry.setMax("0");
		assertThat(entry.getMax(), is(equalTo("5")));
	}
	
	@Test
	public void shouldModifyMinScaleToLegalValue() throws IllegalScaleException {
		entry.setMin("3");
		assertThat(entry.getMin(), is(equalTo("3")));
	}
	
	@Test
	public void shouldModifyMaxScaleToLegalValue() throws IllegalScaleException {
		entry.setMax("12");
		assertThat(entry.getMax(), is(equalTo("12")));
	}
	
	
	@Test
	public void shouldRetrieveCorrectEntity() {
		@SuppressWarnings("unchecked")
		ScriptEntryEntity entity = ((IProxyToEntity<ScriptEntryEntity>) entry).getEntity();
		
		assertThat(entity.getQuestion().getText(), is(equalTo("question 1")));
	}
}
