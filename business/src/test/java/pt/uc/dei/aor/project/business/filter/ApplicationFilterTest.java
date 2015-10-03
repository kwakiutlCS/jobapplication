package pt.uc.dei.aor.project.business.filter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationFilterTest {
	
	private ApplicationFilter filter;
	
	@Before
	public void init() {
		filter = new ApplicationFilter();
		
	}
	
	
	@Test
	public void shouldAddstart() {
		Date d = new Date();
		
		filter.setStartDate(d);
		assertThat(filter.getStartDate().getDate(), is(equalTo(d.getDate())));
	
	}
	
	
}
