package pt.uc.dei.aor.project.business.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import pt.uc.dei.aor.project.business.exception.IllegalFilterParamException;
import pt.uc.dei.aor.project.business.util.Role;

public class WorkerFilter extends GenericFilter {
	
	private String keyword;
	private List<Set<Role>> roles;
	
	
	public WorkerFilter() {
		keyword = null;
		roles = new ArrayList<>();
	}
	
	
	public void setKeyword(String filter) {
		this.keyword = filter;
	}
	
	public String getKeyword() {
		return keyword;
	}
	
	public void addRole(Role role) {
		addGenericSet(roles, role);
	}
	
	public void removeRole(int setIndex, int pos) throws IllegalFilterParamException {
		deleteGenericElement(roles, setIndex, pos);
	}


	public List<List<Role>> getRoleSets() {
		return getGenericSets(roles);
	}


	public void splitRoles(int index, int pos) throws IllegalFilterParamException {
		splitElements(roles, index, pos);
	}


	public void mergeRoles(int index) throws IllegalFilterParamException {
		mergeElements(roles, index);
	}

}
