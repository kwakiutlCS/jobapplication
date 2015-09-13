package pt.uc.dei.aor.project.presentation.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;


@FacesConverter(value="dateConverter")
public class DateConverter implements javax.faces.convert.Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    	
    	try {
    		Date d = sdf.parse(value);
    		return sdf.parse(value);
		} catch (ParseException e) {
			return null;
		}
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
    	
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    	
    	Date date = (Date) value;
    	return sdf.format(date);
	}
}