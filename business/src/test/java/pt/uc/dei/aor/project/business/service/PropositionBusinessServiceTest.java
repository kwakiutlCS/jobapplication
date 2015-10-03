package pt.uc.dei.aor.project.business.service;

import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import pt.uc.dei.aor.project.business.model.IApplication;
import pt.uc.dei.aor.project.business.model.IModelFactory;
import pt.uc.dei.aor.project.business.model.IPosition;
import pt.uc.dei.aor.project.business.model.IProposition;
import pt.uc.dei.aor.project.business.persistence.IApplicationPersistenceService;
import pt.uc.dei.aor.project.business.persistence.IPositionPersistenceService;
import pt.uc.dei.aor.project.business.persistence.IPropositionPersistenceService;

@RunWith (MockitoJUnitRunner.class)
public class PropositionBusinessServiceTest {
	
	@Mock
	private IModelFactory factory;
	
	@Mock
	private IPropositionPersistenceService persistence;
	
	@Mock
	private IApplicationPersistenceService persistenceApp;
	
	@Mock
	private IPositionPersistenceService persistencePos;
	
	
	@Mock
	private IPosition position;
	
	@Mock
	private IProposition proposition;
	
	@InjectMocks
	private PropositionBusinessService ejb;
	
	


	@Test
	public void shouldSendProposition(){
		IApplication application = Mockito.mock(IApplication.class);
		Mockito.when(application.getPosition()).thenReturn(position);
		Mockito.when(position.getVacancies()).thenReturn(2);
		List<IApplication> applications = new ArrayList<IApplication>();
		Mockito.when(factory.proposition()).thenReturn(proposition);
		applications.add(application);
		applications.add(application);
		applications.add(application);
		IApplication otherApp = Mockito.mock(IApplication.class);
		Mockito.when(otherApp.isProposed()).thenReturn(false);
			
		Mockito.when(persistenceApp.findAllApplicationsByPosition(position)).thenReturn(applications);
		
		Mockito.when(application.isProposed()).thenReturn(true);
		
		ejb.sendProposition(application);
		
		verify(factory).proposition();
		
		
		verify(application).sendProposition(proposition);

		//verify(persistenceApp).save(application);
		
		
		
		
		//verify(position).setState(PositionState.CLOSED);
		
		//verify(persistencePos).save(position);
		
		
	}

	
}
