package pt.uc.dei.aor.project.presentation.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.uc.dei.aor.project.business.exception.AllPhasesCompletedException;
import pt.uc.dei.aor.project.business.exception.GenericIllegalParamsException;
import pt.uc.dei.aor.project.business.exception.IllegalApplicationException;
import pt.uc.dei.aor.project.business.exception.IllegalInterviewDeletionException;
import pt.uc.dei.aor.project.business.exception.RepeatedInterviewException;
import pt.uc.dei.aor.project.business.model.IApplication;
import pt.uc.dei.aor.project.business.model.IInterview;
import pt.uc.dei.aor.project.business.model.IPosition;
import pt.uc.dei.aor.project.business.model.IUser;
import pt.uc.dei.aor.project.business.service.IApplicationBusinessService;
import pt.uc.dei.aor.project.business.service.IInterviewBusinessService;
import pt.uc.dei.aor.project.business.service.IUserBusinessService;
import pt.uc.dei.aor.project.business.service.IUserBusinessService;
import pt.uc.dei.aor.project.presentation.util.MetaUtils;


@Named
@ViewScoped
public class InterviewScheduleBean implements Serializable {
	
	private static final long serialVersionUID = -4484148866779896144L;

	private static final Logger logger = LoggerFactory.getLogger(InterviewScheduleBean.class);
	
	@Inject
	private IInterviewBusinessService interviewService;
	
	@Inject
	private IApplicationBusinessService applicationService;
	
	@Inject
	private IUserBusinessService userService;
	
	
	private IApplication selectedApplication;
	private long selectedApplicationId;
		
	private Set<IUser> selectedInterviewers;
	private IUser interviewer;
	private Date interviewDate;
	private String interviewTime;
	private Date tommorrow;
	
	private IInterview editing = null;

	private IPosition selectedPosition;

	private IUser selectedCandidate;
	
	private IPosition positionToAdd;
	
	private boolean spontaneous = false;
	
