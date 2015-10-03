package pt.uc.dei.aor.project.presentation_public.bean;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.uc.dei.aor.project.business.exception.IllegalApplicationException;
import pt.uc.dei.aor.project.business.model.IPosition;
import pt.uc.dei.aor.project.business.model.IUser;
import pt.uc.dei.aor.project.business.service.IApplicationBusinessService;
import pt.uc.dei.aor.project.business.service.IPositionBusinessService;
import pt.uc.dei.aor.project.business.util.PositionState;
import pt.uc.dei.aor.project.presentation_public.util.MetaUtils;


@Named
@ViewScoped
public class ApplicationBean implements Serializable {


	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = LoggerFactory.getLogger(ApplicationBean.class);
	
	@Inject
	private IApplicationBusinessService applicationService;

	@Inject
	private IPositionBusinessService positionService;
	
	
	public String getProvisoryLetter() {
		return provisoryLetter;
	}

	public String getProvisoryCv() {
		return provisoryCv;
	}

	private Part coverLetter;
	private String sourceInfo;
	private IPosition position;
	private long selectedPositionId;
	private Part cv;

	private String provisoryLetter;
	private String provisoryCv;
	
	public ApplicationBean() {
	}
	
	public String createApplication(){
		if (provisoryLetter == null) {
			MetaUtils.setMsg("Upload a cover letter to apply", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		IUser candidate = MetaUtils.getUser();
		try {
			applicationService.createApplication(provisoryLetter, coverLetter, provisoryCv, cv, sourceInfo, candidate, position);
			MetaUtils.setMsg("Application to "+position+" created", FacesMessage.SEVERITY_INFO);
			return "authorized/listPosition.xhtml?faces-redirect=true";
		} catch (IOException e) {
			MetaUtils.setMsg("Error creating application", FacesMessage.SEVERITY_ERROR);
		} catch (IllegalApplicationException e) {
			MetaUtils.setMsg("Already applied to this position", FacesMessage.SEVERITY_ERROR);
		}
		return "myPage.xhtml?faces-redirect=true";
	}
	
	public String createSpontaneous() {
		if (provisoryLetter == null) {
			MetaUtils.setMsg("Upload a cover letter to apply", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		IUser candidate = MetaUtils.getUser();
		if (candidate.getCv() == null) {
			MetaUtils.setMsg("Upload a cover letter to apply", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		try {
			applicationService.createApplication(provisoryLetter, coverLetter, provisoryCv, cv, sourceInfo, candidate);
			MetaUtils.setMsg("Spontaneous application created", FacesMessage.SEVERITY_INFO);
			return "authorized/listPosition.xhtml?faces-redirect=true";
		} catch (IOException e) {
			System.out.println(e.getMessage());
			MetaUtils.setMsg("Error creating application", FacesMessage.SEVERITY_ERROR);
		}
		return "myPage.xhtml?faces-redirect=true";
	}
		
	public IPosition findPosition(){
		
		position =  positionService.findPositionById(selectedPositionId);
		
		IUser candidate = MetaUtils.getUser();
		
		if(candidate.getAddress()==null||candidate.getCv()==null||candidate.getQualifications().isEmpty()){
			MetaUtils.setMsg("Please complete your profile record", FacesMessage.SEVERITY_INFO);
			position = null;
		}
	
		else if(applicationService.findApplicationByCandidateAndPosition(candidate, position)){
			MetaUtils.setMsg("You have already applied for this position", FacesMessage.SEVERITY_INFO);
			position = null;
		}
		else if (position.getState() != PositionState.OPEN) {
			MetaUtils.setMsg("This position is not open anymore", FacesMessage.SEVERITY_INFO);
			position = null;
		}
		 
		return position;
	}

	public void uploadLetter(AjaxBehaviorEvent event) {

		if (!coverLetter.getContentType().equals("application/pdf")) {
			MetaUtils.setMsg("Please upload a pdf file", FacesMessage.SEVERITY_ERROR);
			return; 
		}

		try {
		  provisoryLetter = applicationService.uploadTempLetter(coverLetter);
		} catch (IOException e) {
			MetaUtils.setMsg("Error uploading file", FacesMessage.SEVERITY_ERROR);
			coverLetter = null;
			logger.error("Error uploading file: "+coverLetter.getSubmittedFileName());
		}
	}
	
	public void uploadCv(AjaxBehaviorEvent event) {

		if (!cv.getContentType().equals("application/pdf")) {
			MetaUtils.setMsg("Please upload a pdf file", FacesMessage.SEVERITY_ERROR);
			return; 
		}

		try {
			provisoryCv = applicationService.uploadTempCV(cv);
		} catch (IOException e) {
			MetaUtils.setMsg("Error uploading file", FacesMessage.SEVERITY_ERROR);
			cv = null;
			logger.error("Error uploading file: "+cv.getSubmittedFileName());
		}
	}

	
	public boolean hasSpontaneous() {
		return applicationService.hasSpontaneous(MetaUtils.getUser());
	}
	
	
	
	public Part getCoverLetter() {
		return coverLetter;
	}

	public void setCoverLetter(Part coverLetter) {
		this.coverLetter = coverLetter;
	}

	public String getSourceInfo() {
		return sourceInfo;
	}

	public void setSourceInfo(String sourceInfo) {
		this.sourceInfo = sourceInfo;
	}


	public IPosition getPosition() {
		return position;
	}

	public void setPosition(IPosition position) {
		this.position = position;
	}

	public long getSelectedPositionId() {
		return selectedPositionId;
	}

	public void setSelectedPositionId(long selectedPositionId) {
		this.selectedPositionId = selectedPositionId;
	}

	public Part getCv() {
		return cv;
	}

	public void setCv(Part cv) {
		this.cv = cv;
	}
	
	

}
