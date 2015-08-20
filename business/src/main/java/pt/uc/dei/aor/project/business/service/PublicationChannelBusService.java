package pt.uc.dei.aor.project.business.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import pt.uc.dei.aor.project.business.model.IModelFactory;
import pt.uc.dei.aor.project.business.model.IPublicationChanhel;
import pt.uc.dei.aor.project.business.persistence.IPublicationChannelPersistenceService;



@Stateless
public class PublicationChannelBusService implements IPublicationChannelBusService{
	
	
	@Inject
	private IModelFactory pcfactory;
	
	@Inject
	private IPublicationChannelPersistenceService pcPersistenceService;
	
	

	@Override
	public IPublicationChanhel createNewPublicationChannel(String channel) {
		
		IPublicationChanhel publicationChannel = pcfactory.publicationChannel(channel);
		
		return pcPersistenceService.save(publicationChannel);
	}

}
