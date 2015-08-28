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
import pt.uc.dei.aor.project.persistence.proxy.IProxyToEntity;
import pt.uc.dei.aor.project.persistence.proxy.PublicationChannelProxy;

@Stateless
public class PublicationChannelPersistenceService implements IPublicationChannelPersistenceService {

	
	@PersistenceContext(unitName = "jobs")
    private EntityManager em;

	@Override
	public IPublicationChannel save(
			IPublicationChannel publicationChannel) {
		
		PublicationChannelEntity pcEntity = getEntity(publicationChannel);
		pcEntity = em.merge(pcEntity);
		
		return new PublicationChannelProxy(pcEntity);
	}
	
	
	@SuppressWarnings("unchecked")
	private PublicationChannelEntity getEntity(IPublicationChannel pcProxy) {
        if (pcProxy instanceof IProxyToEntity<?>) {
            return ((IProxyToEntity<PublicationChannelEntity>) pcProxy).getEntity();
        }

        throw new IllegalStateException();
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
	public List<IPublicationChannel> getIPublicationChannel(String channel) {
		
		TypedQuery<PublicationChannelEntity> q = em.createNamedQuery("publicationChannel.findByString", PublicationChannelEntity.class);
		
		q.setParameter("i", channel);
		
		List<PublicationChannelEntity> channels = q.getResultList();
		
		List<IPublicationChannel> ichannels = new ArrayList<IPublicationChannel>();
		
		for(PublicationChannelEntity pce : channels){
			ichannels.add(new PublicationChannelProxy(pce));
		}
				
		return ichannels;
	}

}
