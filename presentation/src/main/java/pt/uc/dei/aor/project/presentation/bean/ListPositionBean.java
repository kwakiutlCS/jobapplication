package pt.uc.dei.aor.project.presentation.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import pt.uc.dei.aor.project.business.filter.PositionFilter;
import pt.uc.dei.aor.project.business.model.IPosition;
import pt.uc.dei.aor.project.business.service.IPositionBusinessService;
import pt.uc.dei.aor.project.business.util.Localization;
import pt.uc.dei.aor.project.business.util.PositionState;
import pt.uc.dei.aor.project.business.util.TechnicalArea;


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
	
	public void addLocalization() {
		filter.setLocalization(localization);
	}
	
	public void removeLocalization() {
		filter.setLocalization(null);
	}
	
	public void addArea() {
		filter.setArea(area);
	}
	
	public void removeArea() {
		filter.setArea(null);
	}
	
	public void addCompany() {
		filter.setCompany(company);
	}
	
	public void removeCompany() {
		filter.setCompany(null);
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



}
