package pt.uc.dei.aor.project.business.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

public class DateModelTest {

	
	DataModel<String, Integer> model = new DataModel();
	
	@Test
	public void shouldCreateAnEmptyModel() {
		assertThat(model.getPoints().size(), is(equalTo(0)));
	}
	
	@Test
	public void shouldAddPointsToModel() {
		model.addPoint(new DataPoint<String, Integer>("1",1));
		assertThat(model.getPoints().size(), is(equalTo(1)));
	}
	
	@Test
	public void shouldAddPointsToModelCorrectly() {
		model.addPoint(new DataPoint<String, Integer>("1",1));
		
		assertThat(model.getPoints().get(0).getX(), is(equalTo("1")));
		assertThat(model.getPoints().get(0).getY(), is(equalTo(1)));
	}
}
