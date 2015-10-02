package pt.uc.dei.aor.project.business.service;

import static org.mockito.Mockito.verify;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import pt.uc.dei.aor.project.business.model.IModelFactory;
import pt.uc.dei.aor.project.business.model.IPosition;
import pt.uc.dei.aor.project.business.model.IPublicationChannel;
import pt.uc.dei.aor.project.business.model.IScript;
import pt.uc.dei.aor.project.business.model.IUser;
import pt.uc.dei.aor.project.business.persistence.IPositionPersistenceService;
import pt.uc.dei.aor.project.business.util.EmailUtil;
import pt.uc.dei.aor.project.business.util.Localization;
import pt.uc.dei.aor.project.business.util.PositionState;
import pt.uc.dei.aor.project.business.util.TechnicalArea;


@RunWith (MockitoJUnitRunner.class)
public class PositionBusinessServiceTest {

	@Mock
	private IModelFactory factory;

	@Mock
	private IPositionPersistenceService persistence;

	@Mock
	private IPosition position;

	@Mock
	private IUser manager; 

	@Mock
	private INotificationBusinessService notification;
	
	@Mock
	private EmailUtil emailUtil;

	@InjectMocks
	private PositionBusinessService ejb;


	@Test
	public void shouldCreatePositionWithPresentDate() throws ParseException{

		String title = "titleTest";
		int vacancies = 5;
		int sla = 15;
		String company = "Our Company Test";
		String description = "testing";
		List<Localization> locations = new ArrayList<Localization>();
		List<IScript> scripts = new ArrayList<IScript>();
		List<TechnicalArea> areas = new ArrayList<TechnicalArea>();
		List<IPublicationChannel> channels = new ArrayList<IPublicationChannel>();
		PositionState state = PositionState.OPEN;
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
		String dateInString = "22-08-2015";
		Date date = sdf.parse(dateInString);
		Date closingDate = date;


		Mockito.when(manager.getEmail()).thenReturn("test@mail");
		Mockito.when(persistence.findBiggestCode()).thenReturn(2L);
		
		

		IPosition pos = ejb.createNewPosition(title, locations, state, vacancies, closingDate, sla, manager, company, areas, description, scripts, channels);

		verify(notification).notify(manager,  "Position: "+title+" was created with you as manager","Position opening");

		verify(emailUtil).send("test@mail", "Position opening", "Position: "+title+" was created with you as manager");
		
		verify(persistence).save(pos);
		
	}
}



