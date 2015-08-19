package pt.uc.dei.aor.project.presentation.util;

import javax.faces.convert.EnumConverter;
import javax.faces.convert.FacesConverter;

import pt.uc.dei.aor.project.business.util.Localization;



@FacesConverter(value="localizationConverter")
public class LocalizationConverter extends EnumConverter{

		public LocalizationConverter() {
			super(Localization.class);
			
		}
}
