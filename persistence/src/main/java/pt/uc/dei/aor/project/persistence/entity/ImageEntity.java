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
@Table(name="image")
@NamedQueries({
	@NamedQuery(name="Image.findAllImages", query = "from ImageEntity u"),
})
public class ImageEntity {
	
	public ImageEntity() {
		
	}

	public ImageEntity(String title) {
		src = title;
	}

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column
	private String src;
	

}
