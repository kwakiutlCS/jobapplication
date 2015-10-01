package pt.uc.dei.aor.project.business.exception;

public class IllegalPositionAssignmentException extends Exception {

	private static final long serialVersionUID = -5462593943374452899L;


	public IllegalPositionAssignmentException(String message){
		super(message);
	}

	public IllegalPositionAssignmentException(){
		super();
	}
	
}
