package pt.uc.dei.aor.project.presentation_public.bean;

import java.io.Serializable;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

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

	@Inject
	private IPositionBusinessService positionService;


	public int verifyLogin(){

		int unlogged = 0;

		if(MetaUtils.getUser()!=null){
			unlogged = 1;	
		}
		return unlogged;
	}

	
	public boolean loginOnApply(){

		if(MetaUtils.getUser()==null)
			return true;

		else if(loginBeanI.login().equals("error")){
			//		MetaUtils.setMsg("Login or password wrong", FacesMessage.SEVERITY_INFO);
			return false;
		}
		else if(!candidateHasCompletedRegistry()){
			return false;
		}
		else if(duplicateApplication()) 
			return false;

		else
			return true;
	}

	public boolean duplicateApplication(){

		boolean duplicateApplication = false;

		//get Candidate
		IUser candidate = MetaUtils.getUser();

		//get Position applying for
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String,String> params = context.getExternalContext().getRequestParameterMap();	
		long positionId = Long.parseLong(params.get("position"));
		IPosition position = positionService.findPositionById(positionId);

		if(applicationService.findApplicationByCandidateAndPosition(candidate, position)){
			//		MetaUtils.setMsg("You have already applied for this position", FacesMessage.SEVERITY_INFO);
			duplicateApplication = true;
		}
		return duplicateApplication;
	}


	public boolean candidateHasCompletedRegistry(){

		IUser candidate = MetaUtils.getUser();

		if(candidate.getAddress()==null||candidate.getCv()==null||candidate.getQualifications().isEmpty()){
			//		MetaUtils.setMsg("Please complete your profile record", FacesMessage.SEVERITY_INFO);
			return false;
		}
		return true;
	}




}
