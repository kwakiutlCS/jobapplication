package pt.uc.dei.aor.project.presentation.bean;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.UploadedFile;

import pt.uc.dei.aor.project.business.exception.DuplicatedUserException;
import pt.uc.dei.aor.project.business.exception.NoRoleException;
import pt.uc.dei.aor.project.business.model.IWorker;
import pt.uc.dei.aor.project.business.service.IWorkerBusinessService;
import pt.uc.dei.aor.project.business.startup.Encryptor;
import pt.uc.dei.aor.project.business.util.Role;
import pt.uc.dei.aor.project.presentation.util.MetaUtils;

@Named
@RequestScoped
public class WorkerBean {
	
	@Inject
	private IWorkerBusinessService workerService;
	
	private String login;
	private String email;
	private String name;
	private String surname;
	private List<Role> roles;
	private UploadedFile file;
	
	public void register() {
		try {
			workerService.createNewWorker(login, name, surname, email, roles);
			login = null;
			email = null;
			name = null;
			surname = null;
			roles = null;
		} catch (NoRoleException e) {
			MetaUtils.setMsg("Missing role", FacesMessage.SEVERITY_ERROR);
		} catch (DuplicatedUserException e) {
			MetaUtils.setMsg("User Already exists", FacesMessage.SEVERITY_ERROR);
		}
	}
	
	public void upload() {
		System.out.println("upload");
		System.out.println(file.getFileName());
		
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

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}
	
}

