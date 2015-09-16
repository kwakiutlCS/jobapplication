package pt.uc.dei.aor.project.business.filter;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import pt.uc.dei.aor.project.business.exception.IllegalFilterParamException;

public class GenericFilter {

	
	// and or section
	
	public <T> void addGenericSet(List<Set<T>> sets, T element) {
		for (Set<T> set : sets) {
			if (set.size() == 1 && set.contains(element)) return;
		}
		
		Set<T> newSet = new LinkedHashSet<>();
		newSet.add(element);
		
		sets.add(newSet);
	}
	
	
	public <T> void deleteGenericElement(List<Set<T>> sets, int setIndex, int pos) 
			throws IllegalFilterParamException {
		if (setIndex >= sets.size()) throw new IllegalFilterParamException();
		if (pos >= sets.get(setIndex).size()) throw new IllegalFilterParamException();
		
		Set<T> set = sets.get(setIndex);
		
		if (set.size() == 1) sets.remove(setIndex);
		else {
			int counter = 0;
			for (T w : set) {
				if (counter++ == pos) {
					set.remove(w);
					break;
				}
			}
		}
	}
	
	
	public <T> void mergeElements(List<Set<T>> sets, int setPos) throws IllegalFilterParamException {
		if (setPos >= sets.size()-1) throw new IllegalFilterParamException();
		
		sets.get(setPos).addAll(sets.get(setPos+1));
		sets.remove(setPos+1);
	}
	
	
	public <T> void splitElements(List<Set<T>> sets, int setPos, int pos) throws IllegalFilterParamException {
		if (setPos >= sets.size()) throw new IllegalFilterParamException();
		if (pos >= sets.get(setPos).size()-1) throw new IllegalFilterParamException();
		
		Set<T> newSet = new LinkedHashSet<>();
		Set<T> oldSet = sets.get(setPos);
		
		int counter = 0;
		for (T w : oldSet) {
			if (counter++ > pos) newSet.add(w);
		}
		
		oldSet.removeAll(newSet);
		
		sets.add(setPos+1, newSet);
	}
	
	
	public <T> List<List<T>> getGenericSets(List<Set<T>> genericSets) {
		List<List<T>> sets = new ArrayList<>();
		for (Set<T> set : genericSets) {
			List<T> list = new ArrayList<>();
			
			for (T w : set) {
				list.add(w);
			}
			
			sets.add(list);
		}
		
		return sets;
	}
	
	
	// or section
}
