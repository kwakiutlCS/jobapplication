package pt.uc.dei.aor.project.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="color")
public class ColorEntity {
	
	public ColorEntity() {
		
	}
	
	public ColorEntity(String page, String header, String content, String contentText, String contentTitle,
			String headerText, String background) {
		this.page = page;
		this.header = header;
		this.contentText = contentText;
		this.contentTitle = contentTitle;
		this.headerText = headerText;
		this.content = content;
		this.background = background;
	}

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	@Column(nullable=false)
	private String page;
	
	@Column(nullable=false)
	private String header;
	
	@Column(nullable=false)
	private String content;

	@Column(nullable=false)
	private String contentText;
	
	@Column(nullable=false)
	private String contentTitle;
	
	@Column(nullable=false)
	private String headerText;
	
	@Column(nullable=false)
	private String background;
	
}
