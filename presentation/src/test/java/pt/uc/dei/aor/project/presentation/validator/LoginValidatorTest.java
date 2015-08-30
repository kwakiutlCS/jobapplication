package pt.uc.dei.aor.project.presentation.validator;

import static org.mockito.Mockito.verify;

import javax.faces.validator.ValidatorException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import pt.uc.dei.aor.project.business.model.IWorker;
import pt.uc.dei.aor.project.business.service.IWorkerBusinessService;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LoginValidatorTest {
	
	@Mock
	private IWorkerBusinessService ejb;
	
	@InjectMocks
	private LoginValidator validator;
	
	
	@Test
	public void correct_format_username_should_not_throw_exception() {
		String login = "username";
		validator.validate(null, null, login);
		verify(ejb).getWorkerByLogin(login);
	}
	
	@Test
	public void should_delegate_to_required_empty_username() {
		validator.validate(null, null, null);
		verify(ejb, never()).getWorkerByLogin(null);
	}
	
	@Test(expected=ValidatorException.class)
	public void shouldThrowExceptionIfLoginAlreadyRegistered() {
		String login = "login";
		IWorker worker = Mockito.mock(IWorker.class);
		
		Mockito.when(ejb.getWorkerByLogin(login)).thenReturn(worker);
		
		validator.validate(null, null, login);
	}
}