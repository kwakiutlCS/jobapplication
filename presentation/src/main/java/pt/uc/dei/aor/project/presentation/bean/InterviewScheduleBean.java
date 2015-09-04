package pt.uc.dei.aor.project.presentation.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import pt.uc.dei.aor.project.business.model.IApplication;
import pt.uc.dei.aor.project.business.model.IInterview;
import pt.uc.dei.aor.project.business.model.IWorker;
import pt.uc.dei.aor.project.business.service.IApplicationBusinessService;
import pt.uc.dei.aor.project.business.service.IInterviewBusinessService;
import pt.uc.dei.aor.project.business.service.IWorkerBusinessService;


@Named
@ViewScoped
public class InterviewScheduleBean implements Serializable {
	
	private static final long serialVersionUID = -4484148866779896144L;

	@Inject
	private IInterviewBusinessService interviewService;
	
	@Inject
	private IApplicationBusinessService applicationService;
	
	@Inject
	private IWorkerBusinessService workerService;
	
	
	private IApplication selectedApplication;
	private long selectedApplicationId;
		
	private Collection<IWorker> selectedInterviewers;
	private IWorker interviewer;
	private Date interviewDate;
	private String interviewTime;
	private Date tommorrow;
	
	
	public InterviewScheduleBean() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_YEAR, 1);
		tommorrow = c.getTime();
	}
	
	public Collection<IInterview> getActiveInterviews() {
		IWorker user = (IWorker) getSession().getAttribute("user");
		return interviewService.findActiveInterviewsByUser(user);
	}
	
	public List<IInterview> getInterviewsByApplication() {
		return selectedApplication.getInterviews();
		//return interviewService.findInterviewsByApplication(applicationBean.getSelectedApplication());
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
		if (selectedInterviewers == null || selectedInterviewers.isEmpty()) {
			setMsg("At least one interviewer must be selected", FacesMessage.SEVERITY_ERROR);
			return;
		}
		
		String[] hours = interviewTime.split("h");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(interviewDate);
		calendar.set(Calendar.HOUR, Integer.parseInt(hours[0]));
		calendar.set(Calendar.MINUTE, Integer.parseInt(hours[1]));
		
		selectedApplication = interviewService.addInterview(selectedApplication, calendar.getTime(), selectedInterviewers);
		setMsg("Interview scheduled", FacesMessage.SEVERITY_INFO);
		System.out.println("updated interviews: "+selectedApplication.getInterviews());
		selectedInterviewers.clear();
		interviewDate = null;
		interviewTime = null;
	}
	
	
	public void onload() {
		selectedApplication = applicationService.findApplicationById(selectedApplicationId);
	}
	
	// helper methods
	
	private HttpSession getSession() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		return request.getSession();
	}

	private void setMsg(String text, Severity severity) {
		FacesMessage msg = new FacesMessage(severity,
				text, text);
		FacesContext.getCurrentInstance().addMessage(null, msg);
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

	public IApplication getSelectedApplication() {
		return selectedApplication;
	}

	public void setSelectedApplication(IApplication selectedApplication) {
		this.selectedApplication = selectedApplication;
	}

	public long getSelectedApplicationId() {
		return selectedApplicationId;
	}

	public void setSelectedApplicationId(long selectedApplicationId) {
		this.selectedApplicationId = selectedApplicationId;
	}
	
	
}

