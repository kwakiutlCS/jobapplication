package pt.uc.dei.aor.project.presentation.bean;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import pt.uc.dei.aor.project.business.model.IInterview;
import pt.uc.dei.aor.project.business.model.IScriptEntry;
import pt.uc.dei.aor.project.business.service.IInterviewBusinessService;
import pt.uc.dei.aor.project.business.util.QuestionType;


@RunWith(MockitoJUnitRunner.class)
public class InterviewBeanTest {
	
	@Mock
	private IScriptEntry selectedEntry;
	
	@Mock
	private List<Boolean> answersGiven;
	
	@Mock
	private List<IScriptEntry> scriptEntries;
	
	@Mock
	private IInterviewBusinessService interviewService;
	
	@InjectMocks
	private InterviewBean bean;

	
	@Before
	public void init() {
		
	}
	
	@Ignore
	@Test
	public void shouldGetNextQuestionCorrectly(){
		//IScriptEntry selectedEntry = mock(IScriptEntry.class);
		IInterview interview = mock(IInterview.class);
		
		when(selectedEntry.getQuestionType()).thenReturn(QuestionType.LONG_ANSWER);
		when(scriptEntries.indexOf(selectedEntry)).thenReturn(0);
		
		
		bean.saveAnswer();
		
		verify(interviewService).saveAnswer(interview, Mockito.anyString(), selectedEntry);
		verify(answersGiven).set(0, true);
	}
	
	
	

}
