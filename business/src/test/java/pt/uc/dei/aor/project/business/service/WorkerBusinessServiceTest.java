package pt.uc.dei.aor.project.business.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.matchers.InstanceOf;
import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.hamcrest.core.IsInstanceOf;

import pt.uc.dei.aor.project.business.model.IModelFactory;
import pt.uc.dei.aor.project.business.model.IWorker;
import pt.uc.dei.aor.project.business.persistence.IWorkerPersistenceService;

@RunWith(MockitoJUnitRunner.class)
public class WorkerBusinessServiceTest {

	@Mock
	private IModelFactory factory;
	
	@Mock
	private IWorkerPersistenceService workerEjb;
	
	@Mock
	private IWorker iWorker;
	
	@InjectMocks
	WorkerBusinessService ejb;
	
	@Test
	public void shouldCallCorrectMethodWhenFindUserByLogin() {
		String login = "login";
		ejb.getWorkerByLogin(login);
		
		verify(workerEjb).getWorkerByLogin(login);
	}
	
	@Test
	public void shouldCallCorrectMethodWhenDeletingUser() {
		ejb.deleteWorker(iWorker);
		
		verify(workerEjb).delete(iWorker);
	}
	
	@Test
	public void shouldCreateAnUserCorrectly() {
		String login = "inexistentUser";
		String name = "name";
		String surname = "surname";
		String email = "email";
		String password = "password";
		
		IWorker worker = factory.worker("other", email, password, name, surname);
		when(factory.worker(login, email, password, name, surname)).thenReturn(worker);
		
		assertThat(ejb.getWorkerByLogin(login), is(equalTo(null)));
		
		ejb.createNewWorker(login, name, surname, email, password);
		
		verify(factory).worker(login, email, password, name, surname);
		verify(workerEjb).save(worker);
	}
	
}
