package pt.uc.dei.aor.project.business.exception;

public class IllegalFilterParamException extends Exception {

	private static final long serialVersionUID = -5870697415548617616L;
	
	public IllegalFilterParamException(String msg) {
		super(msg);
	}

	public IllegalFilterParamException() {
	}
}
