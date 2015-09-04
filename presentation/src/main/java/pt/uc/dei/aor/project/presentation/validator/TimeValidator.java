package pt.uc.dei.aor.project.presentation.validator;

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
    	String time = (String) value;
    	
        if (time == null) {
            return;
        }
        
        String[] fields = time.split("h");
        
        int hour = Integer.parseInt(fields[0]);
        int minute = Integer.parseInt(fields[1]);
        
        if (hour >= 24 || minute >= 60) 
        	throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, 
        			"Time format is incorrect", null));
    }

}