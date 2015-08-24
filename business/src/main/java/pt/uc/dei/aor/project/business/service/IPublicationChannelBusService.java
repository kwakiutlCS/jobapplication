package pt.uc.dei.aor.project.business.service;

import java.util.List;

import pt.uc.dei.aor.project.business.model.IPublicationChannel;

public interface IPublicationChannelBusService {
	
	IPublicationChannel createNewPublicationChannel(String channel);
	
	List<IPublicationChannel> getIPublicationChannels();
	

	
	 
	

}
