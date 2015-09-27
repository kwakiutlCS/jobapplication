package pt.uc.dei.aor.project.presentation.bean;

import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import pt.uc.dei.aor.project.business.exception.DuplicatedUserException;
import pt.uc.dei.aor.project.business.exception.NoRoleException;
import pt.uc.dei.aor.project.business.service.IUserBusinessService;
import pt.uc.dei.aor.project.business.util.Role;

@RunWith(MockitoJUnitRunner.class)
public class WorkerBeanTest {
	
	@Mock
	IUserBusinessService ejb;
	
	@InjectMocks
	WorkerBean bean;

	@Test
	public void registerFunctionShouldCallEjbMethod() throws NoRoleException, DuplicatedUserException {
		String login = "login";
		String name = "name";
		String surname = "surname";
		String email = "email";
		List<Role> roles = new ArrayList<>();
		roles.add(Role.ADMIN);
		
		bean.setEmail(email);
		bean.setLogin(login);
		bean.setName(name);
		bean.setSurname(surname);
		bean.setRoles(roles);
		bean.register();
		
		verify(ejb).createNewUser(login, name, surname, email, roles);
	}

}