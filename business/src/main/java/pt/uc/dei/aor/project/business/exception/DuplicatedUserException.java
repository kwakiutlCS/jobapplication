package pt.uc.dei.aor.project.business.exception;

public class DuplicatedUserException extends Exception {

	private static final long serialVersionUID = -5462593943374452899L;


	public DuplicatedUserException(String message){
		super(message);
	}

	public DuplicatedUserException(){
		super();
	}
	
}
