package pt.uc.dei.aor.project.business.exception;

public class IllegalApplicationException extends Exception {

	private static final long serialVersionUID = -5462593943374452899L;


	public IllegalApplicationException(String message){
		super(message);
	}

	public IllegalApplicationException(){
		super();
	}
	
}
