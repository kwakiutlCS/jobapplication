package pt.uc.dei.aor.project.presentation_public.validator;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;



@Named
@RequestScoped
public class TimeValidator implements Validator {
	
	@Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
    	if (value == null) return;
		
		String time = String.valueOf(value);
    	
        if (time.charAt(2) != 'h')
        	throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, 
        			"Time format is incorrect", null));
        
        String[] fields = time.split("h");
        int hour, minute;
        
        try {
        	hour = Integer.parseInt(fields[0]);
        	minute = Integer.parseInt(fields[1]);
        }
        catch(Exception e) {
        	throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, 
        			"Time format is incorrect", null));
        }
        
        if (hour >= 24 || hour < 0 || minute >= 60 || minute < 0) 
        	throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, 
        			"Time format is incorrect", null));
    }

}