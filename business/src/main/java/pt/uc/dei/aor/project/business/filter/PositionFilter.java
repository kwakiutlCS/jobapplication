package pt.uc.dei.aor.project.business.filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import pt.uc.dei.aor.project.business.exception.IllegalFilterParamException;
import pt.uc.dei.aor.project.business.model.IWorker;
import pt.uc.dei.aor.project.business.util.Localization;
import pt.uc.dei.aor.project.business.util.PositionState;
import pt.uc.dei.aor.project.business.util.TechnicalArea;

public class PositionFilter {
	
	private int code;
	private String title;
	private PositionState state;
	private Localization localization;
	private TechnicalArea area;
	private String company;
	
	public PositionFilter() {
		code = -1;
		title = null;
		setState(PositionState.OPEN);
		localization = null;
		setCompany(null);
	}
	
	
	public Localization getLocalization() {
		return localization;
	}


	public void setLocalization(Localization localization) {
		this.localization = localization;
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


	public TechnicalArea getArea() {
		return area;
	}


	public void setArea(TechnicalArea area) {
		this.area = area;
	}


	public String getCompany() {
		return company;
	}


	public void setCompany(String company) {
		this.company = company;
	}
}
