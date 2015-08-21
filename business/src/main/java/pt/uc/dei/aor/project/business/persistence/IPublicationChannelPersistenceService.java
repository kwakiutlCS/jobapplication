package pt.uc.dei.aor.project.business.persistence;

import pt.uc.dei.aor.project.business.model.IPublicationChanhel;

public interface IPublicationChannelPersistenceService {
	
	IPublicationChanhel save(IPublicationChanhel publicationChannel);
	
	//IPublicationChanhel findAllPublicationChannels();

}
