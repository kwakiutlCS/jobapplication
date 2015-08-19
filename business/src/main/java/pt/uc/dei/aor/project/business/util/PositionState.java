package pt.uc.dei.aor.project.business.util;

public enum PositionState {
	OPEN("Open"), 
	CLOSED("Closed"), 
	ON_HOLD("On Hold");
	
	
	private String positionStateLabel;	
		
	private PositionState(String positionStateLabel){
		this.positionStateLabel = positionStateLabel; 
	}
	
	public String getPositionStateLabel(){
		return positionStateLabel;
	}
	
	
}
