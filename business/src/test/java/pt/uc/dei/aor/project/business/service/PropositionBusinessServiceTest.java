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
import pt.uc.dei.aor.project.business.util.PositionState;

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
	private IApplication application;
	
	@Mock
	private IPosition position;
	
	@Mock
	private IProposition proposition;
	
	@InjectMocks
	private PropositionBusinessService ejb;
	
	
//	@Override
//	public void sendProposition(IApplication application) {
//		IProposition proposition = factory.proposition();
//		application.sendProposition(proposition);
//		applicationPersistence.save(application);
//		
//		//application = applicationPersistence.sendProposition(application, proposition);
//		
//		IPosition position = application.getPosition();
//		List<IApplication> applications = applicationPersistence.findAllApplicationsByPosition(position);
//		
//		int counter = 0;
//		for (IApplication a : applications) {
//			if (a.isProposed()) counter++;
//		}
//		
//		if (counter >= position.getVacancies()) {
//			position.setState(PositionState.CLOSED);
//			positionPersistence.save(position);
//		}
//	}
	
	
	@Test
	public void shouldSendProposition(){
	
		ejb.sendProposition(application);
		
		verify(factory).proposition();
		
		proposition = factory.proposition();
		
		verify(application).sendProposition(proposition);

		verify(persistenceApp).save(application);
		
		Mockito.when(application.getPosition()).thenReturn(position);
		
		List<IApplication> applications = new ArrayList<IApplication>();
		
		applications.add(application);
			
		Mockito.when(persistenceApp.findAllApplicationsByPosition(position)).thenReturn(applications);
		
		Mockito.when(application.isProposed()).thenReturn(true);
		
		Mockito.when(position.getVacancies()).thenReturn(0);
		
		verify(position).setState(PositionState.CLOSED);
		
		verify(persistencePos).save(position);
		
		
	}

}
