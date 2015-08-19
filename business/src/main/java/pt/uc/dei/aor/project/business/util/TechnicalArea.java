package pt.uc.dei.aor.project.business.util;

public enum TechnicalArea {
	SSPA("SSPA"), 
	DOT_NET_DEVELOPMENT(".NET Development"), 
	JAVA_DEVELOPMENT("JAVA Development"), 
	SAFETY_CRITICAL("Safety Critical"), 
	PROJECT_MANAGEMENT("Project Management"),
	INTEGRATION("Integration");
	
	private String technicalAreaLabel;
	
	private TechnicalArea(String technicalAreaLabel){
		this.technicalAreaLabel=technicalAreaLabel;
	}

	public String getTechnicalAreaLabel() {
		return technicalAreaLabel;
	}
	
	
}
