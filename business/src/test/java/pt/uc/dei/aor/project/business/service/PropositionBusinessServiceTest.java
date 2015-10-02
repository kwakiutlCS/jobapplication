package pt.uc.dei.aor.project.business.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import pt.uc.dei.aor.project.business.model.IModelFactory;
import pt.uc.dei.aor.project.business.persistence.IPropositionPersistenceService;

@RunWith (MockitoJUnitRunner.class)
public class PropositionBusinessServiceTest {
	
	@Mock
	private IModelFactory factory;
	
	@Mock
	private IPropositionPersistenceService persistence;
	
	@InjectMocks
	private PropositionBusinessService ejb;

	@Test
	public void init() {
		
	}
}
