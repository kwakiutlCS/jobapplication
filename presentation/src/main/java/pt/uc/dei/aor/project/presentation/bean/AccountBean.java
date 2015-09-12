package pt.uc.dei.aor.project.presentation.bean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import pt.uc.dei.aor.project.business.exception.WrongPasswordException;
import pt.uc.dei.aor.project.business.service.IWorkerBusinessService;
import pt.uc.dei.aor.project.presentation.util.MetaUtils;

@Named
@ViewScoped
public class AccountBean implements Serializable {

	private static final long serialVersionUID = -785077512441625997L;

	@Inject
	private IWorkerBusinessService workerService;
	
	private String login;
	private String name;
	private String surname;
	private String email;
	private String password;
	private String oldPassword;

	
	
	public void updatePassword() {
		System.out.println("herer");
		try {
			workerService.updatePassword(MetaUtils.getUser(), password, oldPassword);
			MetaUtils.setMsg("Password updated", FacesMessage.SEVERITY_INFO);
		} catch (WrongPasswordException e) {
			MetaUtils.setMsg("Password is incorrect", FacesMessage.SEVERITY_ERROR);
		}
	}
	
	// getters and setters
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	
}
