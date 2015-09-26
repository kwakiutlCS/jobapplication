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


}
