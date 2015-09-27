package pt.uc.dei.aor.project.business.model;

public interface IColor {

	public String getBackground();
	
	public String getContent();
	
	public String getHeader();
	
	public String getContentText();
	
	public String getContentTitle();
	
	public String getHeaderText();
	
	public void setBackground(String background);
	
	public void setContent(String content);
	
	public void setHeader(String header);
	
	public void setContentText(String contentText);
	
	public void setContentTitle(String contentTitle);
	
	public void setHeaderText(String headerText);
}
