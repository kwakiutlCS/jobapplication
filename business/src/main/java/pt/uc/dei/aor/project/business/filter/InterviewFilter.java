package pt.uc.dei.aor.project.business.filter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import pt.uc.dei.aor.project.business.exception.IllegalFilterParamException;
import pt.uc.dei.aor.project.business.model.IUser;

public class InterviewFilter extends GenericFilter {
	
	private List<Set<IUser>> interviewerSets;
	private Set<String> positions;
	private String candidate;
	private Date startDate;
	private Date endDate;
	
	public InterviewFilter() {
		interviewerSets = new ArrayList<>();
		positions = new HashSet<>();
		candidate = null;
		startDate = null;
		endDate = null;
	}
	
	
	public void addInterviewerSet(IUser interviewer) {
		addGenericSet(interviewerSets, interviewer);
	}

	public void deleteInterviewer(int setIndex, int pos) throws IllegalFilterParamException {
		deleteGenericElement(interviewerSets, setIndex, pos);
	}
	
	
	public void mergeInterviewers(int setPos) throws IllegalFilterParamException {
		mergeElements(interviewerSets, setPos);
	}
	
	public void splitInterviewers(int setPos, int pos) throws IllegalFilterParamException {
		splitElements(interviewerSets, setPos, pos);
	}
	
	public List<List<IUser>> getInterviewerSets() {
		return getGenericSets(interviewerSets);
	}

	

	public void addPosition(String filterPosition) {
		positions.add(filterPosition);
	}

	public List<String> getPositions() {
		return new ArrayList<>(positions);
	}


	public void deletePosition(int index) {
		positions.remove(getPositions().get(index));
	}


	public String getCandidate() {
		return candidate;
	}
	
	public void addCandidate(String candidate) {
		this.candidate = candidate;
	}
	
	public void deleteCandidate() {
		candidate = null;
	}
	
	
	public Date getStartDate() {
		return startDate;
	}


	public void setStartDate(Date startDate) {
		this.startDate = prepareStartDate(startDate);
	}


	public Date getFinishDate() {
		return endDate;
	}


	public void setFinishDate(Date finishDate) {
		this.endDate = prepareFinishDate(finishDate);
	}
}
