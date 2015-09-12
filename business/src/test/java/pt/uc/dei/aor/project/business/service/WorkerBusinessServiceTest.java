package pt.uc.dei.aor.project.business.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.eq;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ejb.EJBException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import pt.uc.dei.aor.project.business.exception.DuplicatedUserException;
import pt.uc.dei.aor.project.business.exception.NoRoleException;
import pt.uc.dei.aor.project.business.model.IModelFactory;
import pt.uc.dei.aor.project.business.model.IWorker;
import pt.uc.dei.aor.project.business.persistence.IWorkerPersistenceService;
import pt.uc.dei.aor.project.business.util.EmailUtil;
import pt.uc.dei.aor.project.business.util.Role;

@RunWith(MockitoJUnitRunner.class)
public class WorkerBusinessServiceTest {

	@Mock
	private EmailUtil emailUtil;
	
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
	public void shouldCreateAnUserCorrectly() throws NoRoleException, DuplicatedUserException {
		String login = "inexistentUser";
		String name = "name";
		String surname = "surname";
		String email = "email";
		String password = "password";
		List<Role> roles = new ArrayList<>();
    	roles.add(Role.ADMIN);
    	
		IWorker worker = factory.worker("other", email, password, name, surname, roles);
		when(factory.worker(eq(login), eq(email), Mockito.anyString(), eq(name), eq(surname), eq(roles)))
			.thenReturn(worker);
		
		ejb.createNewWorker(login, name, surname, email, roles);
		
		verify(factory).worker(eq(login), eq(email), Mockito.anyString(), eq(name), eq(surname), eq(roles));
		verify(workerEjb).save(worker);
	}
	
	@Test(expected=NoRoleException.class)
	public void shouldNotCreateAnUserWithoutRole() throws NoRoleException, DuplicatedUserException {
		String login = "inexistentUser";
		String name = "name";
		String surname = "surname";
		String email = "email";
		String password = "password";
		List<Role> roles = new ArrayList<>();
    	
		IWorker worker = factory.worker("other", email, password, name, surname, roles);
		when(factory.worker(login, email, password, name, surname, roles)).thenReturn(worker);
		
		ejb.createNewWorker(login, name, surname, email, roles);
	}
	
	@Test(expected=DuplicatedUserException.class)
	public void shouldNotCreateARepeatedAdminUser() throws NoRoleException, DuplicatedUserException {
		String login = "admin";
		String name = "name";
		String surname = "surname";
		String email = "email";
		String password = "password";
		List<Role> roles = new ArrayList<>(Arrays.asList(new Role[]{Role.ADMIN}));
    	
		IWorker worker = factory.worker("other", email, password, name, surname, roles);
		when(factory.worker(login, email, password, name, surname, roles)).thenReturn(worker);
		when(workerEjb.findWorkerByEmailOrLogin(email, login)).thenReturn(true);
		
		ejb.createNewWorker(login, name, surname, email, roles);
		
		verify(workerEjb).findWorkerByEmailOrLogin(email, login);
	}
	
	@Test
	public void shouldCreateAdminUser() throws NoRoleException, DuplicatedUserException {
		String login = "admin";
		String name = "name";
		String surname = "surname";
		String email = "email";
		String password = "password";
		List<Role> roles = new ArrayList<>(Arrays.asList(new Role[]{Role.ADMIN}));
    	
		IWorker worker = factory.worker("other", email, password, name, surname, roles);
		when(factory.worker(login, email, password, name, surname, roles)).thenReturn(worker);
		when(workerEjb.findWorkerByEmailOrLogin(email, login)).thenReturn(false);
		
		ejb.createNewWorker(login, name, surname, email, roles);
		
		verify(workerEjb).findWorkerByEmailOrLogin(email, login);
	}
	
	@Test
	public void shouldReturnNullWhenEjbException() throws NoRoleException, DuplicatedUserException {
		String login = "other";
		String name = "name";
		String surname = "surname";
		String email = "email";
		String password = "password";
		List<Role> roles = new ArrayList<>(Arrays.asList(new Role[]{Role.ADMIN}));
    	
		IWorker worker = factory.worker("other", email, password, name, surname, roles);
		when(factory.worker(login, email, password, name, surname, roles)).thenReturn(worker);
		when(workerEjb.save(worker)).thenThrow(new EJBException());
		
		assertThat(ejb.createNewWorker(login, name, surname, email, roles), is(equalTo(null)));
		
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
	
	@Test
	public void shouldCallCorrectMethodWhenCreateSU() {
		ejb.createSuperUser();
		verify(workerEjb).createSuperUser();
	}
	
	@Test
	public void shouldCallCorrectMethodWhenFindAllInterviewers() {
		ejb.findAllInterviewers();
		verify(workerEjb).findAllInterviewers();
	}
	
	@Test
	public void shouldCallCorrectMethodWhenFindAllManagers() {
		ejb.findAllManagers();
		verify(workerEjb).findAllManagers();
	}
	
	@Test
	public void shouldCallCorrectMethodWhenFindUserByEmail() {
		String email = "email";
		
		ejb.getWorkerByEmail(email);
		verify(workerEjb).getWorkerByEmail(email);
	}
}
