package pt.uc.dei.aor.project.presentation.bean;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import pt.uc.dei.aor.project.business.model.IPublicationChannel;
import pt.uc.dei.aor.project.business.service.IPublicationChannelBusService;



@Named
@SessionScoped
public class PublicationChannelBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private IPublicationChannelBusService publicationChannel;
	
	
	private String channel;
//	private String listedChannel;
	private List<IPublicationChannel> iPublicationChannels;


	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}


	public IPublicationChannelBusService getPublicationChannel() {
		return publicationChannel;
	}


	public void setPublicationChannel(IPublicationChannelBusService publicationChannel) {
		this.publicationChannel = publicationChannel;
	}

	
	public String createNewPublicationChannel(){
		
		String result="";
		publicationChannel.createNewPublicationChannel(channel);
		
		return result;
		
	}

	public void setIPublicationChannels(List<IPublicationChannel> ipublicationchannels) {
		this.iPublicationChannels = ipublicationchannels;
	}

	public List<IPublicationChannel> getiPublicationChannels() {
		return publicationChannel.getIPublicationChannels();
	}
//	
//	public String getListedChannel(){
//		
//		return publicationChannel.getChannel();
//		
//	}
	

}
