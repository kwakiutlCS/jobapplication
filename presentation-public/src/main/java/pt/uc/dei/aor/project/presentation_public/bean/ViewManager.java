package pt.uc.dei.aor.project.presentation_public.bean;

import java.io.Serializable;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import pt.uc.dei.aor.project.business.model.IPosition;
import pt.uc.dei.aor.project.business.model.IUser;
import pt.uc.dei.aor.project.business.service.IApplicationBusinessService;
import pt.uc.dei.aor.project.business.service.IPositionBusinessService;
import pt.uc.dei.aor.project.presentation_public.util.MetaUtils;

@Named
@ViewScoped
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


	public String applyAfterLogin(){
	
		String redirect = "";
		
		//get Candidate
		IUser candidate = MetaUtils.getUser();
		//get Position applying for
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String,String> params = context.getExternalContext().getRequestParameterMap();	
		long positionId = Long.parseLong(params.get("position"));
		IPosition position = positionService.findPositionById(positionId);

		//check if Candidate already applied for given position
		if(duplicateApplication(candidate, position))
			redirect = "/authorized/listPosition.xhtml?faces-redirect=true";
					
		return redirect;
	}
	
	public String loginOnApply(){

		//login error - unregistered candidate 
		if(loginBeanI.login().equals("error")){
			RequestContext requestContext = RequestContext.getCurrentInstance();
			requestContext.execute("PF('loginDlg').show()");
			MetaUtils.setMsg("Login or password wrong", FacesMessage.SEVERITY_INFO);

			return null;
		}
		//login well succeeded
		else{

			//get Candidate
			IUser candidate = MetaUtils.getUser();
			//get Position applying for
			FacesContext context = FacesContext.getCurrentInstance();
			Map<String,String> params = context.getExternalContext().getRequestParameterMap();
			long positionId = Long.parseLong(params.get("position"));
			IPosition position = positionService.findPositionById(positionId);
			
			//check if Candidate already applied for given position
			if(duplicateApplication(candidate, position)){
				return "/authorized/listPosition.xhtml?faces-redirect=true";
			}

			return "/authorized/apply.xhtml?faces-redirect=true";
		}
	}


	public boolean duplicateApplication(IUser candidate, IPosition position){

		boolean duplicateApplication = false;

		if(applicationService.findApplicationByCandidateAndPosition(candidate, position)){
			MetaUtils.setMsg("You have already applied for this position", FacesMessage.SEVERITY_INFO);
			duplicateApplication = true;
		}
		return duplicateApplication;
	}

}
