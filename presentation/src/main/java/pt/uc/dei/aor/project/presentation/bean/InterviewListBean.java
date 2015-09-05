package pt.uc.dei.aor.project.presentation.bean;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import pt.uc.dei.aor.project.business.model.IInterview;
import pt.uc.dei.aor.project.business.model.IWorker;
import pt.uc.dei.aor.project.business.service.IInterviewBusinessService;

@Named
@RequestScoped
public class InterviewListBean {
	
	private int offset;
	private List<IInterview> interviews;
	
	@Inject
	private IInterviewBusinessService interviewService;
	
	
	public List<IInterview> getActiveInterviews() {
		return interviewService.findActiveInterviewsByUser(getUser());
	}

	
	public void onload() {
		interviews = interviewService.findInterviews(offset, 10);
	}
	
	// helper functions
	
	private IWorker getUser() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		return (IWorker) request.getSession().getAttribute("user");	
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
}

