package pt.uc.dei.aor.project.business.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import pt.uc.dei.aor.project.business.model.IModelFactory;
import pt.uc.dei.aor.project.business.model.IPublicationChannel;
import pt.uc.dei.aor.project.business.persistence.IPublicationChannelPersistenceService;



@Stateless
public class PublicationChannelBusService implements IPublicationChannelBusService{


	@Inject
	private IModelFactory pcfactory;

	@Inject
	private IPublicationChannelPersistenceService pcPersistenceService;


	@Override
	public IPublicationChannel createNewPublicationChannel(String channel) {

		IPublicationChannel publicationChannel = pcfactory.publicationChannel(channel);
		return pcPersistenceService.save(publicationChannel);
	}


	@Override
	public List<IPublicationChannel> getIPublicationChannels() {
		return pcPersistenceService.findAllPublicationChannels();
	}


	@Override
	public void deletePublicationChannel(IPublicationChannel publicationChannel) {
		pcPersistenceService.delete(publicationChannel);
	}



}
