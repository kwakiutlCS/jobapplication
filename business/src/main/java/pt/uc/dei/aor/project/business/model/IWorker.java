package pt.uc.dei.aor.project.business.model;

import java.util.List;

import javax.resource.spi.IllegalStateException;

import pt.uc.dei.aor.project.business.exception.IllegalRoleException;

public interface IWorker {

	public String getFullName();
	
	public List<String> getRoles();

	public String getLogin();
	
	public long getId();
	
	public String getName();
	
	public String getSurname();
	
	public String getEmail();

	public void addInterview(IInterview interview) throws IllegalRoleException, IllegalStateException;
	
}
