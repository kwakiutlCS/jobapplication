package pt.uc.dei.aor.project.business.service;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.Part;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import pt.uc.dei.aor.project.business.exception.IllegalApplicationException;
import pt.uc.dei.aor.project.business.model.IApplication;
import pt.uc.dei.aor.project.business.model.IModelFactory;
import pt.uc.dei.aor.project.business.model.IPosition;
import pt.uc.dei.aor.project.business.model.IUser;
import pt.uc.dei.aor.project.business.persistence.IApplicationPersistenceService;
import pt.uc.dei.aor.project.business.util.EmailUtil;
import pt.uc.dei.aor.project.business.util.UploadUtil;


@RunWith (MockitoJUnitRunner.class)
public class ApplicationBusinessServiceTest {

	@Mock
	private IModelFactory factory;

	@Mock
	private IApplicationPersistenceService persistence;

	@Mock
	private IApplication application;

	@InjectMocks
	private ApplicationBusinessService ejb;

	@Mock
	private UploadUtil upload;

	@Mock
	private EmailUtil emailUtil;

	@Mock
	private IUser user;

	@Mock
	private IPosition position;

	@Mock
	private Part anyPart;


	@Mock
	private INotificationBusinessService notification;


	@Test
	public void	findApplicationByIdTest(){

		ejb.findApplicationById(2L);
		verify(persistence).find(2L);		
	}


	@Test(expected=IllegalApplicationException.class)
	public void createApplicationShouldFailWhenCannotFindApplicationbyCandidateAndPosition() throws IOException, IllegalApplicationException {


		Mockito.when(persistence.findApplicationbyCandidateAndPosition(user, position)).thenReturn(true);

		ejb.createApplication("tmpLetter", anyPart, "tmpCv", anyPart, "sourceInfo", user,position);

		Date date= new Date();
		
		verify(factory, never()).application(anyPart.getSubmittedFileName(), 
				"cvName","sourceInfo", date, user, position);

	}

}
