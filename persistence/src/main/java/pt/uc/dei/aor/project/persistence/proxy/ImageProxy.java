package pt.uc.dei.aor.project.persistence.proxy;

import pt.uc.dei.aor.project.business.model.IImage;
import pt.uc.dei.aor.project.persistence.entity.ImageEntity;

public class ImageProxy implements IImage, IProxyToEntity<ImageEntity> {

	private ImageEntity entity;
	
	public ImageProxy(ImageEntity entity) {
		this.entity = entity != null ? entity : new ImageEntity();
	}


	public ImageProxy() {
		entity = new ImageEntity();
	}


	public ImageProxy(String title) {
		entity = new ImageEntity(title);
	}


	@Override
	public ImageEntity getEntity() {
		return entity;
	}



}
