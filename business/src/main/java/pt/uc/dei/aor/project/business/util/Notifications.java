package pt.uc.dei.aor.project.business.util;

	
	public enum Notifications {
		WORKER_CREATED("Account created");
		
		
		private String label;
		
	    private Notifications(String label) {
	        this.label = label;
	    }

		public String getLabel() {
			return label;
		}
	

}
