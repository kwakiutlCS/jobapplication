package pt.uc.dei.aor.project.business.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

public class QuestionTypeTest {

	
	@Test
	public void shouldReturnNullForInexistentType() {
		String text = "inexistentType";
		assertThat(QuestionType.toEnum(text), is(equalTo(null)));
	}
	
	@Test
	public void shouldReturnCorrectType() {
		String text = "Escolha múltipla";
		assertThat(QuestionType.toEnum(text), is(equalTo(QuestionType.MULTIPLE_CHOICE)));
	}
	
	
}
