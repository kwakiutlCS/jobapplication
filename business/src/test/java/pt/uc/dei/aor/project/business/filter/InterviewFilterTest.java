package pt.uc.dei.aor.project.business.filter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import pt.uc.dei.aor.project.business.exception.IllegalFilterParamException;
import pt.uc.dei.aor.project.business.model.IUser;

@RunWith(MockitoJUnitRunner.class)
public class InterviewFilterTest {
	
	private InterviewFilter filter;
	
	@Before
	public void init() {
		filter = new InterviewFilter();
		
		IUser i1 = mock(IUser.class);
		IUser i2 = mock(IUser.class);
		
		filter.addInterviewerSet(i1);
		filter.addInterviewerSet(i2);
	}
	
	
	@Test
	public void shouldAddInterviewerCorrectly() {
		int size = filter.getInterviewerSets().size();
		
		IUser i1 = mock(IUser.class);
		IUser i2 = mock(IUser.class);
		
		filter.addInterviewerSet(i1);
		
		assertThat(filter.getInterviewerSets().size(), is(equalTo(size+1)));
		
		filter.addInterviewerSet(i2);
		
		assertThat(filter.getInterviewerSets().size(), is(equalTo(size+2)));
	}
	
	@Test
	public void shouldDeleteInterviewerCorrectly() throws IllegalFilterParamException {
		int size = filter.getInterviewerSets().size();
		
		IUser i1 = mock(IUser.class);
		filter.addInterviewerSet(i1);
		filter.mergeInterviewers(1);
		
		int setSize = filter.getInterviewerSets().get(1).size();
		filter.deleteInterviewer(1, 0);
		
		assertThat(filter.getInterviewerSets().size(), is(equalTo(size)));
		assertThat(filter.getInterviewerSets().get(1).size(), is(equalTo(setSize-1)));
		
		filter.deleteInterviewer(0, 0);
		
		assertThat(filter.getInterviewerSets().size(), is(equalTo(size-1)));
		assertThat(filter.getInterviewerSets().get(0).size(), is(equalTo(setSize-1)));
	}
	
	@Test(expected=IllegalFilterParamException.class)
	public void shouldnotDeleteInterviewerWithWrongSetIndex() throws IllegalFilterParamException {
		filter.deleteInterviewer(2, 0);
	}
	
	@Test(expected=IllegalFilterParamException.class)
	public void shouldnotDeleteInterviewerWithWrongPos() throws IllegalFilterParamException {
		filter.deleteInterviewer(1, 1);
	}
	
	@Test
	public void shouldDeleteInterviewerCorrectly2() throws IllegalFilterParamException {
		int size = filter.getInterviewerSets().size();
		
		filter.deleteInterviewer(1, 0);
		
		assertThat(filter.getInterviewerSets().size(), is(equalTo(size-1)));
		
		filter.deleteInterviewer(0, 0);
		
		assertThat(filter.getInterviewerSets().size(), is(equalTo(size-2)));
	}
	
	@Test
	public void shouldMergeInterviewerCorrectlyNoRepeated() throws IllegalFilterParamException {
		IUser i1 = mock(IUser.class);
		filter.addInterviewerSet(i1);
		
		int size = filter.getInterviewerSets().size();
		int counter = 0;
		for (List<IUser> workers : filter.getInterviewerSets()) {
			counter += workers.size();
		}
		
		filter.mergeInterviewers(1);
		filter.mergeInterviewers(0);
		
		assertThat(filter.getInterviewerSets().size(), is(equalTo(size-2)));
		
		assertThat(filter.getInterviewerSets().get(0).size(), is(equalTo(counter)));
	}
	
	@Test
	public void shouldMergeInterviewerCorrectlyRepeated() throws IllegalFilterParamException {
		IUser i1 = mock(IUser.class);
		
		filter.addInterviewerSet(i1);
		filter.mergeInterviewers(1);
		filter.addInterviewerSet(i1); // same interviewer
		
		int size = filter.getInterviewerSets().size();
		int counter = 0;
		for (List<IUser> workers : filter.getInterviewerSets()) {
			counter += workers.size();
		}
		
		filter.mergeInterviewers(0);
		filter.mergeInterviewers(0);
		
		assertThat(filter.getInterviewerSets().size(), is(equalTo(size-2)));
		
		assertThat(filter.getInterviewerSets().get(0).size(), is(equalTo(counter-1)));
	}
	
	@Test(expected=IllegalFilterParamException.class)
	public void shouldNotMergeInterviewerWithWrongIndex() throws IllegalFilterParamException {
		filter.mergeInterviewers(1);
	}
	
	@Test
	public void shouldSplitInterviewerCorrectly() throws IllegalFilterParamException {
		IUser i1 = mock(IUser.class);
		filter.addInterviewerSet(i1);
		
		filter.mergeInterviewers(1);
		filter.mergeInterviewers(0);
		
		filter.splitInterviewers(0, 0);
		
		assertThat(filter.getInterviewerSets().size(), is(equalTo(2)));
		assertThat(filter.getInterviewerSets().get(1).size(), is(equalTo(2)));
		
		filter.splitInterviewers(1, 0);
		
		assertThat(filter.getInterviewerSets().size(), is(equalTo(3)));
		assertThat(filter.getInterviewerSets().get(1).size(), is(equalTo(1)));
	}
	
	@Test(expected=IllegalFilterParamException.class)
	public void shouldNotSplitInterviewerWithWrongIndex() throws IllegalFilterParamException {
		filter.mergeInterviewers(0);
		
		filter.splitInterviewers(1, 0);
	}
	
	@Test(expected=IllegalFilterParamException.class)
	public void shouldNotSplitInterviewerWithWrongPos() throws IllegalFilterParamException {
		filter.mergeInterviewers(0);
		
		filter.splitInterviewers(0, 1);
	}
	
	@Test
	public void shouldMergeAndSplitCorrectly() throws IllegalFilterParamException {
		IUser w = mock(IUser.class);
		filter.addInterviewerSet(w);
		
		filter.mergeInterviewers(0);
		filter.splitInterviewers(0, 0);
		
		assertThat(filter.getInterviewerSets().get(2).get(0), is(equalTo(w)));
	}
	
	@Test
	public void addPositionShouldAddCorrectly() {
		filter.addPosition("title");
		assertThat(filter.getPositions().size(), is(equalTo(1)));
		
		filter.addPosition("title2");
		assertThat(filter.getPositions().size(), is(equalTo(2)));
		
		filter.addPosition("title");
		assertThat(filter.getPositions().size(), is(equalTo(2)));
		
	}
}
