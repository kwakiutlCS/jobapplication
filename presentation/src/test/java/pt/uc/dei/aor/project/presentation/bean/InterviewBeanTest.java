package pt.uc.dei.aor.project.presentation.bean;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import pt.uc.dei.aor.project.business.exception.AllPhasesCompletedException;
import pt.uc.dei.aor.project.business.model.IInterview;
import pt.uc.dei.aor.project.business.model.IScriptEntry;
import pt.uc.dei.aor.project.business.service.IInterviewBusinessService;
import pt.uc.dei.aor.project.business.util.QuestionType;


@RunWith(MockitoJUnitRunner.class)
public class InterviewBeanTest {
	
	@Mock
	private IInterview selectedInterview;
	
	@Mock
	private List<String> other;
	
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
	
	@Test
	public void shouldGetNextQuestionCorrectly() throws AllPhasesCompletedException{
		int index = 1;
		
		when(selectedEntry.getQuestionType()).thenReturn(QuestionType.LONG_ANSWER);
		when(scriptEntries.indexOf(selectedEntry)).thenReturn(index);
		when(scriptEntries.get(index+1)).thenReturn(selectedEntry);
		when(answersGiven.get(index+1)).thenReturn(false);
			
		bean.saveAnswer();
		
		verify(interviewService).saveAnswer(Mockito.eq(selectedInterview), Mockito.anyString(), 
				Mockito.eq(selectedEntry));
		verify(answersGiven).set(index, true);
		verify(scriptEntries).get(index+1);
		verify(scriptEntries, never()).get(index+2);
	}
	
	@Test
	public void shouldIgnoreQuestionsCorrectly() throws AllPhasesCompletedException{
		int index = 0;
		
		when(selectedEntry.getQuestionType()).thenReturn(QuestionType.LONG_ANSWER);
		when(scriptEntries.indexOf(selectedEntry)).thenReturn(index);
		when(scriptEntries.get(Mockito.anyInt())).thenReturn(selectedEntry);
		when(answersGiven.get(index+1)).thenReturn(true);
		when(answersGiven.get(index+2)).thenReturn(true);
		when(answersGiven.get(index+3)).thenReturn(false);
			
		bean.saveAnswer();
		
		verify(interviewService).saveAnswer(Mockito.eq(selectedInterview), Mockito.anyString(), 
				Mockito.eq(selectedEntry));
		verify(answersGiven).set(index, true);
		verify(scriptEntries).get(index+3);
		verify(scriptEntries, never()).get(index+4);
	}
	
	
	@Test
	public void shouldRestartCorrectly() throws AllPhasesCompletedException{
		IScriptEntry otherEntry = mock(IScriptEntry.class);
		
		int index = 2;
		
		when(selectedEntry.getQuestionType()).thenReturn(QuestionType.LONG_ANSWER);
		when(otherEntry.getQuestionType()).thenReturn(QuestionType.LONG_ANSWER);
		when(scriptEntries.indexOf(selectedEntry)).thenReturn(index);
		when(scriptEntries.get(Mockito.anyInt())).thenReturn(otherEntry);
		when(scriptEntries.size()).thenReturn(4);
		when(answersGiven.get(index+1)).thenReturn(true);
		when(answersGiven.get(index+2)).thenReturn(true);
		when(answersGiven.get(0)).thenReturn(false);
			
		bean.saveAnswer();
		
		verify(interviewService).saveAnswer(Mockito.eq(selectedInterview), Mockito.anyString(), 
				Mockito.eq(selectedEntry));
		verify(answersGiven).set(index, true);
		verify(scriptEntries).get(0);
		verify(scriptEntries, never()).get(1);
	}
	
	@Test
	public void shouldGiveLastQuestionIfFinish() throws AllPhasesCompletedException{
		IScriptEntry otherEntry = mock(IScriptEntry.class);
		
		int index = 1;
		int size = 4;
		
		when(selectedEntry.getQuestionType()).thenReturn(QuestionType.LONG_ANSWER);
		when(otherEntry.getQuestionType()).thenReturn(QuestionType.LONG_ANSWER);
		when(scriptEntries.indexOf(selectedEntry)).thenReturn(index);
		when(scriptEntries.get(Mockito.anyInt())).thenReturn(otherEntry);
		when(scriptEntries.size()).thenReturn(size);
		when(answersGiven.get(Mockito.anyInt())).thenReturn(true);
		
		bean.saveAnswer();
		
		verify(interviewService).saveAnswer(Mockito.eq(selectedInterview), Mockito.anyString(), 
				Mockito.eq(selectedEntry));
		verify(answersGiven).set(index, true);
		verify(scriptEntries).get(size-1);
	}
}
