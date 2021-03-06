package pt.uc.dei.aor.project.business.filter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import pt.uc.dei.aor.project.business.exception.IllegalFilterParamException;
import pt.uc.dei.aor.project.business.util.Localization;
import pt.uc.dei.aor.project.business.util.PositionState;
import pt.uc.dei.aor.project.business.util.TechnicalArea;

public class PositionFilter extends GenericFilter {
	
	private int code;
	private Set<String> titles;
	private PositionState state;
	private List<Set<Localization>> localizationSets;
	private List<Set<TechnicalArea>> areaSets;
	private String company;
	private Date startDate;
	private Date finishDate;
	private Set<String> keywords;
	
	public PositionFilter() {
		code = -1;
		titles = new HashSet<>();
		setState(PositionState.OPEN);
		areaSets = new ArrayList<>();
		localizationSets = new ArrayList<>();
		setCompany(null);
		startDate = null;
		finishDate = null;
		keywords = new HashSet<>();
	}
	
	
	// and or section
	
	public void addAreaSet(TechnicalArea area) {
		System.out.println(area);
		addGenericSet(areaSets, area);
		System.out.println(areaSets);
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
	
	public void addTitle(String title) {
		titles.add(title);
	}

	public List<String> getTitles() {
		return new ArrayList<>(titles);
	}


	public void deleteTitle(int index) {
		titles.remove(getTitles().get(index));
	}
	
	public void addKeyword(String keyword) {
		keywords.add(keyword);
	}

	public List<String> getKeywords() {
		return new ArrayList<>(keywords);
	}


	public void deleteKeyword(int index) {
		keywords.remove(getKeywords().get(index));
	}
	
	
	// getters and setters
	
	public void setCode(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return code;
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
		this.startDate = prepareStartDate(startDate);
	}


	public Date getFinishDate() {
		return finishDate;
	}


	public void setFinishDate(Date finishDate) {
		this.finishDate = prepareFinishDate(finishDate);
	}

	
}
