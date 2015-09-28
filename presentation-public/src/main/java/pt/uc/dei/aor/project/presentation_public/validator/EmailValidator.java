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
public class EmailValidator implements Validator {
	
	@Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
    	String email = (String) value;
    	
        if (email == null) {
            return;
        }
        
        if (!org.apache.commons.validator.routines.EmailValidator.getInstance().isValid(email)) {
        	throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email format is incorrect", null));
        }
    }

}