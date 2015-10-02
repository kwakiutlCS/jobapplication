package pt.uc.dei.aor.project.presentation_public.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import pt.uc.dei.aor.project.business.model.IPosition;
import pt.uc.dei.aor.project.business.service.IPositionBusinessService;


@Named
@ViewScoped
public class ListPositionBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private IPositionBusinessService position;
	
	private List<IPosition> filteredPositions;
	private IPosition selectedPosition;
	private long selectedPositionId;
	

	public void findPosition(){
		selectedPosition =  position.findPositionById(selectedPositionId);
	}
	
	
	public List<IPosition> getPositions() {
			
		return position.getIPositions();
	}


	public List<IPosition> getFilteredPositions() {
		return filteredPositions;
	}

	
	public void setFilteredPositions(List<IPosition> filteredPositions) {
		this.filteredPositions = filteredPositions;
	}


	public IPosition getSelectedPosition() {
		return selectedPosition;
	}

	
	public void setSelectedPosition(IPosition selectedPosition) {
		this.selectedPosition = selectedPosition;
	}


	public long getSelectedPositionId() {
		return selectedPositionId;
	}


	public void setSelectedPositionId(long selectedPositionId) {
		this.selectedPositionId = selectedPositionId;
	}
	
	public String contactPersonName(){
		
		return selectedPosition.getContactPerson().getFullName();
		
	}

	public int getOpenings() {
		return position.findOpenPosition().size();
	}
}
