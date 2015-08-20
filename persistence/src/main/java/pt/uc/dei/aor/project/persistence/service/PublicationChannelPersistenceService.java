package pt.uc.dei.aor.project.persistence.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import pt.uc.dei.aor.project.business.model.IPublicationChanhel;
import pt.uc.dei.aor.project.business.persistence.IPublicationChannelPersistenceService;
import pt.uc.dei.aor.project.persistence.entity.PublicationChannelEntity;
import pt.uc.dei.aor.project.persistence.proxy.IProxyToEntity;
import pt.uc.dei.aor.project.persistence.proxy.PublicationChannelProxy;

@Stateless
public class PublicationChannelPersistenceService implements IPublicationChannelPersistenceService {

	
	@PersistenceContext(unitName = "jobs")
    private EntityManager em;

	@Override
	public IPublicationChanhel save(
			IPublicationChanhel publicationChannel) {
		
		PublicationChannelEntity pcEntity = getEntity(publicationChannel);
		
		pcEntity = em.merge(pcEntity);
		
		
		return new PublicationChannelProxy(pcEntity);
	}
	
	
	@SuppressWarnings("unchecked")
	private PublicationChannelEntity getEntity(IPublicationChanhel pcProxy) {
        if (pcProxy instanceof IProxyToEntity<?>) {
            return ((IProxyToEntity<PublicationChannelEntity>) pcProxy).getEntity();
        }

        throw new IllegalStateException();
    }


}
