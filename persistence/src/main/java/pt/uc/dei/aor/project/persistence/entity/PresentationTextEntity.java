package pt.uc.dei.aor.project.persistence.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="presentationTextEntity")
public class PresentationTextEntity {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	@Column
	private String title;
	
	@OneToMany(mappedBy="presentationTextEntity" )
	private List<TextBoxEntity> text;

	public PresentationTextEntity() {
	}

	public PresentationTextEntity(List<TextBoxEntity> text, String title) {
		this.text = text;
		this.title= title;
	}

	public List<TextBoxEntity> getText() {
		return text;
	}

	public void setText(List<TextBoxEntity> text) {
		this.text = text;
	}

	public long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	
	
	
	
	
	
	
	

}
