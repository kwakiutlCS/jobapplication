package pt.uc.dei.aor.project.persistence.proxy;

import java.util.List;

import pt.uc.dei.aor.project.business.persistence.IPresentationText;
import pt.uc.dei.aor.project.persistence.entity.PresentationTextEntity;
import pt.uc.dei.aor.project.persistence.entity.TextBoxEntity;




public class PresentationTextProxy implements IPresentationText, IProxyToEntity<PresentationTextEntity> {

	private PresentationTextEntity entity;

	public PresentationTextProxy(PresentationTextEntity entity) {
		this.entity = entity != null ? entity : new PresentationTextEntity();
	}

	public PresentationTextProxy(List<TextBoxEntity> text, String title){
		
		entity = new PresentationTextEntity (text, title);
	}
	
	
	@Override
	public PresentationTextEntity getEntity() {
		return entity;
	}


}
