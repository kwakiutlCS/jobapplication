package pt.uc.dei.aor.project.business.filter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import pt.uc.dei.aor.project.business.exception.IllegalFilterParamException;
import pt.uc.dei.aor.project.business.model.IWorker;

public class InterviewFilter extends GenericFilter {
	
	private List<Set<IWorker>> interviewerSets;
	private Set<String> positions;
	private String candidate;
	
	public InterviewFilter() {
		interviewerSets = new ArrayList<>();
		positions = new HashSet<>();
		candidate = null;
	}
	
	
	public void addInterviewerSet(IWorker interviewer) {
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
	
	public List<List<IWorker>> getInterviewerSets() {
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
}
