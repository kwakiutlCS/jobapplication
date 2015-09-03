package pt.uc.dei.aor.project.business.startup;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

public class EncryptorTest {

	@Test
	public void shouldEncryptPasswordCorrectly() {
		String password = "12345";
		String expected = "WZRHGrsBESr8wYFZ9sx0tPURuZgG2lmzyvWpwXPKz8U=";
		
		String crypt = Encryptor.encrypt(password);
		
		assertThat(crypt, is(equalTo(expected)));
	}
}
