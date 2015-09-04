package pt.uc.dei.aor.project.presentation.bean;

import java.util.List;
import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import pt.uc.dei.aor.project.business.model.IInterview;
import pt.uc.dei.aor.project.business.model.IScriptEntry;
import pt.uc.dei.aor.project.business.model.IWorker;
import pt.uc.dei.aor.project.business.service.IInterviewBusinessService;

@Named
@ViewScoped
public class InterviewBean implements Serializable {
	
	private static final long serialVersionUID = -4041049634352023785L;
	
	private long selectedInterviewId;
	private IInterview selectedInterview;

	
	@Inject
	private IInterviewBusinessService interviewService;
	
	
	
	public List<IScriptEntry> getScriptEntries() {
		return interviewService.getScriptEntries(selectedInterview);
	}
	
	public void onload() {
		setSelectedInterview(interviewService.findInterviewById(selectedInterviewId));
	}
	
	
	
	
	// helper functions
	
	private IWorker getUser() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		return (IWorker) request.getSession().getAttribute("user");	
	}

	
	
	// getters and setters
	
	public IInterview getSelectedInterview() {
		return selectedInterview;
	}

	public void setSelectedInterview(IInterview selectedInterview) {
		this.selectedInterview = selectedInterview;
	}

	public long getSelectedInterviewId() {
		return selectedInterviewId;
	}

	public void setSelectedInterviewId(long selectedInterviewId) {
		this.selectedInterviewId = selectedInterviewId;
	}
	
	
}

