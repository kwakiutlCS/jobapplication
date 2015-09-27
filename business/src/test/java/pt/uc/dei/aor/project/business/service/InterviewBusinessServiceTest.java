package pt.uc.dei.aor.project.business.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;

import pt.uc.dei.aor.project.business.exception.AllPhasesCompletedException;
import pt.uc.dei.aor.project.business.exception.GenericIllegalParamsException;
import pt.uc.dei.aor.project.business.exception.IllegalInterviewDeletionException;
import pt.uc.dei.aor.project.business.exception.RepeatedInterviewException;
import pt.uc.dei.aor.project.business.model.IAnswer;
import pt.uc.dei.aor.project.business.model.IApplication;
import pt.uc.dei.aor.project.business.model.IInterview;
import pt.uc.dei.aor.project.business.model.IModelFactory;
import pt.uc.dei.aor.project.business.model.IPosition;
import pt.uc.dei.aor.project.business.model.IScript;
import pt.uc.dei.aor.project.business.model.IUser;
import pt.uc.dei.aor.project.business.persistence.IAnswerPersistenceService;
import pt.uc.dei.aor.project.business.persistence.IApplicationPersistenceService;
import pt.uc.dei.aor.project.business.persistence.IInterviewPersistenceService;
import pt.uc.dei.aor.project.business.persistence.IUserPersistenceService;
import pt.uc.dei.aor.project.business.persistence.IUserPersistenceService;



@RunWith(MockitoJUnitRunner.class)
public class InterviewBusinessServiceTest {

	@Mock
	private Logger logger;
	
	@Mock
	private IModelFactory factory;
	
	@Mock
	private IInterviewPersistenceService interviewEjb;
	
	@Mock
	private IUserPersistenceService workerEjb;
	
	@Mock
	private IApplicationPersistenceService applicationEjb;
	
	@Mock
	private IAnswerPersistenceService answerEjb;
	
	@Mock
	private INotificationBusinessService notificationEjb;
	
	@InjectMocks
	InterviewBusinessService ejb;
	
	private List<IUser> interviewers;
	private Date date;
	private IApplication application;
	private List<IAnswer> answers;
	private IPosition position;
	private IScript script;
	
	
	@Before
	public void init() {
		interviewers = new ArrayList<>();
		interviewers.add(Mockito.mock(IUser.class));
		interviewers.add(Mockito.mock(IUser.class));
		
		long counter = 1;
		for (IUser w : interviewers) {
			when(w.getId()).thenReturn(counter++);
		}
		
		date = mock(Date.class);
		
		application = mock(IApplication.class);
		when(application.getId()).thenReturn(1L);
		
		answers = new ArrayList<>();
		answers.add(mock(IAnswer.class));
		answers.add(mock(IAnswer.class));
		answers.add(mock(IAnswer.class));
		
		position = mock(IPosition.class);
		script = mock(IScript.class);
	}
	
	
	@Test
	public void shouldCallCorrectFunctionWhenSearchingActiveInterviews() {
		IUser user = mock(IUser.class);
		
		ejb.findActiveInterviewsByUser(user);
		verify(interviewEjb).findActiveInterviewsByUser(user);
	}
	
	@Test(expected=GenericIllegalParamsException.class)
	public void shouldNotAddInterviewWithoutCorrectDate() 
			throws GenericIllegalParamsException, RepeatedInterviewException, AllPhasesCompletedException {
		date = null;
		
		ejb.addInterview(application, date, interviewers);
	}
	
	@Test(expected=GenericIllegalParamsException.class)
	public void shouldNotAddInterviewWithoutCorrectApplication() 
			throws GenericIllegalParamsException, RepeatedInterviewException, AllPhasesCompletedException {
		application = null;
		
		ejb.addInterview(application, date, interviewers);
	}
	
	@Test(expected=GenericIllegalParamsException.class)
	public void shouldNotAddInterviewWithoutCorrectInterviewers() 
			throws GenericIllegalParamsException, RepeatedInterviewException, AllPhasesCompletedException {
		interviewers = null;
		
		ejb.addInterview(application, date, interviewers);
	}
	
