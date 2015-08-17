package pt.uc.dei.aor.project.presentation.bean;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import pt.uc.dei.aor.project.business.service.IWorkerBusinessService;
import pt.uc.dei.aor.project.presentation.security.Encryptor;

@Named
@RequestScoped
public class RegisterBean {
	
	@Inject
	private IWorkerBusinessService workerService;
	
	private String login;
	private String password;
	private String email;
	private String name;
	private String surname;
	
	public RegisterBean() {
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
	

	public String register() {
		workerService.createNewWorker(login, name, surname, email, Encryptor.encrypt(password));
		return "index.xhtml";
	}
}