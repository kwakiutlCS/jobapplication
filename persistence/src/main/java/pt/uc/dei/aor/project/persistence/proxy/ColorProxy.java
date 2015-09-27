package pt.uc.dei.aor.project.persistence.proxy;

import pt.uc.dei.aor.project.business.model.IColor;
import pt.uc.dei.aor.project.persistence.entity.ColorEntity;

public class ColorProxy implements IColor, IProxyToEntity<ColorEntity> {

	private ColorEntity entity;
	
	public ColorProxy(ColorEntity entity) {
		this.entity = entity != null ? entity : new ColorEntity();
	}


	public ColorProxy() {
		this(null);
	}


	public ColorProxy(String page, String header, String content, String contentText, String contentTitle,
			String headerText, String background) {
		entity = new ColorEntity(page, header, content, contentText, contentTitle, headerText, background);
	}


	@Override
	public ColorEntity getEntity() {
		return entity;
	}


	@Override
	public String getBackground() {
		System.out.println("back "+entity.getBackground());
		return entity.getBackground();
	}


	@Override
	public String getContent() {
		System.out.println("content "+entity.getContent());
		return entity.getContent();
	}


	@Override
	public String getHeader() {
		System.out.println("header "+entity.getHeader());
		return entity.getHeader();
	}


	@Override
	public String getContentText() {
		return entity.getContentText();
	}


	@Override
	public String getContentTitle() {
		return entity.getContentTitle();
	}


	@Override
	public String getHeaderText() {
		return entity.getHeaderText();
	}


	@Override
	public void setBackground(String background) {
		entity.setBackground(background);
	}


	@Override
	public void setContent(String content) {
		entity.setContent(content);
	}


	@Override
	public void setHeader(String header) {
		entity.setHeader(header);
	}


	@Override
	public void setContentText(String contentText) {
		entity.setContentText(contentText);
	}


	@Override
	public void setContentTitle(String contentTitle) {
		entity.setContentTitle(contentTitle);
	}


	@Override
	public void setHeaderText(String headerText) {
		entity.setHeaderText(headerText);
	}


}
