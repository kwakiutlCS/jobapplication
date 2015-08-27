package pt.uc.dei.aor.project.business.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import pt.uc.dei.aor.project.business.exception.NoRoleException;
import pt.uc.dei.aor.project.business.model.IModelFactory;
import pt.uc.dei.aor.project.business.model.IWorker;
import pt.uc.dei.aor.project.business.persistence.IWorkerPersistenceService;
import pt.uc.dei.aor.project.business.util.Role;

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
	public void shouldCreateAnUserCorrectly() throws NoRoleException {
		String login = "inexistentUser";
		String name = "name";
		String surname = "surname";
		String email = "email";
		String password = "password";
		List<Role> roles = new ArrayList<>();
    	roles.add(Role.ADMIN);
    	
		IWorker worker = factory.worker("other", email, password, name, surname, roles);
		when(factory.worker(login, email, password, name, surname, roles)).thenReturn(worker);
		
		assertThat(ejb.getWorkerByLogin(login), is(equalTo(null)));
		
		ejb.createNewWorker(login, name, surname, email, password, roles);
		
		verify(factory).worker(login, email, password, name, surname, roles);
		verify(workerEjb).save(worker);
	}
	
	@Test(expected=NoRoleException.class)
	public void shouldNotCreateAnUserWithoutRole() throws NoRoleException {
		String login = "inexistentUser";
		String name = "name";
		String surname = "surname";
		String email = "email";
		String password = "password";
		List<Role> roles = new ArrayList<>();
    	
		IWorker worker = factory.worker("other", email, password, name, surname, roles);
		when(factory.worker(login, email, password, name, surname, roles)).thenReturn(worker);
		
		assertThat(ejb.getWorkerByLogin(login), is(equalTo(null)));
		
		ejb.createNewWorker(login, name, surname, email, password, roles);
	}
	
	@Test
	public void shouldCallCorrectMethodWhenListingUsers() {
		ejb.findAllUsers();
		verify(workerEjb).findAllUsers();
	}
	
	@Test
	public void shouldReturnListRoles() {
		List<Role> roles = ejb.getRoles();
		assertThat(roles.size(), is(equalTo(3)));
	}
}
