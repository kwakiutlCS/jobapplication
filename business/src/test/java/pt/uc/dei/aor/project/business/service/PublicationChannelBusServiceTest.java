package pt.uc.dei.aor.project.business.service;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import pt.uc.dei.aor.project.business.model.IModelFactory;
import pt.uc.dei.aor.project.business.model.IPublicationChannel;
import pt.uc.dei.aor.project.business.persistence.IPublicationChannelPersistenceService;

@RunWith(MockitoJUnitRunner.class)
public class PublicationChannelBusServiceTest {
	
	@Mock
	private IModelFactory factory;
	
	@Mock
	private IPublicationChannelPersistenceService persistence;
	
	@Mock
	private IPublicationChannel channel;
	
	@InjectMocks
	private PublicationChannelBusService ejb;
	


	@Test
	public void shouldCallCorrectMethodWhenCreatingChannel()  {
		
		String test = "abc";
		
		Mockito.when(factory.publicationChannel(test)).thenReturn(channel);
		
		ejb.createNewPublicationChannel(test);
		
		verify(factory).publicationChannel(test);
		
		verify(persistence).save(channel); 
		
	}

	@Test
	public void shouldCallCorrectMethodWhenGettingAllChannels(){
		
		ejb.getIPublicationChannels();
		verify(persistence).findAllPublicationChannels();
	}
	
	
	@Test
	public void shouldDeleteChannel(){
		
	//	IPublicationChannel channel  = Mockito.mock(IPublicationChannel.class);
		
		ejb.deletePublicationChannel(channel);
		verify(persistence).delete(channel);
	}
		
}
