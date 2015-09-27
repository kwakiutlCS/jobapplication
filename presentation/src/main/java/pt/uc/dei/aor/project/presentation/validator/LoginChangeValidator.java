package pt.uc.dei.aor.project.presentation.validator;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

import pt.uc.dei.aor.project.business.service.IUserBusinessService;
import pt.uc.dei.aor.project.presentation.util.MetaUtils;



@Named
@RequestScoped
public class LoginChangeValidator implements Validator {
	
	@Inject 
	private IUserBusinessService ejb;
	  
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
    	String login = (String) value;
    	
        if (login == null) {
            return;
        }
        
        if (!MetaUtils.getUser().getLogin().equals(login) && ejb.getUserByLogin(login) != null) {
        	throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login already registered", null));
        }
    }

}