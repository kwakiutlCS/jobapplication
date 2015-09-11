package pt.uc.dei.aor.project.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="publication_channel")
@NamedQueries({
	@NamedQuery(name="publicationChannel.findAll", query="from PublicationChannelEntity u"),
	@NamedQuery(name="publicationChannel.findByName", query="from PublicationChannelEntity u where u.channel like:i")
})
public class PublicationChannelEntity implements Comparable<PublicationChannelEntity> {
	

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable=false, unique=true)
	private String channel;
	
	
	public PublicationChannelEntity(String channel) {
		this.channel = channel;
	}


	public PublicationChannelEntity() {
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getChannel() {
		return channel;
	}


	public void setTitle(String channel) {
		this.channel = channel;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((channel == null) ? 0 : channel.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PublicationChannelEntity other = (PublicationChannelEntity) obj;
		if (channel == null) {
			if (other.channel != null)
				return false;
		} else if (!channel.equals(other.channel))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "PublicationChannelEntity [channel=" + channel + "]";
	}


	@Override
	public int compareTo(PublicationChannelEntity o) {
		return channel.compareTo(o.channel);
	}
	
	
}
