package pt.uc.dei.aor.project.presentation.util;

import javax.faces.convert.EnumConverter;
import javax.faces.convert.FacesConverter;

import pt.uc.dei.aor.project.business.util.QuestionType;

@FacesConverter(value="questionTypeConverter")
public class QuestionTypeConverter extends EnumConverter{

		public QuestionTypeConverter() {
			super(QuestionType.class);
		}
}
