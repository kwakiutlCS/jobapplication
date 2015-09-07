package pt.uc.dei.aor.project.presentation.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import pt.uc.dei.aor.project.business.exception.IllegalFilterParamException;
import pt.uc.dei.aor.project.business.filter.InterviewFilter;
import pt.uc.dei.aor.project.business.model.IInterview;
import pt.uc.dei.aor.project.business.model.IWorker;
import pt.uc.dei.aor.project.business.service.IInterviewBusinessService;
import pt.uc.dei.aor.project.business.service.IWorkerBusinessService;

@Named
@ViewScoped
public class InterviewListBean implements Serializable {
	
	private static final long serialVersionUID = -5948435434591689631L;
	
	private int offset;
	private InterviewFilter filter;
		
	private List<IInterview> interviews;
	
	// filter params
	private IWorker filterInterviewer;
	private String filterPosition;
	
	
	@Inject
	private IInterviewBusinessService interviewService;
	
	@Inject
	private IWorkerBusinessService workerService;
	
	
	@PostConstruct
	public void init() {
		filter = new InterviewFilter();
	}
	
	
	
	// public methods
	
	public void onload() {
		interviews = interviewService.findInterviews(offset, 10);
	}
	
	
			// filter functions
	
	public List<IInterview> getActiveInterviews() {
		return interviewService.findActiveInterviewsByUser(getUser());
	}

	public List<IInterview> getInterviewsWithFilter() {
		return interviewService.findInterviews(offset, 10, filter);
	}
	
	
	
			// prepare filter functions
	
	public void addInterviewerToFilter() {
		filter.addInterviewerSet(filterInterviewer);
		interviews = getInterviewsWithFilter();
	}
	
	public void addPositionToFilter() {
		filter.addPosition(filterPosition);
		interviews = getInterviewsWithFilter();
		filterPosition = null;
	}
	
	public void deleteInterviewer(int setPos, int pos) {
		try {
			filter.deleteInterviewer(setPos, pos);
			interviews = getInterviewsWithFilter();
		} catch (IllegalFilterParamException e) {
			setMsg("todo", FacesMessage.SEVERITY_ERROR);
		}
	}
	
	public void deletePosition(int index) {
		filter.deletePosition(index);
		interviews = getInterviewsWithFilter();
	}
	
	
	public void mergeInterviewers(int setPos) {
		try {
			filter.mergeInterviewers(setPos);
			interviews = getInterviewsWithFilter();
		} catch (IllegalFilterParamException e) {
			setMsg("todo", FacesMessage.SEVERITY_ERROR);
		}
	}
	
	public void splitInterviewers(int setPos, int pos) {
		try {
			filter.splitInterviewers(setPos, pos);
			interviews = getInterviewsWithFilter();
		} catch (IllegalFilterParamException e) {
			setMsg("todo", FacesMessage.SEVERITY_ERROR);
		}
	}
	
	
	// helper functions
	
	private IWorker getUser() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		return (IWorker) request.getSession().getAttribute("user");	
	}

	private void setMsg(String text, Severity severity) {
		FacesMessage msg = new FacesMessage(severity,
				text, text);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	// getters and setters
	
	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}


	public List<IInterview> getInterviews() {
		return interviews;
	}


	public void setInterviews(List<IInterview> interviews) {
		this.interviews = interviews;
	}
	
	public Collection<IWorker> getAllInterviewers() {
		return workerService.findAllInterviewers();
	}


	public IWorker getFilterInterviewer() {
		return filterInterviewer;
	}


	public void setFilterInterviewer(IWorker filterInterviewer) {
		this.filterInterviewer = filterInterviewer;
	}

		
	public List<List<IWorker>> getInterviewerSets() {
		return filter.getInterviewerSets();
	}

	public List<String> getPositionSets() {
		return filter.getPositions();
	}


	public String getFilterPosition() {
		return filterPosition;
	}



	public void setFilterPosition(String filterPosition) {
		this.filterPosition = filterPosition;
	}
}

