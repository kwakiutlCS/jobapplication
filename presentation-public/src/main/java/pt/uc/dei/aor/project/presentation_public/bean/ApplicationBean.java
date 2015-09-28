package pt.uc.dei.aor.project.presentation_public.bean;

import java.io.Serializable;
import java.util.Date;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import pt.uc.dei.aor.project.business.model.IPosition;
import pt.uc.dei.aor.project.business.model.IUser;
import pt.uc.dei.aor.project.business.service.IApplicationBusinessService;


@Named
@ViewScoped
public class ApplicationBean implements Serializable {


	private static final long serialVersionUID = 1L;
	
	@Inject
	private IApplicationBusinessService applicationService;

	
	
	private String coverLetter;
	private String sourceInfo;
	private Date date;
	private boolean analyzed;
	private boolean refused;
	private IUser candidate;
	private IPosition position;


	public ApplicationBean() {
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
