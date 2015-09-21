package pt.uc.dei.aor.project.presentation.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import pt.uc.dei.aor.project.business.model.IAnswer;
import pt.uc.dei.aor.project.business.model.IInterview;
import pt.uc.dei.aor.project.business.model.IScriptEntry;
import pt.uc.dei.aor.project.business.model.IWorker;
import pt.uc.dei.aor.project.business.service.IInterviewBusinessService;
import pt.uc.dei.aor.project.business.util.QuestionType;
import pt.uc.dei.aor.project.presentation.util.MetaUtils;

@Named
@ViewScoped
public class InterviewBean implements Serializable {
	
	private static final long serialVersionUID = -4041049634352023785L;
	
	private long selectedInterviewId;
	private IInterview selectedInterview;

	private List<IScriptEntry> scriptEntries;
	private IScriptEntry selectedEntry;
	
	private String answer;
	private Date answerDate;
	
	private List<IAnswer> answerList;
	private List<Boolean> answersGiven;
	
	private List<IInterview> historic;
	
	@Inject
	private IInterviewBusinessService interviewService;
	
	
	
	public void onload() {
		setSelectedInterview(interviewService.findInterviewById(selectedInterviewId));
		
		scriptEntries = findScriptEntries();
		
		System.out.println(scriptEntries);
		answerList = interviewService.findAnswersByInterview(selectedInterview);
	
		populateAnswersGiven();
		
		if (scriptEntries != null || !scriptEntries.isEmpty()) {
			selectedEntry = scriptEntries.get(0);
			getPreviousAnswer();
		}
	}
	
	public void changeQuestion(IScriptEntry entry) {
		selectedEntry = entry;
		getPreviousAnswer();
	}
	
	public void saveAnswer() {
		if (selectedEntry.getQuestionType() != QuestionType.DATE)
			interviewService.saveAnswer(selectedInterview, answer, selectedEntry);
		else {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			interviewService.saveAnswer(selectedInterview, sdf.format(answerDate).toString(), selectedEntry);
		}
		
		int index = scriptEntries.indexOf(selectedEntry);
		answersGiven.set(index, true);
		selectedEntry = nextQuestion();
		getPreviousAnswer();
	}
	
	
	
	private List<IScriptEntry> findScriptEntries() {
		return interviewService.getCompleteScriptEntries(selectedInterview);
	}
	
	private IScriptEntry nextQuestion() {
		int startIndex = scriptEntries.indexOf(selectedEntry);
		int currentIndex = startIndex+1;
		while (true) {
			if (currentIndex == scriptEntries.size()) currentIndex = 0;
			if (!isAnswered(currentIndex)) return scriptEntries.get(currentIndex);
			currentIndex++;
			if (startIndex == currentIndex) return scriptEntries.get(scriptEntries.size()-1);
		}
	}
	
	private void getPreviousAnswer() {
		if (selectedEntry.getQuestionType() != QuestionType.DATE) {
			answer = interviewService.findAnswerByInterviewAndQuestion(selectedInterview, 
					selectedEntry.getText());
		}
		else {
			String date = interviewService.findAnswerByInterviewAndQuestion(selectedInterview, 
					selectedEntry.getText());
			
			if (date == null) {
				answerDate = null;
				return;
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
			try {
				answerDate = sdf.parse(date);
			} catch (ParseException e) {
				answerDate = null;
			}
		}
	}
	
	
	private void populateAnswersGiven() {
		answersGiven = new ArrayList<>();
		for (IScriptEntry e : scriptEntries) {
			answersGiven.add(isAnswered(e));
		}
	}
	
	private boolean isAnswered(IScriptEntry entry) {
		for (IAnswer answer : answerList) {
			if (answer.getQuestion().equals(entry.getText())) return true;
		}
		return false;
	}
	
	public String getCvLink() {
		String app = ((HttpServletRequest) FacesContext.getCurrentInstance()
				.getExternalContext().getRequest()).getContextPath();
		
		String login = selectedInterview.getCandidate().getLogin();
		String cv = selectedInterview.getCandidate().getCv();
		return "https://localhost:8443"+app+"/interview/cv/"+login+"/"+cv;
	}
	
	public String getLetterLink() {
		String app = ((HttpServletRequest) FacesContext.getCurrentInstance()
				.getExternalContext().getRequest()).getContextPath();
		
		long id = selectedInterview.getApplication().getId();
		String letter = selectedInterview.getApplication().getLetter();
		return "https://localhost:8443"+app+"/interview/letter/"+id+"/"+letter;
	}
	
	public List<IInterview> getPreviousInterviews() {
		return interviewService.getPreviousInterviews(selectedInterview);
	}
	
	public List<IInterview> getHistoricInterviews() {
		if (historic == null) {
			historic = interviewService.getHistoricInterviews(selectedInterview);
		}
		
		return historic;
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

	public Date getAnswerDate() {
		return answerDate;
	}

	public void setAnswerDate(Date answerDate) {
		this.answerDate = answerDate;
	}
	
	public boolean isAnswered(int i) {
		return answersGiven.get(i);
	}
	
	public boolean isSelectedEntry(IScriptEntry entry) {
		return entry.equals(selectedEntry);
	}
	
	public boolean isFinalRemarks(IScriptEntry entry) {
		return entry.equals(scriptEntries.get(scriptEntries.size()-1));
	}
	
	public boolean isFinished() {
		return !answersGiven.contains(false);
	}
}

