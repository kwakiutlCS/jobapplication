package pt.uc.dei.aor.project.business.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import pt.uc.dei.aor.project.business.exception.GenericIllegalParamsException;
import pt.uc.dei.aor.project.business.exception.IllegalAnswerOptionsException;
import pt.uc.dei.aor.project.business.exception.IllegalInterviewDeletionException;
import pt.uc.dei.aor.project.business.exception.IllegalQuestionTypeException;
import pt.uc.dei.aor.project.business.exception.IllegalScaleException;
import pt.uc.dei.aor.project.business.exception.RepeatedInterviewException;
import pt.uc.dei.aor.project.business.model.IAnswer;
import pt.uc.dei.aor.project.business.model.IAnswerChoice;
import pt.uc.dei.aor.project.business.model.IApplication;
import pt.uc.dei.aor.project.business.model.IInterview;
import pt.uc.dei.aor.project.business.model.IModelFactory;
import pt.uc.dei.aor.project.business.model.IScript;
import pt.uc.dei.aor.project.business.model.IScriptEntry;
import pt.uc.dei.aor.project.business.model.IWorker;
import pt.uc.dei.aor.project.business.persistence.IAnswerPersistenceService;
import pt.uc.dei.aor.project.business.persistence.IApplicationPersistenceService;
import pt.uc.dei.aor.project.business.persistence.IInterviewPersistenceService;
import pt.uc.dei.aor.project.business.persistence.IScriptPersistenceService;
import pt.uc.dei.aor.project.business.persistence.IWorkerPersistenceService;
import pt.uc.dei.aor.project.business.util.QuestionType;



@RunWith(MockitoJUnitRunner.class)
public class InterviewBusinessServiceTest {

	@Mock
	private IModelFactory factory;
	
	@Mock
	private IInterviewPersistenceService interviewEjb;
	
	@Mock
	private IWorkerPersistenceService workerEjb;
	
	@Mock
	private IApplicationPersistenceService applicationEjb;
	
	@Mock
	private IAnswerPersistenceService answerEjb;
	
	@InjectMocks
	InterviewBusinessService ejb;
	
	private List<IWorker> interviewers;
	private Date date;
	private IApplication application;
	private List<IAnswer> answers;
	
	
	@Before
	public void init() {
		interviewers = new ArrayList<>();
		interviewers.add(Mockito.mock(IWorker.class));
		interviewers.add(Mockito.mock(IWorker.class));
		
		long counter = 1;
		for (IWorker w : interviewers) {
			when(w.getId()).thenReturn(counter++);
		}
		
		date = mock(Date.class);
		
		application = mock(IApplication.class);
		when(application.getId()).thenReturn(1L);
		
		answers = new ArrayList<>();
		answers.add(mock(IAnswer.class));
		answers.add(mock(IAnswer.class));
		answers.add(mock(IAnswer.class));
	}
	
	
	@Test
	public void shouldCallCorrectFunctionWhenSearchingActiveInterviews() {
		IWorker user = mock(IWorker.class);
		
		ejb.findActiveInterviewsByUser(user);
		verify(interviewEjb).findActiveInterviewsByUser(user);
	}
	
	@Test(expected=GenericIllegalParamsException.class)
	public void shouldNotAddInterviewWithoutCorrectDate() throws GenericIllegalParamsException, RepeatedInterviewException {
		date = null;
		
		ejb.addInterview(application, date, interviewers);
	}
	
	@Test(expected=GenericIllegalParamsException.class)
	public void shouldNotAddInterviewWithoutCorrectApplication() throws GenericIllegalParamsException, RepeatedInterviewException {
		application = null;
		
		ejb.addInterview(application, date, interviewers);
	}
	
	@Test(expected=GenericIllegalParamsException.class)
	public void shouldNotAddInterviewWithoutCorrectInterviewers() throws GenericIllegalParamsException, RepeatedInterviewException {
		interviewers = null;
		
		ejb.addInterview(application, date, interviewers);
	}
	
