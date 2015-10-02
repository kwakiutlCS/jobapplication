package pt.uc.dei.aor.project.business.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import pt.uc.dei.aor.project.business.model.IModelFactory;
import pt.uc.dei.aor.project.business.persistence.INotificationPersistenceService;


@RunWith (MockitoJUnitRunner.class)
public class NotificationBusinessServiceTest {
	
	
	@Mock
	private IModelFactory factory;
	
	@Mock
	private INotificationPersistenceService persistence;
	
	@InjectMocks
	private NotificationBusinessService ejb;
	

	@Test
	public void init() {
		
	}
}
