package pt.uc.dei.aor.project.business.persistence;

import pt.uc.dei.aor.project.business.model.IAnswerChoice;
import pt.uc.dei.aor.project.business.model.IColor;

public interface IColorPersistenceService {

	IColor save(IColor color);
	
	IColor findByTitle(String title);

}
