package pt.uc.dei.aor.project.persistence.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="publication_channel")
public class PublicationChannelEntity {
	

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable=false)
	private String title;
	
	@ManyToMany
	private List<PositionEntity> positions;
	
	
	public PublicationChannelEntity(String channel) {
	}

	
	public PublicationChannelEntity() {
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public List<PositionEntity> getPositions() {
		return positions;
	}


	public void setPositions(List<PositionEntity> positions) {
		this.positions = positions;
	}
	
	
	
}
