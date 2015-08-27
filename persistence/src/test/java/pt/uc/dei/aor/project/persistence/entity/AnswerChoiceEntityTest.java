package pt.uc.dei.aor.project.persistence.entity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import java.util.TreeSet;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import pt.uc.dei.aor.project.business.util.QuestionType;

public class AnswerChoiceEntityTest {

	@Test
	public void shouldReturnEqualForEqualTextAnswer() {
		AnswerChoiceEntity ac1 = new AnswerChoiceEntity("text");
		AnswerChoiceEntity ac2 = new AnswerChoiceEntity("text");
		
		assertThat(ac1, is(equalTo(ac2)));
	}
	
	@Test
	public void shouldReturnSameHashForDiferentTextAnswer() {
		AnswerChoiceEntity ac1 = new AnswerChoiceEntity("text");
		AnswerChoiceEntity ac2 = new AnswerChoiceEntity("text");
		
		assertThat(ac1.hashCode(), is(equalTo(ac2.hashCode())));
	}
	
	@Test
	public void shouldNotReturnEqualForEqualTextAnswer() {
		AnswerChoiceEntity ac1 = new AnswerChoiceEntity("text");
		AnswerChoiceEntity ac2 = new AnswerChoiceEntity("Text");
		
		assertThat(ac1, is(not(equalTo(ac2))));
	}
	
	@Test
	public void shouldNotReturnSameHashForDiferentTextAnswer() {
		AnswerChoiceEntity ac1 = new AnswerChoiceEntity("text");
		AnswerChoiceEntity ac2 = new AnswerChoiceEntity("Text");
		
		assertThat(ac1.hashCode(), is(not(equalTo(ac2.hashCode()))));
	}
	
	@Test
	public void shouldNotReturnEqualForNullTextAnswer() {
		AnswerChoiceEntity ac1 = new AnswerChoiceEntity("text");
		AnswerChoiceEntity ac2 = new AnswerChoiceEntity(null);
		
		assertThat(ac1, is(not(equalTo(ac2))));
	}
	
	@Test
	public void shouldNotReturnSameHashForNullTextAnswer() {
		AnswerChoiceEntity ac1 = new AnswerChoiceEntity("text");
		AnswerChoiceEntity ac2 = new AnswerChoiceEntity(null);
		
		assertThat(ac1.hashCode(), is(not(equalTo(ac2.hashCode()))));
	}
	
	@Test
	public void shouldCompareCorrectlyForEqualTextAnswer() {
		AnswerChoiceEntity ac1 = new AnswerChoiceEntity("text");
		AnswerChoiceEntity ac2 = new AnswerChoiceEntity("text");
		
		assertThat(ac1.compareTo(ac2), is(equalTo(0)));
	}
}