	public InterviewScheduleBean() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_YEAR, 1);
		tommorrow = c.getTime();
	}
	
	public Collection<IInterview> getActiveInterviews() {
		IUser user = MetaUtils.getUser();
		return interviewService.findActiveInterviewsByUser(user);
	}
	
	public List<IInterview> getInterviewsByApplication() {
		return interviewService.findInterviewsByApplication(selectedApplication);
	}
	
	
	
	public Collection<IUser> getAllInterviewers() {
		return userService.findAllInterviewers();
	}
	
	public void addInterviewer() {
		if (interviewer == null) return;
		if (selectedInterviewers == null) selectedInterviewers = new HashSet<>();
		selectedInterviewers.add(interviewer);
	}
	
	public void removeInterviewer(IUser interviewer) {
		selectedInterviewers.remove(interviewer);
	}
	
	
	public void addInterview() {
		if (selectedInterviewers == null || selectedInterviewers.isEmpty()) {
			MetaUtils.setMsg("At least one interviewer must be selected", FacesMessage.SEVERITY_ERROR);
			return;
		}
		
		String[] hours = interviewTime.split("h");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(interviewDate);
		calendar.set(Calendar.HOUR, Integer.parseInt(hours[0]));
		calendar.set(Calendar.MINUTE, Integer.parseInt(hours[1]));
		
		try {
			selectedApplication = interviewService.addInterview(selectedApplication, calendar.getTime(), selectedInterviewers);
			MetaUtils.setMsg("Interview scheduled", FacesMessage.SEVERITY_INFO);
			selectedApplication = applicationService.changeAnalyzed(selectedApplication, true);
			
		} catch (GenericIllegalParamsException | RepeatedInterviewException e) {
			MetaUtils.setMsg("Error scheduling interview", FacesMessage.SEVERITY_ERROR);
		} catch (AllPhasesCompletedException e) {
			MetaUtils.setMsg("Application reached process' final phase. Add another script to position", 
					FacesMessage.SEVERITY_ERROR);
		}
		
		selectedInterviewers.clear();
		
		interviewDate = null;
		interviewTime = null;
	}
	
	public void delete(IInterview interview) {
		try {
			selectedApplication = interviewService.delete(selectedApplication, interview);
			MetaUtils.setMsg("Interview deleted", FacesMessage.SEVERITY_INFO);
			applicationService.changeAnalyzed(selectedApplication, false);
		} catch (IllegalInterviewDeletionException e) {
			MetaUtils.setMsg("Interview already started", FacesMessage.SEVERITY_ERROR);
		}
	}
	
	public void edit(IInterview interview) {
		
		if (interview.equals(editing)) {
			editing = null;
			selectedInterviewers = new HashSet<>();
			interviewDate = null;
			interviewTime = null;
		}
		else {
			editing = interview;
			selectedInterviewers = new HashSet<>(interview.getInterviewers());
			interviewDate = interview.getDateObject();
			Calendar cal = Calendar.getInstance();
			cal.setTime(interviewDate);
			
			int h = cal.get(Calendar.HOUR_OF_DAY);
			String hour = "";
			if (h < 10) hour = "0";
			hour += h;
			
			int m = cal.get(Calendar.MINUTE);
			String min = "";
			if (m < 10) min = "0";
			min += m;
			
			interviewTime = hour+"h"+min;
		}
	}
	
	
	public void onload() {
		selectedApplication = applicationService.findApplicationById(selectedApplicationId);
		IUser user = MetaUtils.getUser();
		
		if (!user.isAdmin() && !selectedApplication.getPosition().getContactPerson().equals(user))
			selectedApplication = null;
		
		setSelectedPosition(selectedApplication.getPosition());
		if (selectedPosition.getTitle() == null) spontaneous = true;
		setSelectedCandidate(selectedApplication.getCandidate());
	}
	
	
	public void addPositionToApplication() {
		try {
			 selectedApplication = applicationService.addPositionToApplication(positionToAdd, selectedApplication);
			 spontaneous = false;
			 selectedPosition = selectedApplication.getPosition();
		} catch (IllegalApplicationException e) {
			MetaUtils.setMsg("Not possible to add a position to that application", 
					FacesMessage.SEVERITY_ERROR);
		}
		
	}

	public void saveInterview(IInterview interview) {
		String[] hours = interviewTime.split("h");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(interviewDate);
		calendar.set(Calendar.HOUR, Integer.parseInt(hours[0]));
		calendar.set(Calendar.MINUTE, Integer.parseInt(hours[1]));
		
		interview.setDate(calendar.getTime());
		
		interviewService.setInterviewers(interview, selectedInterviewers);
		
		try {
			interviewService.saveInterview(interview);
			applicationService.changeAnalyzed(selectedApplication, true);
		} catch (IllegalInterviewDeletionException e) {
			MetaUtils.setMsg("Interview already started", FacesMessage.SEVERITY_ERROR);
		}
		editing = null;
		interviewDate = null;
		interviewTime = null;
		selectedInterviewers = null;
		
	}
	
	public void cancelAlterations() {
		editing = null;
		interviewDate = null;
		interviewTime = null;
		selectedInterviewers = null;
	}
		
	public void changeAnalyzed() {
		applicationService.changeAnalyzed(selectedApplication, !selectedApplication.getAnalyzed());
	}
	
	public List<IInterview> getPastInterviews() {
		try {
			return new ArrayList<>(interviewService.findPastInterviews(selectedApplication));
		} catch (AllPhasesCompletedException e) {
			MetaUtils.setMsg("An error occurred with the interviews of this application", FacesMessage.SEVERITY_FATAL);
			logger.error("Illegal interview scheduling");
			return new ArrayList<>();
		}
	}
	
	
	public List<IInterview> getFutureInterviews() {
		try {
			return new ArrayList<>(interviewService.findFutureInterviews(selectedApplication));
		} catch (AllPhasesCompletedException e) {
			MetaUtils.setMsg("An error occurred with the interviews of this application", FacesMessage.SEVERITY_FATAL);
			logger.error("Illegal interview scheduling");
			return new ArrayList<>();
		}
	}
	
	public boolean completed(IInterview interview) {
		try {
			return interviewService.isCompleted(interview);
		} catch (AllPhasesCompletedException e) {
			return false;
		}
	}
	
	public void refuseApplication() {
		applicationService.refuse(selectedApplication);
	}
	
	public boolean isWaitingInterview() {
		IInterview last = null;
		List<IInterview> interviews = selectedApplication.getInterviews();
		
		Calendar lastCal = Calendar.getInstance();
		Calendar otherCal = Calendar.getInstance();
		for (IInterview i : interviews) {
			if (last == null) {
				last = i;
				lastCal.setTime(last.getDateObject());
			}
			else {
				otherCal.setTime(i.getDateObject());
				if (otherCal.after(lastCal)) {
					last = i;
					lastCal.setTime(last.getDateObject());
				}
			}
		}
		
		if (last == null) return false;
		boolean result;
		try {
			result = !interviewService.isCompleted(last);
		} catch (AllPhasesCompletedException e) {
			return false;
		}
		return result;
	}
	
	public boolean isPreRefused() {
		if (!selectedApplication.getAnalyzed()) return false;
		if (selectedApplication.isRefused() ||
				selectedApplication.isAccepted() ||
				selectedApplication.isPropositionSent() ||
				selectedApplication.isRefusedByCandidate()) return false;
		
		if (isWaitingInterview()) return false;
		
		return true;
	}
	
	// getters and setters
	
	public IUser getInterviewer() {
		return interviewer;
	}


	public void setInterviewer(IUser interviewer) {
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

	public List<IUser> getSelectedInterviewers() {
		if (selectedInterviewers == null) return new ArrayList<IUser>();
		
		List<IUser> list = new ArrayList<>(selectedInterviewers);
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

	public IInterview getEditing() {
		return editing;
	}

	public void setEditing(IInterview editing) {
		this.editing = editing;
	}

	public IUser getSelectedCandidate() {
		return selectedCandidate;
	}

	public void setSelectedCandidate(IUser selectedCandidate) {
		this.selectedCandidate = selectedCandidate;
	}

	public IPosition getSelectedPosition() {
		return selectedPosition;
	}

	public void setSelectedPosition(IPosition selectedPosition) {
		this.selectedPosition = selectedPosition;
	}

	public IPosition getPositionToAdd() {
		return positionToAdd;
	}

	public void setPositionToAdd(IPosition positionToAdd) {
		this.positionToAdd = positionToAdd;
	}

	public boolean isSpontaneous() {
		return spontaneous;
	}

	public void setSpontaneous(boolean spontaneous) {
		this.spontaneous = spontaneous;
	}

	
	
}

