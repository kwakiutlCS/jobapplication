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
import org.junit.Test;

import pt.uc.dei.aor.project.business.model.IApplication;
import pt.uc.dei.aor.project.business.model.IWorker;
import pt.uc.dei.aor.project.business.util.Role;

public class InterviewProxyTest {

	private InterviewProxy proxy;
	private List<IWorker> workerProxies;
	
	@Before
	public void init() {
		workerProxies = new ArrayList<>();
		List<Role> roles = Arrays.asList(new Role[]{Role.INTERVIEWER});
		
		workerProxies.add(new WorkerProxy("worker1", "email1", "surname1", "name1", "pass1", roles));
		workerProxies.add(new WorkerProxy("worker2", "email2", "surname2", "name2", "pass2", roles));
		
		IApplication application = new ApplicationProxy();
		
		proxy = new InterviewProxy(application, new Date(), workerProxies);
	}
	
	
	@Test
	public void shouldFormatInterviewersCorrectly() {
		String expected = workerProxies.get(0).getFullName()+", "+workerProxies.get(1).getFullName();
		assertThat(proxy.getInterviewersFormatted(), is(equalTo(expected)));
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
		
		proxy = new InterviewProxy(application, cal.getTime(), workerProxies);
		
		assertThat(proxy.isEditable(), is(equalTo(true)));
	}
}
