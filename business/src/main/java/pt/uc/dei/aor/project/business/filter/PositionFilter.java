package pt.uc.dei.aor.project.business.filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import pt.uc.dei.aor.project.business.exception.IllegalFilterParamException;
import pt.uc.dei.aor.project.business.model.IWorker;

public class PositionFilter {
	
	private int code;
	
	public PositionFilter() {
		code = -1;
	}
	
	
	public void setCode(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
}
