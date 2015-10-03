package pt.uc.dei.aor.project.business.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import pt.uc.dei.aor.project.business.model.IPosition;
import pt.uc.dei.aor.project.business.model.IUser;
import pt.uc.dei.aor.project.business.persistence.IPositionPersistenceService;
import pt.uc.dei.aor.project.business.service.INotificationBusinessService;
import pt.uc.dei.aor.project.business.startup.ScheduledEjb;

@RunWith(MockitoJUnitRunner.class)
public class ScheduledTest {

	@Mock
	private INotificationBusinessService notificationService;
	  	

	 @Mock
	 private EmailUtil emailUtil;
	 
	 @Mock
	 private IPositionPersistenceService positionPersistence;
	 
	 @InjectMocks
	 private ScheduledEjb schedule;
	 
	 @Test
	 public void init() {
		 IPosition position = Mockito.mock(IPosition.class);
		 List<IPosition> positions = new ArrayList<>();
		 positions.add(position);
		 IUser user = Mockito.mock(IUser.class);
		 
		 Mockito.when(positionPersistence.findOpenPositions()).thenReturn(positions);
		 Mockito.when(position.getSla()).thenReturn(1);
		 Mockito.when(position.getContactPerson()).thenReturn(user);
		 Mockito.when(user.getEmail()).thenReturn("skdfj");
		 Calendar cal = Calendar.getInstance();
		 cal.add(Calendar.DAY_OF_MONTH, -1);
		 Mockito.when(position.getOpeningDate()).thenReturn(cal.getTime());
		 
		 schedule.run();
		 
	 }
}
