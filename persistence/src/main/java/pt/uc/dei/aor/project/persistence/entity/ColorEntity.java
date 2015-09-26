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
@NamedQueries({
	@NamedQuery(name="Color.findColorByTitle", query = "from ColorEntity c where c.page = :title"),
})
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

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = "#"+header;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = "#"+content;
	}

	public String getContentText() {
		return contentText;
	}

	public void setContentText(String contentText) {
		this.contentText = "#"+contentText;
	}

	public String getContentTitle() {
		return contentTitle;
	}

	public void setContentTitle(String contentTitle) {
		this.contentTitle = "#"+contentTitle;
	}

	public String getHeaderText() {
		return headerText;
	}

	public void setHeaderText(String headerText) {
		this.headerText = "#"+headerText;
	}

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = "#"+background;
	}
	
	
}
