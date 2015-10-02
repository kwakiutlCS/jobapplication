package pt.uc.dei.aor.project.business.startup;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import pt.uc.dei.aor.project.business.service.IColorBusinessService;
import pt.uc.dei.aor.project.business.service.IPublicationChannelBusService;
import pt.uc.dei.aor.project.business.service.IQualificationBusinessService;
import pt.uc.dei.aor.project.business.service.IUserBusinessService;


@RunWith(MockitoJUnitRunner.class)
public class InitialScriptTest {

	@Mock
	private IUserBusinessService user;

	@Mock
	private IColorBusinessService color;

	@Mock
	private IQualificationBusinessService qualification;

	@Mock
	private IPublicationChannelBusService channel;


	@InjectMocks
	private InitialScript initScript;

	
//	@Test
//	public void shouldLoadDataWhenQualificationIsNotPopulated() throws IOException{

//		Mockito.when(qualification.isPopulated()).thenReturn(false);
//				
////		Path path = (Path) Mockito.anyObject();
////				
////		Mockito.when(Files.exists(path)).thenReturn(true);
////		
////		BufferedReader reader = Files.newBufferedReader(path);
//
////		Mockito.when(((BufferedReader) Mockito.anyObject()).readLine()).thenReturn("something");
//		
//		initScript.initialize();
//		
//		verify(qualification).addQualification("something");
//		
//
//		
//
//	}


}
