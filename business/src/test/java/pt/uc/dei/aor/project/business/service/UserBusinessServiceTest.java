package pt.uc.dei.aor.project.business.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
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
import pt.uc.dei.aor.project.business.model.IQualification;
import pt.uc.dei.aor.project.business.model.IUser;
import pt.uc.dei.aor.project.business.persistence.IUserPersistenceService;
import pt.uc.dei.aor.project.business.util.EmailUtil;
import pt.uc.dei.aor.project.business.util.PasswordUtil;
import pt.uc.dei.aor.project.business.util.Role;
import pt.uc.dei.aor.project.business.util.UploadUtil;

@RunWith(MockitoJUnitRunner.class)
public class UserBusinessServiceTest {

	@Mock
	private EmailUtil emailUtil;

	@Mock
	private IModelFactory factory;

	@Mock
	private IUserPersistenceService userEjb;

	@Mock
	private IUser IUser;

	@Mock
	private UploadUtil upload;
	
	@Mock
	private PasswordUtil passwordUtil;

	@InjectMocks
	UserBusinessService ejb;


	@Test
	public void shouldCallCorrectMethodWhenFindUserByLogin() {
		String login = "login";
		ejb.getUserByLogin(login);

		verify(userEjb).getUserByLogin(login);
	}

	@Test
	public void shouldCallCorrectMethodWhenDeletingUser() {
		ejb.deleteUser(IUser);

		verify(userEjb).delete(IUser);
	}

	@Test
	public void shouldCreateCandidate() throws IOException {

		String test = "test";
		String login= "loginTest";
		String cv= "cvTest";
		String provisoryCv = "provisoryCv";


		List<IQualification> qualifications = new ArrayList<IQualification>();
		
		IUser user  = Mockito.mock(IUser.class);

		Mockito.when(factory.user(login, test, test, test, test, test,test, test, test, test, qualifications, cv))
		.thenReturn(user);	

		ejb.createNewCandidate(login, test, test, test, test, test,test, test, test, test, qualifications,cv, provisoryCv);

		verify(upload).mv("cv/temp/"+provisoryCv, "cv/users/"+login, cv);


		verify(factory).user(login, test, test, test, test, test,test, test, test, test, qualifications, cv);

		
		verify(userEjb).save(user);
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

		IUser worker = factory.user("other", email, password, name, surname, roles);
		when(factory.user(eq(login), eq(email), Mockito.anyString(), eq(name), eq(surname), eq(roles)))
		.thenReturn(worker);

		ejb.createNewUser(login, name, surname, email, roles);

		verify(factory).user(eq(login), eq(email), Mockito.anyString(), eq(name), eq(surname), eq(roles));
		verify(userEjb).save(worker);
	}

	@Test(expected=NoRoleException.class)
	public void shouldNotCreateAnUserWithoutRole() throws NoRoleException, DuplicatedUserException {
		String login = "inexistentUser";
		String name = "name";
		String surname = "surname";
		String email = "email";
		String password = "password";
		List<Role> roles = new ArrayList<>();

		IUser worker = factory.user("other", email, password, name, surname, roles);
		when(factory.user(login, email, password, name, surname, roles)).thenReturn(worker);

		ejb.createNewUser(login, name, surname, email, roles);
	}

	@Test(expected=DuplicatedUserException.class)
	public void shouldNotCreateARepeatedAdminUser() throws NoRoleException, DuplicatedUserException {
		String login = "admin";
		String name = "name";
		String surname = "surname";
		String email = "email";
		String password = "password";
		List<Role> roles = new ArrayList<>(Arrays.asList(new Role[]{Role.ADMIN}));

		IUser worker = factory.user("other", email, password, name, surname, roles);
		when(factory.user(login, email, password, name, surname, roles)).thenReturn(worker);
		when(userEjb.findUserByEmailOrLogin(email, login)).thenReturn(true);

		ejb.createNewUser(login, name, surname, email, roles);

		verify(userEjb).findUserByEmailOrLogin(email, login);
	}

	@Test
	public void shouldCreateAdminUser() throws NoRoleException, DuplicatedUserException {
		String login = "admin";
		String name = "name";
		String surname = "surname";
		String email = "email";
		String password = "password";
		List<Role> roles = new ArrayList<>(Arrays.asList(new Role[]{Role.ADMIN}));

		IUser worker = factory.user("other", email, password, name, surname, roles);
		when(factory.user(login, email, password, name, surname, roles)).thenReturn(worker);
		when(userEjb.findUserByEmailOrLogin(email, login)).thenReturn(false);

		ejb.createNewUser(login, name, surname, email, roles);

		verify(userEjb).findUserByEmailOrLogin(email, login);
	}

	@Test
	public void shouldReturnNullWhenEjbException() throws NoRoleException, DuplicatedUserException {
		String login = "other";
		String name = "name";
		String surname = "surname";
		String email = "email";
		String password = "password";
		List<Role> roles = new ArrayList<>(Arrays.asList(new Role[]{Role.ADMIN}));

		IUser worker = factory.user("other", email, password, name, surname, roles);
		when(factory.user(login, email, password, name, surname, roles)).thenReturn(worker);
		when(userEjb.save(worker)).thenThrow(new EJBException());

		assertThat(ejb.createNewUser(login, name, surname, email, roles), is(equalTo(null)));

	}


	@Test
	public void shouldCallCorrectMethodWhenListingUsers() {
		ejb.findAllUsers();
		verify(userEjb).findAllUsers();
	}

	@Test
	public void shouldReturnListRoles() {
		List<Role> roles = ejb.getRoles();
		assertThat(roles.size(), is(equalTo(3)));
	}


	@Test
	public void shouldCallCorrectMethodWhenFindAllInterviewers() {
		ejb.findAllInterviewers();
		verify(userEjb).findAllInterviewers();
	}

	@Test
	public void shouldCallCorrectMethodWhenFindAllManagers() {
		ejb.findAllManagers();
		verify(userEjb).findAllManagers();
	}

	@Test
	public void shouldCallCorrectMethodWhenFindUserByEmail() {
		String email = "email";

		ejb.getUserByEmail(email);
		verify(userEjb).findUserByEmail(email);
	}
	
	
	@Test
	public void shouldRecoverPassword(){
		
		String email = "email";
		IUser user  = Mockito.mock(IUser.class);
				
		Mockito.when(userEjb.findUserByEmail(email)).thenReturn(user);
		ejb.recoverPassword(email);
		
		verify(user).setPassword(Mockito.anyString());
	}
	
	@Test
	public void shouldRecoverPasswordReturnNullWhenUserIsNull(){
		
		String email = "email";
		IUser user = null;
			
		Mockito.when(userEjb.findUserByEmail(email)).thenReturn(user);		
		ejb.recoverPassword(email);
		

		verify(userEjb,Mockito.never()).save(user);
		

	}
	
	@Test
	public void shouldUpdateUser(){
		
		IUser user  = Mockito.mock(IUser.class);
		
		ejb.update(user);
		verify(userEjb).save(user);
		
	}


	
	
}
