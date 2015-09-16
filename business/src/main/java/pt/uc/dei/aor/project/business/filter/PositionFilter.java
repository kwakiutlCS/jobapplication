package pt.uc.dei.aor.project.business.filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import pt.uc.dei.aor.project.business.exception.IllegalFilterParamException;
import pt.uc.dei.aor.project.business.model.IWorker;
import pt.uc.dei.aor.project.business.util.Localization;
import pt.uc.dei.aor.project.business.util.PositionState;
import pt.uc.dei.aor.project.business.util.TechnicalArea;

public class PositionFilter extends GenericFilter {
	
	private int code;
	private String title;
	private PositionState state;
	private List<Set<Localization>> localizationSets;
	private List<Set<TechnicalArea>> areaSets;
	private String company;
	private Date startDate;
	private Date finishDate;
	private String keyword;
	private Localization localization = null;
	
	public PositionFilter() {
		code = -1;
		title = null;
		setState(PositionState.OPEN);
		areaSets = new ArrayList<>();
		localizationSets = new ArrayList<>();
		setCompany(null);
		startDate = null;
		finishDate = null;
		keyword = null;
	}
	
	
	// and or section
	
	public void addAreaSet(TechnicalArea area) {
		addGenericSet(areaSets, area);
	}

	public void deleteArea(int setIndex, int pos) throws IllegalFilterParamException {
		deleteGenericElement(areaSets, setIndex, pos);
	}
	
	
	public void mergeAreas(int setPos) throws IllegalFilterParamException {
		mergeElements(areaSets, setPos);
	}
	
	public void splitAreas(int setPos, int pos) throws IllegalFilterParamException {
		splitElements(areaSets, setPos, pos);
	}
	
	
	public List<List<TechnicalArea>> getAreaSets() {
		return getGenericSets(areaSets);
	}
	
	
	public void addLocalizationSet(Localization localization) {
		addGenericSet(localizationSets, localization);
	}

	public void deleteLocalization(int setIndex, int pos) throws IllegalFilterParamException {
		deleteGenericElement(localizationSets, setIndex, pos);
	}
	
	
	public void mergeLocalizations(int setPos) throws IllegalFilterParamException {
		mergeElements(localizationSets, setPos);
	}
	
	public void splitLocalizations(int setPos, int pos) throws IllegalFilterParamException {
		splitElements(localizationSets, setPos, pos);
	}
	
	public List<List<Localization>> getLocalizationSets() {
		return getGenericSets(localizationSets);
	}
	
	
	
	// or section
	
	
	
	
	// getters and setters
	
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


	public String getCompany() {
		return company;
	}


	public void setCompany(String company) {
		this.company = company;
	}


	public Date getStartDate() {
		return startDate;
	}


	public void setStartDate(Date startDate) {
		if (startDate != null) {
			Calendar date = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
			date.setTime(startDate);
			date.add(Calendar.HOUR, -1);
			this.startDate = date.getTime();
		}
		else {
			this.startDate = null;
		}
	}


	public Date getFinishDate() {
		return finishDate;
	}


	public void setFinishDate(Date finishDate) {
		if (finishDate != null) {
			Calendar date = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
			date.setTime(finishDate);
			date.set(Calendar.HOUR, 22);
			date.set(Calendar.MINUTE, 59);
			date.set(Calendar.SECOND, 59);
			this.finishDate = date.getTime();
		}
		else {
			this.finishDate = null;
		}
	}


	public String getKeyword() {
		return keyword;
	}


	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Localization getLocalization() {
		return localization;
	}

	public void setLocalization(Localization localization) {
		this.localization = localization;
	}
}
