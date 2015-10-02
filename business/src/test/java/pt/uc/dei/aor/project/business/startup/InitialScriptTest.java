package pt.uc.dei.aor.project.business.startup;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import pt.uc.dei.aor.project.business.exception.DuplicatedUserException;
import pt.uc.dei.aor.project.business.exception.NoRoleException;
import pt.uc.dei.aor.project.business.service.IColorBusinessService;
import pt.uc.dei.aor.project.business.service.IPublicationChannelBusService;
import pt.uc.dei.aor.project.business.service.IQualificationBusinessService;
import pt.uc.dei.aor.project.business.service.IUserBusinessService;
import pt.uc.dei.aor.project.business.util.Role;


@RunWith(MockitoJUnitRunner.class)
public class InitialScriptTest {

	@Mock
	private IUserBusinessService user;

	@Mock
	private IColorBusinessService color;

	@Mock
	private IQualificationBusinessService qualification;

	@Mock
	private IPublicationChannelBusService channel;


	@InjectMocks
	private InitialScript initScript;

	private Path path;
	
	@Before
	public void init() {
		path = Mockito.mock(Path.class);
		System.setProperty("jboss.server.data.dir","fake_path");
	}
	



	@Test
	public void shouldAddAdmin() throws NoRoleException, DuplicatedUserException, IOException {
		List<Role> roles = new ArrayList<>();
		roles.add(Role.ADMIN);
		
		initScript.initialize();
		
		Mockito.verify(user).createNewUser("admin", "admin", "admin", "admin@admin.pt", roles);
	}
}
