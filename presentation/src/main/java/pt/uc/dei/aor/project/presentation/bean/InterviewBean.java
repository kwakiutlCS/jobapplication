package pt.uc.dei.aor.project.presentation.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import pt.uc.dei.aor.project.business.model.IInterview;
import pt.uc.dei.aor.project.business.model.IWorker;
import pt.uc.dei.aor.project.business.service.IInterviewBusinessService;
import pt.uc.dei.aor.project.business.service.IWorkerBusinessService;


@Named
@ViewScoped
public class InterviewBean implements Serializable {
	
	private static final long serialVersionUID = -4484148866779896144L;

	@Inject
	private IInterviewBusinessService interviewService;
	
	@Inject
	private IWorkerBusinessService workerService;
	
	@Inject
	private ApplicationBean applicationBean;
	
	
	private IInterview selected;
	
	private Collection<IWorker> selectedInterviewers;
	private IWorker interviewer;
	private Date interviewDate;
	private String interviewTime;
	private Date tommorrow;
	
	
	public InterviewBean() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_YEAR, 1);
		tommorrow = c.getTime();
	}
	
	public Collection<IInterview> getActiveInterviews() {
		IWorker user = (IWorker) getSession().getAttribute("user");
		return interviewService.findActiveInterviewsByUser(user);
	}
	
	public List<IInterview> getInterviewsByApplication() {
		return applicationBean.getSelectedApplication().getInterviews();
		//return interviewService.findInterviewsByApplication(applicationBean.getSelectedApplication());
	}
	
	public String selectInterview(IInterview interview) {
		selected = interview;
		return "interview.xhtml?faces-redirect=true";
	}
	
	public Collection<IWorker> getAllInterviewers() {
		return workerService.findAllInterviewers();
	}
	
	public void addInterviewer() {
		if (interviewer == null) return;
		if (selectedInterviewers == null) selectedInterviewers = new HashSet<>();
		selectedInterviewers.add(interviewer);
	}
	
	public void removeInterviewer(IWorker interviewer) {
		selectedInterviewers.remove(interviewer);
	}
	
	
	public void addInterview() {
		System.out.println("Add interview");
		System.out.println(getInterviewsByApplication());
		System.out.println(selectedInterviewers);
		if (selectedInterviewers == null || selectedInterviewers.isEmpty()) return;
		System.out.println("here");
		String[] hours = interviewTime.split("h");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(interviewDate);
		calendar.set(Calendar.HOUR, Integer.parseInt(hours[0]));
		calendar.set(Calendar.MINUTE, Integer.parseInt(hours[1]));
		
		interviewService.addInterview(applicationBean.getSelectedApplication(), calendar.getTime(), selectedInterviewers);
	
		selectedInterviewers.clear();
		interviewDate = null;
		interviewTime = null;
		System.out.println(applicationBean.getSelectedApplication().getInterviews());
		System.out.println(getInterviewsByApplication());
	}
	
	
	
	// helper methods
	
	private HttpSession getSession() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		return request.getSession();
	}

	
	// getters and setters
	
	public IWorker getInterviewer() {
		return interviewer;
	}


	public void setInterviewer(IWorker interviewer) {
		this.interviewer = interviewer;
	}


	public Date getInterviewDate() {
		return interviewDate;
	}


	public void setInterviewDate(Date interviewDate) {
		this.interviewDate = interviewDate;
	}


	public Date getTommorrow() {
		return tommorrow;
	}

	public List<IWorker> getSelectedInterviewers() {
		if (selectedInterviewers == null) return new ArrayList<IWorker>();
		
		List<IWorker> list = new ArrayList<>(selectedInterviewers);
		return list;
	}

	public String getInterviewTime() {
		return interviewTime;
	}

	public void setInterviewTime(String interviewTime) {
		this.interviewTime = interviewTime;
	}
}

