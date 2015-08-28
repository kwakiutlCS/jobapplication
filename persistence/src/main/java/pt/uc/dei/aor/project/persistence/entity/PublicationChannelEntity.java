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
	@NamedQuery(name="publicationChannel.findByString", query="SELECT c FROM PublicChannelEntity c WHERE c.name LIKE :name")
})
public class PublicationChannelEntity {
	

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable=false)
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

	
	
}
