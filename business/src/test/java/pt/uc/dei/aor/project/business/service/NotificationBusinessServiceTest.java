package pt.uc.dei.aor.project.business.service;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import pt.uc.dei.aor.project.business.model.IModelFactory;
import pt.uc.dei.aor.project.business.model.INotification;
import pt.uc.dei.aor.project.business.persistence.INotificationPersistenceService;


@RunWith (MockitoJUnitRunner.class)
public class NotificationBusinessServiceTest {
	
	
	@Mock
	private IModelFactory factory;
	
	@Mock
	private INotificationPersistenceService persistence;
	
	@Mock
	private INotification notification;
	
	@InjectMocks
	private NotificationBusinessService ejb;
	
//	@Override
//	public INotification markNotificationAsViewed(INotification notification) {
//		notification.markAsViewed();
//		return notificationPersistence.save(notification);
//	}
	

	@Test
	public void shouldSaveMarkedNotificationAsViewed(){
		
		ejb.markNotificationAsViewed(notification);
		
		verify(notification).markAsViewed();
	
		
	}
	
	
	
}