	@Test
	public void shouldCallCorrectFunctionsWhenAddingInterviews() throws GenericIllegalParamsException, RepeatedInterviewException {
		IInterview mockedInterview = mock(IInterview.class);
		
		when(factory.interview(application, date)).thenReturn(mockedInterview);
		when(interviewEjb.save(mockedInterview)).thenReturn(mockedInterview);
		when(interviewEjb.findInterview(mockedInterview)).thenReturn(null);
		
		long counter = 1;
		for (IWorker w : interviewers) {
			when(w.getId()).thenReturn(counter++);
		}
		
		ejb.addInterview(application, date, interviewers);
				
		verify(factory).interview(application, date);
		verify(interviewEjb).save(mockedInterview);
		verify(applicationEjb).find(application.getId());
		
		for (IWorker w : interviewers) 
			verify(workerEjb).insertInterview(w.getId(), mockedInterview);
	}

	
	@Test(expected=RepeatedInterviewException.class)
	public void shouldThrowExceptionWithRepeatedInterview() throws GenericIllegalParamsException, RepeatedInterviewException {
IInterview mockedInterview = mock(IInterview.class);
		
		when(factory.interview(application, date)).thenReturn(mockedInterview);
		when(interviewEjb.save(mockedInterview)).thenReturn(mockedInterview);
		when(interviewEjb.findInterview(mockedInterview)).thenReturn(mockedInterview);
				
		ejb.addInterview(application, date, interviewers);
				
		verify(factory).interview(application, date);
		verify(interviewEjb, Mockito.never()).save(mockedInterview);
		verify(applicationEjb, Mockito.never()).find(application.getId());
		
		for (IWorker w : interviewers) 
			verify(workerEjb, Mockito.never()).insertInterview(w.getId(), mockedInterview);
	}
	
	
	@Test
	public void shouldCallTheCorrectFunctionToFindInterviewByApplication() {
		long id = 1L;
		
		Mockito.when(application.getId()).thenReturn(id);
		Mockito.when(applicationEjb.find(id)).thenReturn(application);
		
		ejb.findInterviewsByApplication(application);
		
		verify(applicationEjb).find(id);
	}
	
	@Test 
	public void shouldCallTheCorrectFunctionsToDeleteInterview() throws IllegalInterviewDeletionException {
		IInterview interview = mock(IInterview.class);
		long interview_id = 1L;
		
		when(interview.getInterviewers()).thenReturn(interviewers);
		when(interview.getId()).thenReturn(interview_id);
		when(interview.getApplication()).thenReturn(application);
		when(applicationEjb.find(application.getId())).thenReturn(application);
		when(answerEjb.findAnswersByInterview(interview)).thenReturn(new ArrayList<IAnswer>());
		
		ejb.delete(interview);
		
		verify(answerEjb).findAnswersByInterview(interview);
		
		for (IWorker w : interviewers) {
			verify(workerEjb).removeInterview(w.getId(), interview_id);
		}
		
		verify(interviewEjb).delete(interview);
		verify(applicationEjb).find(application.getId());
	}
	
	@Test(expected=IllegalInterviewDeletionException.class)
	public void shouldNotDeleteInterviewWithAnswers() throws IllegalInterviewDeletionException {
		IInterview interview = mock(IInterview.class);
		long interview_id = 1L;
		
		when(interview.getInterviewers()).thenReturn(interviewers);
		when(interview.getId()).thenReturn(interview_id);
		when(interview.getApplication()).thenReturn(application);
		when(applicationEjb.find(application.getId())).thenReturn(application);
		when(answerEjb.findAnswersByInterview(interview)).thenReturn(answers);
		
		ejb.delete(interview);
		
		verify(answerEjb).findAnswersByInterview(interview);
		
		for (IWorker w : interviewers) {
			verify(workerEjb).removeInterview(w.getId(), interview_id);
		}
		
		verify(interviewEjb).delete(interview);
		verify(applicationEjb).find(application.getId());
	}
	
	
	@Test
	public void shouldCallCorrectMethodsWhenFindInterviewById() {
		long id = 1L;
		
		ejb.findInterviewById(id);
		
		verify(interviewEjb).findInterviewById(id);
	}
}
