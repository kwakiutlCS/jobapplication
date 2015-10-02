package pt.uc.dei.aor.project.business.service;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import pt.uc.dei.aor.project.business.model.IColor;
import pt.uc.dei.aor.project.business.model.IModelFactory;
import pt.uc.dei.aor.project.business.persistence.IColorPersistenceService;

@RunWith (MockitoJUnitRunner.class)
public class ColorBusinessServiceTest {
	
	
	
	@Mock
	private IModelFactory factory;
	
	@Mock
	private IColorPersistenceService persistence;
	
	@Mock
	private IColor icolor;
	
	
	@InjectMocks
	private ColorBusinessService ejb;

	

	@Test
	public void shouldCallCorrectMethodWhenSavingColor(){
		
		ejb.save(icolor);
		verify(persistence).save(icolor);
	}
	
	@Test
	public void shouldCallCorrectMethodWhenFindingColorByTitle(){
		
		String test = "test";
		ejb.findColorByTitle(test);
		verify(persistence).findByTitle(test);
	}
	
	
	
}
