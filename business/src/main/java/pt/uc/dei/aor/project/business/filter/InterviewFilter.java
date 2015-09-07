package pt.uc.dei.aor.project.business.filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import pt.uc.dei.aor.project.business.exception.IllegalFilterParamException;
import pt.uc.dei.aor.project.business.model.IWorker;

public class InterviewFilter {
	
	private List<Set<IWorker>> interviewerSets;
	private Set<String> positions;
	
	public InterviewFilter() {
		interviewerSets = new ArrayList<>();
		positions = new HashSet<>();
	}
	
	
	public void addInterviewerSet(IWorker interviewer) {
		interviewerSets.add(new LinkedHashSet<IWorker>(Arrays.asList(new IWorker[]{interviewer})));
	}

	public void deleteInterviewer(int setIndex, int pos) throws IllegalFilterParamException {
		if (setIndex >= interviewerSets.size()) throw new IllegalFilterParamException();
		if (pos >= interviewerSets.get(setIndex).size()) throw new IllegalFilterParamException();
		
		Set<IWorker> set = interviewerSets.get(setIndex);
		
		if (set.size() == 1) interviewerSets.remove(setIndex);
		else {
			int counter = 0;
			for (IWorker w : set) {
				if (counter++ == pos) {
					set.remove(w);
					break;
				}
			}
		}
	}
	
	
	public void mergeInterviewers(int setPos) throws IllegalFilterParamException {
		if (setPos >= interviewerSets.size()-1) throw new IllegalFilterParamException();
		
		interviewerSets.get(setPos).addAll(interviewerSets.get(setPos+1));
		interviewerSets.remove(setPos+1);
	}
	
	public void splitInterviewers(int setPos, int pos) throws IllegalFilterParamException {
		if (setPos >= interviewerSets.size()) throw new IllegalFilterParamException();
		if (pos >= interviewerSets.get(setPos).size()-1) throw new IllegalFilterParamException();
		
		Set<IWorker> newSet = new LinkedHashSet<>();
		Set<IWorker> oldSet = interviewerSets.get(setPos);
		
		int counter = 0;
		for (IWorker w : oldSet) {
			if (counter++ > pos) newSet.add(w);
		}
		
		oldSet.removeAll(newSet);
		
		interviewerSets.add(setPos+1, newSet);
	}
	
	
	
	
	public List<List<IWorker>> getInterviewerSets() {
		List<List<IWorker>> sets = new ArrayList<>();
		for (Set<IWorker> set : interviewerSets) {
			List<IWorker> workers = new ArrayList<>();
			
			for (IWorker w : set) {
				workers.add(w);
			}
			
			sets.add(workers);
		}
		
		return sets;
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
	
}
