package pt.uc.dei.aor.project.business.startup;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import pt.uc.dei.aor.project.business.exception.NoRoleException;
import pt.uc.dei.aor.project.business.service.IWorkerBusinessService;
import pt.uc.dei.aor.project.business.util.Role;

@RunWith(MockitoJUnitRunner.class)
public class StartUpEJBTest {

	@Mock
	private IWorkerBusinessService workerEjb;
	
	@InjectMocks
	private StartUpEjb startUp;
	
	
	@Test
	public void shouldCreateAdminIfDoesntExists() throws NoRoleException {
		List<Role> roles = new ArrayList<>();
		roles.add(Role.ADMIN);
		
		String login = "admin";
		String name = "admin";
		String surname = "admin";
		String email = "admin@admin";
		String password = Encryptor.encrypt("admin");
		
		startUp.init(); 
		
		Mockito.verify(workerEjb).createNewWorker(login, name, surname, email, password, roles);
	}
}