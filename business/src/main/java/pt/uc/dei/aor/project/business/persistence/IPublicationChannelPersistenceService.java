package pt.uc.dei.aor.project.business.persistence;

import java.util.List;

import pt.uc.dei.aor.project.business.model.IPublicationChannel;

public interface IPublicationChannelPersistenceService {
	
	IPublicationChannel save(IPublicationChannel publicationChannel);
	
	List<IPublicationChannel> findAllPublicationChannels();
	
	void delete(IPublicationChannel publicationChannel);
	
	
	
	
	

}
