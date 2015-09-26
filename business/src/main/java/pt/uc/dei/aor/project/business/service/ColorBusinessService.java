package pt.uc.dei.aor.project.business.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import pt.uc.dei.aor.project.business.model.IColor;
import pt.uc.dei.aor.project.business.model.IModelFactory;
import pt.uc.dei.aor.project.business.persistence.IColorPersistenceService;


@Stateless
public class ColorBusinessService implements IColorBusinessService {

	@Inject
	private IModelFactory factory;
	
	@Inject
	private IColorPersistenceService colorPersistence;
	
	@Override
	public IColor save(IColor color) {
		return colorPersistence.save(color);
	}

	@Override
	public IColor findColorByTitle(String title) {
		return colorPersistence.findByTitle(title);
	}

	@Override
	public IColor save(String page, String header, String content, String contentText, String contentTitle,
			String headerText, String background) {
		IColor color = factory.color(page, header, content, contentText, contentTitle, headerText, background);
		return colorPersistence.save(color);
	}
	
}
