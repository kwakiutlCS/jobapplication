package pt.uc.dei.aor.project.presentation.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import pt.uc.dei.aor.project.business.exception.IllegalFilterParamException;
import pt.uc.dei.aor.project.business.filter.PositionFilter;
import pt.uc.dei.aor.project.business.model.IPosition;
import pt.uc.dei.aor.project.business.service.IPositionBusinessService;
import pt.uc.dei.aor.project.business.util.Localization;
import pt.uc.dei.aor.project.business.util.PositionState;
import pt.uc.dei.aor.project.business.util.TechnicalArea;
import pt.uc.dei.aor.project.presentation.util.MetaUtils;


@Named
@ViewScoped
public class ListPositionBean implements Serializable{

	
	private static final long serialVersionUID = 1L;
	

	@Inject
	private IPositionBusinessService positionEjb;
	
	private IPosition selectedPosition;
	private int offset;
	private PositionFilter filter;
	
	// filter params
	private int code;
	private String title;
	private PositionState state;
	private Localization localization;
	private TechnicalArea area;
	private String company;
	private Date startDate;
	private Date finishDate;
	private String keyword;
	
	@PostConstruct
	public void init() {
		offset = 0;
		code = 1;
		filter = new PositionFilter();
	}
	
	
	public List<IPosition> getPositions() {
		return positionEjb.findFilteredPositions(offset, 10, filter);
	}

	
	
	// filter functions
	
	public void addAreaToFilter() {
		filter.addAreaSet(area);
	}
	
	public void addLocalizationToFilter() {
		filter.addLocalizationSet(localization);
	}
	
	public void addCode() {
		filter.setCode(code);
	}
	
	public void removeCode() {
		filter.setCode(-1);
	}

	public void addTitle() {
		filter.setTitle(title);
	}
	
	public void removeTitle() {
		filter.setTitle(null);
	}
	
	public void addState() {
		filter.setState(state);
	}
	
	public void removeState() {
		filter.setState(null);
	}
	
	public void addCompany() {
		filter.setCompany(company);
	}
	
	public void removeCompany() {
		filter.setCompany(null);
	}
	
	public void removeStartDate() {
		startDate = null;
		filter.setStartDate(null);
	}
	
	public void removeFinishDate() {
		finishDate = null;
		filter.setFinishDate(null);
	}
	
	public void addDateFilter() {
		filter.setStartDate(startDate);
		filter.setFinishDate(finishDate);
	}
	
	public void addKeyword() {
		filter.setKeyword(keyword);
	}
	
	public void removeKeyword() {
		filter.setKeyword(null);
	}
	
	
	public IPosition getSelectedPosition() {
		return selectedPosition;
	}


	public void setSelectedPosition(IPosition selectedPosition) {
		this.selectedPosition = selectedPosition;
	}
	
	public void updatePosition(IPosition selectedposition){
		positionEjb.updatePosition(selectedposition);
	}
	
	
	public void mergeAreas(int setPos) {
		try {
			filter.mergeAreas(setPos);
		} catch (IllegalFilterParamException e) {
			MetaUtils.setMsg("todo", FacesMessage.SEVERITY_ERROR);
		}
	}
	
	public void splitAreas(int setPos, int pos) {
		try {
			filter.splitAreas(setPos, pos);
		} catch (IllegalFilterParamException e) {
			MetaUtils.setMsg("todo", FacesMessage.SEVERITY_ERROR);
		}
	}
	
	public void deleteArea(int setPos, int pos) {
		try {
			filter.deleteArea(setPos, pos);
		} catch (IllegalFilterParamException e) {
			MetaUtils.setMsg("todo", FacesMessage.SEVERITY_ERROR);
		}
	}
	
	public void mergeLocalizations(int setPos) {
		try {
			filter.mergeLocalizations(setPos);
		} catch (IllegalFilterParamException e) {
			MetaUtils.setMsg("todo", FacesMessage.SEVERITY_ERROR);
		}
	}
	
	public void splitLocalizations(int setPos, int pos) {
		try {
			filter.splitLocalizations(setPos, pos);
		} catch (IllegalFilterParamException e) {
			MetaUtils.setMsg("todo", FacesMessage.SEVERITY_ERROR);
		}
	}
	
	public void deleteLocalization(int setPos, int pos) {
		try {
			filter.deleteLocalization(setPos, pos);
		} catch (IllegalFilterParamException e) {
			MetaUtils.setMsg("todo", FacesMessage.SEVERITY_ERROR);
		}
	}
	
	
	// getters and setters
	public int getCode() {
		return code;
	}


	public void setCode(int code) {
		this.code = code;
	}


	public PositionFilter getFilter() {
		return filter;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public PositionState getState() {
		return state;
	}


	public void setState(PositionState state) {
		this.state = state;
	}


	public Localization getLocalization() {
		return localization;
	}


	public void setLocalization(Localization localization) {
		this.localization = localization;
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


	public Date getStartDate() {
		return startDate;
	}


	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}


	public Date getFinishDate() {
		return finishDate;
	}


	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}


	public String getKeyword() {
		return keyword;
	}


	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}


	public List<List<TechnicalArea>> getAreaSet() {
		return filter.getAreaSets();
	}
	
	public List<List<Localization>> getLocalizationSet() {
		return filter.getLocalizationSets();
	}
}
