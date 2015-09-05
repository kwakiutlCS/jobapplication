package pt.uc.dei.aor.project.presentation.validator;

import javax.faces.validator.ValidatorException;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TimeValidatorTest {
	
	private TimeValidator validator = new TimeValidator();
	
	
	@Test(expected=ValidatorException.class)
	public void shouldNotAllowIncorrectMinuteValues() {
		validator.validate(null, null, "14h60");
	}
	
	@Test(expected=ValidatorException.class)
	public void shouldNotAllowIncorrectHourValues() {
		validator.validate(null, null, "24h00");
	}
	
	@Test(expected=ValidatorException.class)
	public void shouldNotAllowIncorrectFormattedValues() {
		validator.validate(null, null, "2400");
	}
	
	@Test(expected=ValidatorException.class)
	public void shouldNotAllowIncorrectFormattedValues2() {
		validator.validate(null, null, "24h456");
	}
	
	@Test(expected=ValidatorException.class)
	public void shouldNotAllowIncorrectFormattedValues3() {
		validator.validate(null, null, "a5h45");
	}
	
	@Test(expected=ValidatorException.class)
	public void shouldNotAllowIncorrectFormattedValues4() {
		validator.validate(null, null, "15h-5");
	}
	
}