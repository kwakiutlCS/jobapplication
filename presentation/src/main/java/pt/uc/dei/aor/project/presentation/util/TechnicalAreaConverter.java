package pt.uc.dei.aor.project.presentation.util;

import javax.faces.convert.EnumConverter;
import javax.faces.convert.FacesConverter;

import pt.uc.dei.aor.project.business.util.TechnicalArea;

@FacesConverter(value="technicalAreaConverter")
public class TechnicalAreaConverter extends EnumConverter{

	public TechnicalAreaConverter() {
		super(TechnicalArea.class);
		
	}

	
	

}
