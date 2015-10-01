package pt.uc.dei.aor.project.business.service;

import pt.uc.dei.aor.project.business.model.IColor;

public interface IColorBusinessService {

	IColor save(IColor color);
	
	IColor findColorByTitle(String title);
	
	IColor save(String page, String header, String content, String contentText, String contentTitle, String headerText,
			String background);

	IColor color();
}
