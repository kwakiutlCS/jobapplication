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

	private List<IScriptEntry> scriptEntries;
	private IScriptEntry selectedEntry;
	
	private String answer;
	
	@Inject
	private IInterviewBusinessService interviewService;
	
	
	
	public void onload() {
		setSelectedInterview(interviewService.findInterviewById(selectedInterviewId));
		scriptEntries = findScriptEntries();
		
		if (scriptEntries != null || !scriptEntries.isEmpty())
			selectedEntry = scriptEntries.get(0);
	}
	
	public void changeQuestion(IScriptEntry entry) {
		selectedEntry = entry;
	}
	
	public void saveAnswer() {
		interviewService.saveAnswer(selectedInterview, answer, selectedEntry);
	}
	
	
	// helper functions
	
	private IWorker getUser() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		return (IWorker) request.getSession().getAttribute("user");	
	}

	private List<IScriptEntry> findScriptEntries() {
		return interviewService.getScriptEntries(selectedInterview);
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

	public IScriptEntry getSelectedEntry() {
		return selectedEntry;
	}

	public void setSelectedEntry(IScriptEntry selectedEntry) {
		this.selectedEntry = selectedEntry;
	}

	public void setScriptEntries(List<IScriptEntry> scriptEntries) {
		this.scriptEntries = scriptEntries;
	}
	
	public List<IScriptEntry> getScriptEntries() {
		return scriptEntries;
	}




	public String getAnswer() {
		return answer;
	}




	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
}

