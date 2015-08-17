package pt.uc.dei.aor.project.business.util;

	
	public enum Localization {
		COIMBRA("Coimbra"), 
		LISBOA("Lisboa"), 
		OPORTO("Oporto"), 
		CLIENT("Client");
		
		private String label;

	    private Localization(String label) {
	        this.label = label;
	    }

	    public String getLabel() {
	        return label;
	    }

}
