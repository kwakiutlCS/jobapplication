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
import pt.uc.dei.aor.project.business.exception.IllegalQuestionTypeException;
import pt.uc.dei.aor.project.business.exception.IllegalScaleException;
import pt.uc.dei.aor.project.business.exception.RepeatedInterviewException;
import pt.uc.dei.aor.project.business.model.IAnswerChoice;
import pt.uc.dei.aor.project.business.model.IApplication;
import pt.uc.dei.aor.project.business.model.IInterview;
import pt.uc.dei.aor.project.business.model.IModelFactory;
import pt.uc.dei.aor.project.business.model.IScript;
import pt.uc.dei.aor.project.business.model.IScriptEntry;
import pt.uc.dei.aor.project.business.model.IWorker;
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
	
	@InjectMocks
	InterviewBusinessService ejb;
	
	private List<IWorker> interviewers;
	private Date date;
	private IApplication application;
	
	
	@Before
	public void init() {
		interviewers = new ArrayList<>();
		interviewers.add(Mockito.mock(IWorker.class));
		interviewers.add(Mockito.mock(IWorker.class));
		
		date = mock(Date.class);
		
		application = mock(IApplication.class);
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
		
		long counter = 1;
		for (IWorker w : interviewers) {
			when(w.getId()).thenReturn(counter++);
		}
		
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
}
