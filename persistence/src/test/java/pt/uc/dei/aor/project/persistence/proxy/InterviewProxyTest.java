package pt.uc.dei.aor.project.persistence.proxy;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import pt.uc.dei.aor.project.business.model.IApplication;
import pt.uc.dei.aor.project.business.model.IWorker;
import pt.uc.dei.aor.project.business.util.Role;
import pt.uc.dei.aor.project.persistence.entity.ApplicationEntity;
import pt.uc.dei.aor.project.persistence.entity.InterviewEntity;
import pt.uc.dei.aor.project.persistence.entity.ScriptEntryEntity;
import pt.uc.dei.aor.project.persistence.service.GenericPersistenceService;


public class InterviewProxyTest {

	private InterviewProxy proxy;
	IApplication application;
	
	@Before
	public void init() {
		application  = new ApplicationProxy();
		proxy = new InterviewProxy(application, new Date());
	}
	
	@Ignore
	@Test
	public void shouldFormatInterviewersCorrectly() {
		//String expected = workerProxies.get(0).getFullName()+", "+workerProxies.get(1).getFullName();
		//assertThat(proxy.getInterviewersFormatted(), is(equalTo(expected)));
	}
	
	@Test
	public void interviewForTodayShouldNotBeEditable() {
		assertThat(proxy.isEditable(), is(equalTo(false)));
	}
	
	@Test
	public void interviewForTommorrowShouldBeEditable() {
		IApplication application = new ApplicationProxy();
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, 1);
		
		proxy = new InterviewProxy(application, cal.getTime());
		
		assertThat(proxy.isEditable(), is(equalTo(true)));
	}
	
	@Test
	public void shouldRetrieveCorrectEntity() {
		InterviewEntity entity = GenericPersistenceService.getEntity(proxy);
		
		ApplicationEntity applicationEntity = GenericPersistenceService.getEntity(application);
		
		assertThat(entity.getApplication(), is(equalTo(applicationEntity)));
	}
}
