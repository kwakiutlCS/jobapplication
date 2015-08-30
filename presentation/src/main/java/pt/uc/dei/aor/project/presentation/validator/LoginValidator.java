package pt.uc.dei.aor.project.presentation.validator;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

import pt.uc.dei.aor.project.business.service.IWorkerBusinessService;



@Named
@RequestScoped
public class LoginValidator implements Validator {
	
	@Inject 
	private IWorkerBusinessService ejb;
	  
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
    	String login = (String) value;
    	
        if (login == null) {
            return;
        }
        
        if (ejb.getWorkerByLogin(login) != null) {
        	throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login already registered", null));
        }
    }

}