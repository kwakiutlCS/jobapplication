package pt.uc.dei.aor.project.business.exception;

public class ReservedQuestionException extends Exception {

	private static final long serialVersionUID = -5870697415548617616L;
	
	public ReservedQuestionException(String msg) {
		super(msg);
	}

	public ReservedQuestionException() {
	}
}
