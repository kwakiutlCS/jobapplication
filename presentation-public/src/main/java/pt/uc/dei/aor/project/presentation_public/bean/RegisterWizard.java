package pt.uc.dei.aor.project.presentation_public.bean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.event.FlowEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.uc.dei.aor.project.business.exception.DuplicatedUserException;
import pt.uc.dei.aor.project.presentation_public.util.MetaUtils;

@Named
@ViewScoped
public class RegisterWizard implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = LoggerFactory.getLogger(RegisterWizard.class);

	
    private RegisterBean onregist = new RegisterBean(); 
    private boolean skip;
     
    public RegisterBean getRegisterBean() {
        return onregist;
    }
 
    public void setRegisterBean(RegisterBean onregist) {
        this.onregist = onregist;
    }
     
    public void save() throws DuplicatedUserException {  
    	System.out.println("MeatUtil getUser: "+MetaUtils.getUser().getName());
    	onregist.updateCandidate(MetaUtils.getUser());
        FacesMessage msg = new FacesMessage("Successful", "Welcome :" + MetaUtils.getUser().getName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
     
    public boolean isSkip() {
        return skip;
    }
 
    public void setSkip(boolean skip) {
        this.skip = skip;
    }
     
    public String onFlowProcess(FlowEvent event) {
        if(skip) {
            skip = false;   //reset in case user goes back
            return "confirm";
        }
        else {
            return event.getNewStep();
        }
    }
}