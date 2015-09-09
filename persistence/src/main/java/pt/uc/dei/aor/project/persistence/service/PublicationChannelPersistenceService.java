package pt.uc.dei.aor.project.persistence.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import pt.uc.dei.aor.project.business.model.IPublicationChannel;
import pt.uc.dei.aor.project.business.persistence.IPublicationChannelPersistenceService;
import pt.uc.dei.aor.project.persistence.entity.PublicationChannelEntity;
import pt.uc.dei.aor.project.persistence.proxy.PublicationChannelProxy;

@Stateless
public class PublicationChannelPersistenceService implements IPublicationChannelPersistenceService {

	
	@PersistenceContext(unitName = "jobs")
    private EntityManager em;

	@Override
	public IPublicationChannel save(
			IPublicationChannel publicationChannel) {
		
		PublicationChannelEntity pcEntity = GenericPersistenceService.getEntity(publicationChannel);
		pcEntity = em.merge(pcEntity);
		
		return new PublicationChannelProxy(pcEntity);
	}
	
	
	@Override
	public List<IPublicationChannel> findAllPublicationChannels() {
		
		TypedQuery<PublicationChannelEntity> q =  em.createNamedQuery("publicationChannel.findAll", PublicationChannelEntity.class);
		
		List<PublicationChannelEntity> channels = q.getResultList();
		
		List<IPublicationChannel> ichannels = new ArrayList<IPublicationChannel>();
		
		for(PublicationChannelEntity pce : channels){
			ichannels.add(new PublicationChannelProxy(pce));
			
		}
		
		return ichannels;
	}


	@Override
	public void delete(IPublicationChannel publicationChannel) {
	
		PublicationChannelEntity entity = GenericPersistenceService.getEntity(publicationChannel);
		em.remove(em.merge(entity));
	}

}
