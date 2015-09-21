package pt.uc.dei.aor.project.presentation_public.validator;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

import pt.uc.dei.aor.project.business.service.ICandidateBusinessService;
import pt.uc.dei.aor.project.presentation_public.util.MetaUtils;



@Named
@RequestScoped
public class LoginChangeValidator implements Validator {
	
	@Inject 
	private ICandidateBusinessService ejb;
	  
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
    	String login = (String) value;
    	
        if (login == null) {
            return;
        }
        
        System.out.println("AQUI"+ejb.getCandidateByEmail(login));
        
        System.out.println("AQUI USER:"+MetaUtils.getUser().getLogin());
        
        
        if (!MetaUtils.getUser().getLogin().equals(login) && ejb.getCandidateByEmail(login) != null) {
        	throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login already registered", null));
        }
    }

}