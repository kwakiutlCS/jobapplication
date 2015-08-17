package pt.uc.dei.aor.project.business.exception;

public class IllegalAnswerOptionsException extends Exception {

	private static final long serialVersionUID = -5870697415548617616L;
	
	public IllegalAnswerOptionsException(String msg) {
		super(msg);
	}

	public IllegalAnswerOptionsException() {
	}
}
