package pt.uc.dei.aor.project.presentation_public.bean;

import java.io.Serializable;
import java.util.Map;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import pt.uc.dei.aor.project.business.model.ICandidate;
import pt.uc.dei.aor.project.business.model.IPosition;
import pt.uc.dei.aor.project.business.service.IApplicationBusinessService;
import pt.uc.dei.aor.project.business.service.IPositionBusinessService;
import pt.uc.dei.aor.project.presentation_public.util.MetaUtils;

@Named
@RequestScoped
public class ViewManager implements Serializable {


	private static final long serialVersionUID = 1L;

	@Inject
	private LoginBean loginBeanI;

	@Inject
	private IApplicationBusinessService applicationService;

	@EJB
	private IPositionBusinessService positionService;



	public int verifyLogin(){

		int unlogged = 0;

		if(MetaUtils.getUser()!=null){
			unlogged = 1;	
		}
		return unlogged;
	}

	public String swapDialog(){
		
		RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.execute("PF('positionDetailDlg').hide()"); 

		if(MetaUtils.getUser()==null){
			System.out.println("Swaping dialogs");	
			requestContext.execute("PF('identifyDlg').show()");
			return null;
		}
		else 
			return "/authorized/apply.xhtml?faces-redirect=true";
	}

	public String applyAfterLogin(){
	
		String redirect = "";
		
		//get Candidate
		ICandidate candidate = MetaUtils.getUser();
		//get Position applying for
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String,String> params = context.getExternalContext().getRequestParameterMap();
		long positionId = Long.parseLong(params.get("position"));
		IPosition position = positionService.find(positionId);

		//check if Candidate already applied for given position
		if(duplicateApplication(candidate, position))
			redirect = "/authorized/oportunities.xhtml?faces-redirect=true";
		
		
				
		return redirect;
	}
	
	

	public String loginOnApply(){

		String redirect="";

		//login error - unregistered candidate 
		if(loginBeanI.login().equals("loginerror")){
			RequestContext requestContext = RequestContext.getCurrentInstance();
			requestContext.execute("PF('identifyDlg').show()");
			MetaUtils.setMsg("Login or password wrong", FacesMessage.SEVERITY_INFO);

		}
		//login well succeeded
		else{

			//get Candidate
			ICandidate candidate = MetaUtils.getUser();
			//get Position applying for
			FacesContext context = FacesContext.getCurrentInstance();
			Map<String,String> params = context.getExternalContext().getRequestParameterMap();
			long positionId = Long.parseLong(params.get("position"));
			IPosition position = positionService.find(positionId);

			//check if Candidate already applied for given position
			if(duplicateApplication(candidate, position))
				redirect = "/authorized/oportunities.xhtml?faces-redirect=true";

			redirect = "/authorized/apply.xhtml?faces-redirect=true";
		}

		return redirect;
	}

	public boolean duplicateApplication(ICandidate candidate, IPosition position){

		boolean duplicateApplication = false;

		if(applicationService.findApplicationByCandidateAndPosition(candidate, position)){
			MetaUtils.setMsg("You have already applied for this position", FacesMessage.SEVERITY_INFO);
			duplicateApplication = true;
		}

		return duplicateApplication;
	}
	

}
