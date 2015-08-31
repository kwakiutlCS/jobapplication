package pt.uc.dei.aor.project.business.util;

public enum Role {
	ADMIN("Admin"), MANAGER("Manager"), INTERVIEWER("Interviewer"), CANDIDATE("Candidate");
	
	private String label;
	
	private Role(String label) {
		this.label = label;
	}
	
	public String getLabel() { return label; }
}
