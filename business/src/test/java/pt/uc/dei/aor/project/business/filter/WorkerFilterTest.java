package pt.uc.dei.aor.project.business.filter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import pt.uc.dei.aor.project.business.exception.IllegalFilterParamException;
import pt.uc.dei.aor.project.business.util.Role;

@RunWith(MockitoJUnitRunner.class)
public class WorkerFilterTest {
	
	private WorkerFilter filter;
	
	@Before
	public void init() {
		filter = new WorkerFilter();
		
	}
	
	
	@Test
	public void addKeyword() {
		filter.setKeyword("key");
		assertThat(filter.getKeyword(), is(equalTo("key")));
	}
	
	@Test
	public void addRole() throws IllegalFilterParamException {
		filter.addRole(Role.ADMIN);
		filter.addRole(Role.MANAGER);
		
		assertThat(filter.getRoleSets().size(), is(equalTo(2)));
		filter.mergeRoles(0);
		filter.splitRoles(0, 0);
		
		filter.removeRole(0, 0);
		assertThat(filter.getRoleSets().size(), is(equalTo(1)));
	}
}
