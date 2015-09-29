package pt.uc.dei.aor.project.presentation_public.bean;

import java.io.Serializable;
import java.util.Date;

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
public class ApplicationBean implements Serializable {


	private static final long serialVersionUID = 1L;
	
	@Inject
	private IApplicationBusinessService applicationService;

	@Inject
	private IPositionBusinessService positionService;
	
	
	private String coverLetter;
	private String sourceInfo;
	private Date date;
	private boolean analyzed;
	private boolean refused;
	private IUser candidate;
	private IPosition position;
	private long selectedPositionId;

	private String cv;

	public long getSelectedPositionId() {
		return selectedPositionId;
	}

	public void setSelectedPositionId(long selectedPositionId) {
		this.selectedPositionId = selectedPositionId;
	}

	public ApplicationBean() {
	}
	
	public void createApplication(){
		System.out.println("registerBean *************");
		System.out.println(coverLetter);
		System.out.println(sourceInfo);
		System.out.println(candidate);
		System.out.println(position);
		System.out.println("registerBean *************");
	
		candidate=MetaUtils.getUser();
		position = positionService.findPositionById(2L);
		applicationService.createApplication(coverLetter, sourceInfo, candidate, position);
		
	}
	
	public void bothPositionAndUser(IUser candid, IPosition posit){
		position = posit;
		candidate = candid;
	}
	
	
	public void findPosition(){
		position =  positionService.findPositionById(selectedPositionId);
	}
	
	
	public String getCoverLetter() {
		return coverLetter;
	}


	public void setCoverLetter(String coverLetter) {
		this.coverLetter = coverLetter;
	}


	public String getSourceInfo() {
		return sourceInfo;
	}


	public void setSourceInfo(String sourceInfo) {
		this.sourceInfo = sourceInfo;
	}


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	public boolean isAnalyzed() {
		return analyzed;
	}


	public void setAnalyzed(boolean analyzed) {
		this.analyzed = analyzed;
	}


	public boolean isRefused() {
		return refused;
	}


	public void setRefused(boolean refused) {
		this.refused = refused;
	}


	public IPosition getPosition() {
		return position;
	}


	public void setPosition(IPosition position) {
		this.position = position;
	}


	public IUser getCandidate() {
		return candidate;
	}


	public void setCandidate(IUser candidate) {
		this.candidate = candidate;
	}





}
