package pt.uc.dei.aor.project.business.startup;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import pt.uc.dei.aor.project.business.exception.DuplicatedUserException;
import pt.uc.dei.aor.project.business.exception.NoRoleException;
import pt.uc.dei.aor.project.business.service.IQualificationBusinessService;
import pt.uc.dei.aor.project.business.service.IUserBusinessService;
import pt.uc.dei.aor.project.business.util.Role;

@RunWith(MockitoJUnitRunner.class)
public class StartUpEJBTest {

	@Mock
	private IUserBusinessService userEjb;
	
	@Mock
	private IQualificationBusinessService qualificationEjb;
	
	@InjectMocks
	private StartUpEjb startUp;
	
	@Ignore
	@Test
	public void shouldCreateAdminIfDoesntExists() throws NoRoleException, DuplicatedUserException {
		List<Role> roles = new ArrayList<>();
		roles.add(Role.ADMIN);
		
		String login = "admin";
		String name = "admin";
		String surname = "admin";
		String email = "admin@admin";
		
		startUp.init(); 
		
		Mockito.verify(userEjb).createNewUser(login, name, surname, email, roles);
	}
}
