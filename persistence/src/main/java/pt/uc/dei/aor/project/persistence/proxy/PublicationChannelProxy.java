package pt.uc.dei.aor.project.persistence.proxy;

import pt.uc.dei.aor.project.business.model.IPublicationChannel;
import pt.uc.dei.aor.project.persistence.entity.PublicationChannelEntity;


public class PublicationChannelProxy implements IPublicationChannel,IProxyToEntity<PublicationChannelEntity> {
	
	
	private PublicationChannelEntity pcEntity;

	
	public PublicationChannelProxy(PublicationChannelEntity pcEntity) {
		this.setEntity(pcEntity != null ? pcEntity : new PublicationChannelEntity());
	}
	
	public PublicationChannelProxy(String channel){
		pcEntity = new PublicationChannelEntity(channel);
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

	
	

	
}
