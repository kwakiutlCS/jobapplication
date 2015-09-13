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
	
	
	
	@PostConstruct
	public void init() {
		offset = 0;
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



}
