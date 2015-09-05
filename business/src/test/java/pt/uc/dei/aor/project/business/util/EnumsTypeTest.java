package pt.uc.dei.aor.project.business.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

public class EnumsTypeTest {

	
	@Test
	public void shouldReturnNullForInexistentTypeQuestion() {
		String text = "inexistentType";
		assertThat(QuestionType.toEnum(text), is(equalTo(null)));
	}
	
	@Test
	public void shouldReturnCorrectTypeQuestion() {
		String text = "Multiple choice";
		assertThat(QuestionType.toEnum(text), is(equalTo(QuestionType.MULTIPLE_CHOICE)));
	}
	
	@Test
	public void shouldReturnCorrectDescriptionQuestion() {
		QuestionType question = QuestionType.LONG_ANSWER;
		assertThat(question.getLabel(), is(equalTo("Long answer")));
		assertThat(question.toString(), is(equalTo("Long answer")));
	}
	
	@Test
	public void shouldReturnCorrectDescriptionLocalization() {
		Localization local = Localization.CLIENT;
		assertThat(local.getLocalizationLabel(), is(equalTo("Client")));
	}
	
	@Test
	public void shouldReturnCorrectDescriptionRole() {
		Role role = Role.ADMIN;
		assertThat(role.getLabel(), is(equalTo("Admin")));
	}
}
