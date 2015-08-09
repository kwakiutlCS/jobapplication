package pt.uc.dei.aor.project.presentation.bean;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Mockito.*;

import pt.uc.dei.aor.project.business.service.IWorkerBusinessService;
import pt.uc.dei.aor.project.presentation.security.Encryptor;

@RunWith(MockitoJUnitRunner.class)
public class RegisterBeanTest {
	
	@Mock
	IWorkerBusinessService ejb;
	
	@InjectMocks
	RegisterBean bean;

	@Test
	public void registerFunctionShouldCallEjbMethod() {
		String login = "login";
		String name = "name";
		String surname = "surname";
		String email = "email";
		String password = "password";
		
		bean.setEmail(email);
		bean.setLogin(login);
		bean.setName(name);
		bean.setSurname(surname);
		bean.setPassword(password);
		bean.register();
		
		verify(ejb).createNewWorker(login, name, surname, email, Encryptor.encrypt(password));
	}

}