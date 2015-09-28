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
public class EmailValidatorTest {
	
	@Mock
	private IUserBusinessService ejb;
	
	@InjectMocks
	private EmailValidator validator;
	
	@Test(expected=ValidatorException.class)
	public void incorrect_format_email_should_throw_exception() {
		validator.validate(null, null, "aa.pt");
	}
	
	@Test
	public void correct_format_email_should_not_throw_exception() {
		validator.validate(null, null, "a@a.pt");
		verify(ejb).getUserByEmail("a@a.pt");
	}
	
	@Test
	public void should_delegate_to_required_empty_email() {
		validator.validate(null, null, null);
		verify(ejb, never()).getUserByEmail(null);
	}
	
	@Test(expected=ValidatorException.class)
	public void shouldThrowExceptionIfEmailAlreadyRegistered() {
		String email = "email@email.com";
		IUser worker = Mockito.mock(IUser.class);
		
		Mockito.when(ejb.getUserByEmail(email)).thenReturn(worker);
		
		validator.validate(null, null, email);
	}
}