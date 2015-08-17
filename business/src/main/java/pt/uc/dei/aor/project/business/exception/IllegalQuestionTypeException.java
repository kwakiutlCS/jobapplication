package pt.uc.dei.aor.project.business.exception;

public class IllegalQuestionTypeException extends Exception {

	private static final long serialVersionUID = -5870697415548617616L;
	
	public IllegalQuestionTypeException(String msg) {
		super(msg);
	}

	public IllegalQuestionTypeException() {
	}
}
