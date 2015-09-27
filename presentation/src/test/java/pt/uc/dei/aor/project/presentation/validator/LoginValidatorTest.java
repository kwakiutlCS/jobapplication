package pt.uc.dei.aor.project.presentation.validator;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import javax.faces.validator.ValidatorException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import pt.uc.dei.aor.project.business.model.IUser;
import pt.uc.dei.aor.project.business.service.IUserBusinessService;

@RunWith(MockitoJUnitRunner.class)
public class LoginValidatorTest {
	
	@Mock
	private IUserBusinessService ejb;
	
	@InjectMocks
	private LoginValidator validator;
	
	
	@Test
	public void correct_format_username_should_not_throw_exception() {
		String login = "username";
		validator.validate(null, null, login);
		verify(ejb).getUserByLogin(login);
	}
	
	@Test
	public void should_delegate_to_required_empty_username() {
		validator.validate(null, null, null);
		verify(ejb, never()).getUserByLogin(null);
	}
	
	@Test(expected=ValidatorException.class)
	public void shouldThrowExceptionIfLoginAlreadyRegistered() {
		String login = "login";
		IUser worker = Mockito.mock(IUser.class);
		
		Mockito.when(ejb.getUserByLogin(login)).thenReturn(worker);
		
		validator.validate(null, null, login);
	}
}