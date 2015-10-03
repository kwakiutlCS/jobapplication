package pt.uc.dei.aor.project.business.service;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import pt.uc.dei.aor.project.business.model.IApplication;
import pt.uc.dei.aor.project.business.model.IModelFactory;
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
	private INotificationBusinessService notification;
	
	
	@Test
	public void	findApplicationByIdTest(){
		
		ejb.findApplicationById(2L);
		verify(persistence).find(2L);		
	}
	
}
