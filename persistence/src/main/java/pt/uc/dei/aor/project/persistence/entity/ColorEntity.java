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
		if (header == null || header.length() == 0) {
			this.header = header;
			return;
		}
		if (header.charAt(0) != '#')
			this.header = "#"+header;
		else
			this.header = header;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		if (content == null || content.length() == 0) {
			this.content = content;
			return;
		}
		if (content.charAt(0) != '#')
			this.content = "#"+content;
		else
			this.content = content;
	}

	public String getContentText() {
		return contentText;
	}

	public void setContentText(String contentText) {
		if (contentText == null || contentText.length() == 0) {
			this.contentText = contentText;
			return;
		}
		if (contentText.charAt(0) != '#')
			this.contentText = "#"+contentText;
		else
			this.contentText = contentText;
	}

	public String getContentTitle() {
		return contentTitle;
	}

	public void setContentTitle(String contentTitle) {
		if (contentTitle == null || contentTitle.length() == 0) {
			this.contentTitle = contentTitle;
			return;
		}
		if (contentTitle.charAt(0) != '#')
			this.contentTitle = "#"+contentTitle;
		else
			this.contentTitle = contentTitle;
	}

	public String getHeaderText() {
		return headerText;
	}

	public void setHeaderText(String headerText) {
		if (headerText == null || headerText.length() == 0) {
			this.headerText = headerText;
			return;
		}
		if (headerText.charAt(0) != '#')
			this.headerText = "#"+headerText;
		else
			this.headerText = headerText;
	}

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		if (background == null || background.length() == 0) {
			this.background = background;
			return;
		}
		if (background.charAt(0) != '#')
			this.background = "#"+background;
		else
			this.background = background;
	}
	
	
}
