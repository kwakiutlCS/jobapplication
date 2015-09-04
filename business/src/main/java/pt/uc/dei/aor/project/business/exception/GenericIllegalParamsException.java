package pt.uc.dei.aor.project.business.exception;

public class GenericIllegalParamsException extends Exception {

	private static final long serialVersionUID = -5870697415548617616L;
	
	public GenericIllegalParamsException(String msg) {
		super(msg);
	}

	public GenericIllegalParamsException() {
	}
}
