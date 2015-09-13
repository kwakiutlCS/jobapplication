package pt.uc.dei.aor.project.business.filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import pt.uc.dei.aor.project.business.exception.IllegalFilterParamException;
import pt.uc.dei.aor.project.business.model.IWorker;
import pt.uc.dei.aor.project.business.util.PositionState;

public class PositionFilter {
	
	private int code;
	private String title;
	private PositionState state;
	
	public PositionFilter() {
		code = -1;
		title = null;
		setState(PositionState.OPEN);
	}
	
	
	public void setCode(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}


	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}


	public PositionState getState() {
		return state;
	}


	public void setState(PositionState state) {
		this.state = state;
	}
}
