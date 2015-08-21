package pt.uc.dei.aor.project.presentation.bean;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import pt.uc.dei.aor.project.business.exception.NoRoleException;
import pt.uc.dei.aor.project.business.model.IWorker;
import pt.uc.dei.aor.project.business.service.IWorkerBusinessService;
import pt.uc.dei.aor.project.business.util.Role;
import pt.uc.dei.aor.project.presentation.security.Encryptor;

@Named
@RequestScoped
public class WorkerBean {
	
	@Inject
	private IWorkerBusinessService workerService;
	
	private String login;
	private String password;
	private String email;
	private String name;
	private String surname;
	private List<Role> roles;
	
	
	public void register() {
		try {
			workerService.createNewWorker(login, name, surname, email, Encryptor.encrypt(password), roles);
		} catch (NoRoleException e) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Missing role", "At least one role should be selected");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	public List<IWorker> getUsers() {
		return workerService.findAllUsers();
	}
	
	public List<Role> getPossibleRoles() {
		return workerService.getRoles();
	}
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> role) {
		this.roles = role;
	}
}
