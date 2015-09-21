package pt.uc.dei.aor.project.business.exception;

public class IllegalRoleChangeException extends Exception {

	private static final long serialVersionUID = -5870697415548617616L;
	
	public IllegalRoleChangeException(String msg) {
		super(msg);
	}

	public IllegalRoleChangeException() {
	}
}
