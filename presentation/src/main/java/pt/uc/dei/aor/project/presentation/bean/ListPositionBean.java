package pt.uc.dei.aor.project.presentation.bean;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import pt.uc.dei.aor.project.business.model.IPosition;
import pt.uc.dei.aor.project.business.service.IPositionBusinessService;


@Named
@SessionScoped
public class ListPositionBean implements Serializable{

	
	private static final long serialVersionUID = 1L;
	

	@Inject
	private IPositionBusinessService iposition;
	
	private List<IPosition> filteredPositions;
	
	
	
	
	public List<IPosition> getPositions() {
			
		return iposition.getIPositions();
	}


	public List<IPosition> getFilteredPositions() {
		return filteredPositions;
	}

	
	public void setFilteredPositions(List<IPosition> filteredPositions) {
		this.filteredPositions = filteredPositions;
	}

}
