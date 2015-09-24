package pt.uc.dei.aor.project.presentation.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import pt.uc.dei.aor.project.business.model.IPublicationChannel;
import pt.uc.dei.aor.project.business.service.IPublicationChannelBusService;
import pt.uc.dei.aor.project.presentation.util.MetaUtils;



@Named
@ViewScoped
public class PublicationChannelBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private IPublicationChannelBusService publicationChannel;


	private String channel;
	private String editchannel;
	private List<IPublicationChannel> channels;
	
	
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


	public void setIPublicationChannels(List<IPublicationChannel> ipublicationchannels) {
	}

	public List<IPublicationChannel> getiPublicationChannels() {
		if (channels == null || channels.isEmpty()) {
			channels = publicationChannel.getIPublicationChannels();
		}
		return channels;
	}


	public void createNewPublicationChannel(){
		for (IPublicationChannel c : channels) {
			if (c.getChannel().equals(editchannel)) {
				MetaUtils.setMsg("Publication channel "+editchannel+" already exists", FacesMessage.SEVERITY_ERROR,
						"channelMessage");
				return;
			}
		}
		channels.add(publicationChannel.createNewPublicationChannel(editchannel));
		
		editchannel = null;

	}

	public void deletePublicationChannel(IPublicationChannel ipchannel){
		try {
			publicationChannel.deletePublicationChannel(ipchannel);
			channels = publicationChannel.getIPublicationChannels();
		}
		catch (Exception e) {
			MetaUtils.setMsg("Channel is being used in a position", FacesMessage.SEVERITY_ERROR);
		}
	}

	public String getEditchannel() {
		return editchannel;
	}

	public void setEditchannel(String editchannel) {
		this.editchannel = editchannel;
	}

	public List<IPublicationChannel> getChannels() {
		return channels;
	}

	public void setChannels(List<IPublicationChannel> channels) {
		this.channels = channels;
	}


}
