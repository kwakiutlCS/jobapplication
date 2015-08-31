package pt.uc.dei.aor.project.presentation.bean;

import java.util.Collection;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import pt.uc.dei.aor.project.business.model.IInterview;
import pt.uc.dei.aor.project.business.model.IWorker;
import pt.uc.dei.aor.project.business.service.IInterviewBusinessService;

@Named
@RequestScoped
public class InterviewBean {
	
	@Inject
	private IInterviewBusinessService interviewService;
	
	private IInterview selected;
	
	
	public Collection<IInterview> getActiveInterviews() {
		IWorker user = (IWorker) getSession().getAttribute("user");
		return interviewService.findActiveInterviewsByUser(user);
	}
	
	
	public String selectInterview(IInterview interview) {
		selected = interview;
		return "interview.xhtml?faces-redirect=true";
	}
	
	
	
	// helper methods
	
	private HttpSession getSession() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		return request.getSession();
	}
}

