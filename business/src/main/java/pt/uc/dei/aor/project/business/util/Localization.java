package pt.uc.dei.aor.project.business.util;

	
	public enum Localization {
		COIMBRA("Coimbra"), 
		LISBOA("Lisboa"), 
		OPORTO("Oporto"), 
		CLIENT("Client");
		
		private String localizationLabel;

	    private Localization(String localizationLabel) {
	        this.localizationLabel = localizationLabel;
	    }

	    public String getLocalizationLabel() {
	        return localizationLabel;
	    }

}