	@Ignore
	@Test
	public void shouldCallCorrectFunctionsWhenAddingInterviews() 
			throws GenericIllegalParamsException, RepeatedInterviewException, AllPhasesCompletedException {
		IInterview mockedInterview = mock(IInterview.class);
		
		when(factory.interview(date)).thenReturn(mockedInterview);
		when(interviewEjb.save(mockedInterview)).thenReturn(mockedInterview);
		when(interviewEjb.findInterview(mockedInterview)).thenReturn(null);
		doNothing().when(logger).info(Mockito.anyString());
		
		long counter = 1;
		for (IUser w : interviewers) {
			when(w.getId()).thenReturn(counter++);
		}
		
		ejb.addInterview(application, date, interviewers);
				
		verify(factory).interview(date);
		verify(interviewEjb).save(mockedInterview);
		verify(applicationEjb).find(application.getId());
		
		for (IUser w : interviewers) 
			verify(workerEjb).insertInterview(w.getId(), mockedInterview);
	}

	@Ignore
	@Test(expected=RepeatedInterviewException.class)
	public void shouldThrowExceptionWithRepeatedInterview() 
			throws GenericIllegalParamsException, RepeatedInterviewException, AllPhasesCompletedException {
IInterview mockedInterview = mock(IInterview.class);
		
		when(factory.interview(date)).thenReturn(mockedInterview);
		when(interviewEjb.save(mockedInterview)).thenReturn(mockedInterview);
		when(interviewEjb.findInterview(mockedInterview)).thenReturn(mockedInterview);
				
		ejb.addInterview(application, date, interviewers);
				
		verify(factory).interview(date);
		verify(interviewEjb, Mockito.never()).save(mockedInterview);
		verify(applicationEjb, Mockito.never()).find(application.getId());
		
		for (IUser w : interviewers) 
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
		
		ejb.delete(application, interview);
		
		verify(answerEjb).findAnswersByInterview(interview);
		
		for (IUser w : interviewers) {
			verify(workerEjb).removeInterview(w.getId(), interview_id);
		}
		
		verify(interviewEjb).delete(interview);
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
		
		ejb.delete(application, interview);
		
		verify(answerEjb).findAnswersByInterview(interview);
		
		for (IUser w : interviewers) {
			verify(workerEjb).removeInterview(w.getId(), interview_id);
		}
		
		verify(interviewEjb).delete(interview);
	}
	
	
	@Test
	public void shouldCallCorrectMethodsWhenFindInterviewById() {
		long id = 1L;
		
		ejb.findInterviewById(id);
		
		verify(interviewEjb).findInterviewById(id);
	}
	
	@Test
	public void shouldCallCorrectFunctionsWhenFindScriptEntries() throws AllPhasesCompletedException {
		IInterview interview = mock(IInterview.class);
		List<IScript> scripts = new ArrayList<>();
		scripts.add(script);
		when(interview.getApplication()).thenReturn(application);
		when(application.getPosition()).thenReturn(position);
		when(position.getScripts()).thenReturn(scripts);
		when(interview.getInterviewPhase()).thenReturn(1);
		
		ejb.getScriptEntries(interview);
		
		verify(interview).getApplication();
		verify(application).getPosition();
		verify(position).getScripts();
	}
	
	@Test
	public void shouldReturnNullWhenPositionHasNoScriptInFindScriptEntries() throws AllPhasesCompletedException {
		IInterview interview = mock(IInterview.class);
		List<IScript> scripts = new ArrayList<>();
		
		when(interview.getApplication()).thenReturn(application);
		when(application.getPosition()).thenReturn(position);
		when(position.getScripts()).thenReturn(scripts);
		when(interview.getInterviewPhase()).thenReturn(1);
		
		assertThat(ejb.getScriptEntries(interview), is(equalTo(null)));
	}
}
