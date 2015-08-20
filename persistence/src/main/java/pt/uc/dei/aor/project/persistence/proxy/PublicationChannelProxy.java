package pt.uc.dei.aor.project.persistence.proxy;

import pt.uc.dei.aor.project.business.model.IPublicationChanhel;
import pt.uc.dei.aor.project.persistence.entity.PublicationChannelEntity;


public class PublicationChannelProxy implements IPublicationChanhel,IProxyToEntity<PublicationChannelEntity> {
	
	
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

	
	

	
}
