package pt.uc.dei.aor.project.business.model;

import java.util.List;

import javax.resource.spi.IllegalStateException;

import pt.uc.dei.aor.project.business.exception.IllegalRoleException;
import pt.uc.dei.aor.project.business.util.Role;

public interface IUser {

	public String getFullName();
	
	public List<String> getRoles();
	
	public String getRolesAsString();

	public String getLogin();
	
	public long getId();
	
	public String getName();
	
	public String getSurname();
	
	public String getEmail();

	public void addInterview(IInterview interview) throws IllegalRoleException, IllegalStateException;

	public void setPassword(String encrypt);

	public void setLogin(String login);

	public void setName(String name);

	public void setSurname(String surname);

	public void setEmail(String email);

	public void setAddress(String address);

	public void setCity(String city);

	public void setCountry(String country);

	public void setMobile(String mobile);

	public void setPhone(String phone);

	public String getAddress();

	public String getCity();

	public String getCountry();

	public String getPhone();

	public String getMobile();

	public List<IQualification> getQualifications();

	public void addQualification(IQualification qualification);

	public void removeQualification(IQualification qualification);

	public void setCv(String filename);

	public String getCv();
	
	public boolean isAdmin();
	
	public boolean isManager();
	
	public boolean isInterviewer();

	public void addRole(Role interviewer);

	public void removeRole(Role interviewer);
	
}
