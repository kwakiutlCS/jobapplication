package pt.uc.dei.aor.project.persistence.proxy;

import pt.uc.dei.aor.project.business.model.IPublicationChannel;
import pt.uc.dei.aor.project.persistence.entity.PublicationChannelEntity;
import pt.uc.dei.aor.project.persistence.service.GenericPersistenceService;


public class PublicationChannelProxy implements IPublicationChannel,IProxyToEntity<PublicationChannelEntity> {
	
	
	private PublicationChannelEntity pcEntity;

	
	public PublicationChannelProxy(PublicationChannelEntity pcEntity) {
		this.setEntity(pcEntity != null ? pcEntity : new PublicationChannelEntity());
	}
	
	public PublicationChannelProxy(String channel){
		pcEntity = new PublicationChannelEntity(channel);
	}
	
	public PublicationChannelProxy() {
		pcEntity = new PublicationChannelEntity();
	}

	@Override
	public PublicationChannelEntity getEntity() {
		return pcEntity;
	}

	public void setEntity(PublicationChannelEntity pcEntity) {
		this.pcEntity = pcEntity;
	}

	@Override
	public String getChannel() {
		
		return pcEntity.getChannel();
	}

	@Override
	public String toString() {
		return pcEntity.toString();
	}

	@Override
	public int hashCode() {
		return pcEntity.hashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		return pcEntity.equals(GenericPersistenceService.getEntity(o));
	}

	@Override
	public int getPublicationChannelId() {
		return pcEntity.getId();
	}
}
