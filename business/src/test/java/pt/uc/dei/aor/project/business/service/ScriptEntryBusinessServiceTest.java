package pt.uc.dei.aor.project.business.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import pt.uc.dei.aor.project.business.exception.IllegalAnswerOptionsException;
import pt.uc.dei.aor.project.business.model.IScriptEntry;
import pt.uc.dei.aor.project.business.persistence.IScriptEntryPersistenceService;

@RunWith(MockitoJUnitRunner.class)
public class ScriptEntryBusinessServiceTest {

	@Mock
	private IScriptEntryPersistenceService persistenceEjb;
	
	@Mock
	private IScriptEntry iScriptEntry;
	
	@InjectMocks
	ScriptEntryBusinessService ejb;
	
	
	@Test
	public void shouldCallMethodWhenSave() {
		ejb.save(iScriptEntry);
		verify(persistenceEjb).save(iScriptEntry);
	}
	
	@Test
	public void shouldCallMethodWhenDelete() {
		ejb.delete(iScriptEntry);
		verify(persistenceEjb).delete(iScriptEntry);
	}
	
	@Test
	public void shouldCallMethodWhenAddAnswer() throws IllegalAnswerOptionsException {
		String option = "option_c";
		List<String> list = Arrays.asList(new String[]{"option_a","option_b"});
		when(iScriptEntry.getAnswers()).thenReturn(list);
		
		ejb.addAnswer(iScriptEntry, option);
		verify(iScriptEntry).addAnswer(option);
		verify(persistenceEjb).save(iScriptEntry);
	}
	
	@Test
	public void shouldCallMethodWhenAddAnswerTrimmed() throws IllegalAnswerOptionsException {
		String option = "   option_c  ";
		List<String> list = Arrays.asList(new String[]{"option_a","option_b"});
		when(iScriptEntry.getAnswers()).thenReturn(list);
		
		ejb.addAnswer(iScriptEntry, option);
		verify(iScriptEntry).addAnswer(option.trim());
		verify(persistenceEjb).save(iScriptEntry);
	}
	
	@Test(expected=IllegalAnswerOptionsException.class)
	public void shouldThrowExceptionWhenAddAnswer() throws IllegalAnswerOptionsException {
		String option = "option_a";
		List<String> list = Arrays.asList(new String[]{"option_a","option_b"});
		when(iScriptEntry.getAnswers()).thenReturn(list);
		
		ejb.addAnswer(iScriptEntry, option);
	}
	
}