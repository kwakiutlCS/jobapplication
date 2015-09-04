package pt.uc.dei.aor.project.business.exception;

public class RepeatedInterviewException extends Exception {

	private static final long serialVersionUID = -5870697415548617616L;
	
	public RepeatedInterviewException(String msg) {
		super(msg);
	}

	public RepeatedInterviewException() {
	}
}
