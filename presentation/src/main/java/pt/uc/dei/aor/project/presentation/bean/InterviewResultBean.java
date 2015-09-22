package pt.uc.dei.aor.project.presentation.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import pt.uc.dei.aor.project.business.model.IAnswer;
import pt.uc.dei.aor.project.business.model.IInterview;
import pt.uc.dei.aor.project.business.service.IInterviewBusinessService;
import pt.uc.dei.aor.project.presentation.util.MetaUtils;

@Named
@ViewScoped
public class InterviewResultBean implements Serializable {
	
	private static final long serialVersionUID = -4041049634352023785L;
	
	private long selectedInterviewId;
	private IInterview selectedInterview;
	private List<IAnswer> answerList;
	private String areas;
	private String interviewers;
	private String title;
	private String date;

	@Inject
	private IInterviewBusinessService interviewService;
	
	
	
	public void onload() {
		selectedInterview = interviewService.findInterviewById(selectedInterviewId);
		
		setAnswerList(interviewService.findAnswersByInterview(selectedInterview));
		title = selectedInterview.getApplication().getPosition().getTitle();
		setAreas(selectedInterview.getApplication().getPosition().getTechnicalAreasFormatted());
		date = selectedInterview.getDate();
		setInterviewers(selectedInterview.getInterviewersFormatted());
	}



	public List<IAnswer> getAnswerList() {
		return answerList;
	}



	public void setAnswerList(List<IAnswer> answerList) {
		this.answerList = answerList;
	}



	public long getSelectedInterviewId() {
		return selectedInterviewId;
	}



	public void setSelectedInterviewId(long selectedInterviewId) {
		this.selectedInterviewId = selectedInterviewId;
	}



	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}

	public String getAreas() {
		return areas;
	}


	public void setAreas(String areas) {
		this.areas = areas;
	}


	public String getInterviewers() {
		return interviewers;
	}


	public void setInterviewers(String interviewers) {
		this.interviewers = interviewers;
	}

}

