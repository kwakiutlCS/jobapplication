package pt.uc.dei.aor.project.presentation_public.bean;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.uc.dei.aor.project.business.model.IPosition;
import pt.uc.dei.aor.project.business.model.IUser;
import pt.uc.dei.aor.project.business.service.IApplicationBusinessService;
import pt.uc.dei.aor.project.business.service.IPositionBusinessService;
import pt.uc.dei.aor.project.business.service.IUserBusinessService;
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
	
	@Inject
	private IUserBusinessService candidateService;
	
	
	private Part coverLetter;
	private String sourceInfo;
	private IPosition position;
	private long selectedPositionId;
	private Part cv;

	public ApplicationBean() {
	}
	
	public void createApplication(){

		IUser candidate = MetaUtils.getUser();
		applicationService.createApplication(coverLetter.getSubmittedFileName(), cv.getSubmittedFileName(),sourceInfo, candidate, position);
		
	}
		
	public void findPosition(){
		position =  positionService.findPositionById(selectedPositionId);
	}

//	public void uploadLetter(AjaxBehaviorEvent event) {
//
//		if (!coverLetter.getContentType().equals("application/pdf")) {
//			MetaUtils.setMsg("Please upload a pdf file", FacesMessage.SEVERITY_ERROR);
//			return; 
//		}
//
//		try {
//		 applicationService.uploadLetter(coverLetter);
//		} catch (IOException e) {
//			MetaUtils.setMsg("Error uploading file", FacesMessage.SEVERITY_ERROR);
//			coverLetter = null;
//			logger.error("Error uploading file: "+cv.getSubmittedFileName());
//		}
//	}
//	
//	public void upload(AjaxBehaviorEvent event) {
//
//		if (!cv.getContentType().equals("application/pdf")) {
//			MetaUtils.setMsg("Please upload a pdf file", FacesMessage.SEVERITY_ERROR);
//			return; 
//		}
//
//		try {
//			provisoryCv = candidateService.uploadTempCV(cv);
//			cvPath = cv.getSubmittedFileName();
//		} catch (IOException e) {
//			MetaUtils.setMsg("Error uploading file", FacesMessage.SEVERITY_ERROR);
//			cv = null;
//			logger.error("Error uploading file: "+cv.getSubmittedFileName());
//		}
//	}
//
//	
	
	
	
	
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
