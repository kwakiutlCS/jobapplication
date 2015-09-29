package pt.uc.dei.aor.project.business.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import pt.uc.dei.aor.project.business.model.IAnswer;
import pt.uc.dei.aor.project.business.model.IInterview;
import pt.uc.dei.aor.project.business.persistence.IAnswerPersistenceService;
import pt.uc.dei.aor.project.business.persistence.IInterviewPersistenceService;
import pt.uc.dei.aor.project.business.util.DataModel;



@RunWith(MockitoJUnitRunner.class)
public class ReportBusinessServiceTest {
	
	@Mock
	private IInterviewPersistenceService interviewPersistence;
	
	@Mock
	private IAnswerPersistenceService answerPersistence;
	
	@InjectMocks
	private ReportBusinessService reportService;
	
	private List<IInterview> interviews;
	
	private List<IAnswer> answers;
	
	
	@Before
	public void init() {
		interviews = new ArrayList<>();
		interviews.add(mock(IInterview.class));
		interviews.add(mock(IInterview.class));
		interviews.add(mock(IInterview.class));
		
		answers = new ArrayList<>();
		Mockito.when(answerPersistence.findAnswersByInterview(Mockito.any())).thenReturn(answers);
	}
	
	@Ignore
	@Test
	public void shouldPresentRejectedInterviews() {
		Mockito.when(interviewPersistence.findInterviewsByClosedPositionAndDate(null))
		.thenReturn(interviews);
		
		DataModel<String, Long> model = reportService.generateInterviewReport(1);
		System.out.println(model.getPoints().get(3).getY());
		
		Mockito.verify(answerPersistence).findAnswersByInterview(Mockito.any());
		
		assertThat(model.getPoints().get(3).getY(), 
				is(org.hamcrest.Matchers.equalTo(0L)));
	}
}
