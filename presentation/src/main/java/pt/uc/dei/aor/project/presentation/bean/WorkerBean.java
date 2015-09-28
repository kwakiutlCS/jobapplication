package pt.uc.dei.aor.project.presentation.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.uc.dei.aor.project.business.exception.DuplicatedUserException;
import pt.uc.dei.aor.project.business.exception.IllegalFilterParamException;
import pt.uc.dei.aor.project.business.exception.IllegalFormatUploadException;
import pt.uc.dei.aor.project.business.exception.IllegalRoleChangeException;
import pt.uc.dei.aor.project.business.exception.NoRoleException;
import pt.uc.dei.aor.project.business.filter.WorkerFilter;
import pt.uc.dei.aor.project.business.model.IUser;
import pt.uc.dei.aor.project.business.service.IUserBusinessService;
import pt.uc.dei.aor.project.business.util.Role;
import pt.uc.dei.aor.project.presentation.util.MetaUtils;

@Named
@ViewScoped
public class WorkerBean implements Serializable {
	
	private static final long serialVersionUID = -1265270672848552351L;

	private static final Logger logger = LoggerFactory.getLogger(WorkerBean.class);
	
	@Inject
	private IUserBusinessService userService;
	
	private String login;
	private String email;
	private String name;
	private String surname;
	private List<Role> roles;
	private UploadedFile file;
	
	// filter
	private WorkerFilter filter;
	private String keyword;
	private Role role;
	
	@PostConstruct
	public void init() {
		filter = new WorkerFilter();
	}
	
	
	

	public void register() {
		try {
			userService.createNewUser(login, name, surname, email, roles);
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
		String filename = file.getFileName();
		if (filename.length() > 4 && !filename.substring(filename.length()-4).equals(".csv")) {
			MetaUtils.setMsg("Please upload a csv file", FacesMessage.SEVERITY_ERROR);
			return; 
		}
		
		try {
			userService.uploadUsers(file.getInputstream());
		} catch (IOException e) {
			MetaUtils.setMsg("Error uploading file", FacesMessage.SEVERITY_ERROR);
		} catch (IllegalFormatUploadException e) {
			MetaUtils.setMsg(e.getMessage(), FacesMessage.SEVERITY_ERROR);
		} catch (NoRoleException e) {
			MetaUtils.setMsg("No role selected", FacesMessage.SEVERITY_ERROR);
		} catch (DuplicatedUserException e) {
			MetaUtils.setMsg("Duplicated user", FacesMessage.SEVERITY_ERROR);
		}
		
	}
	
	
	public void addAdmin(IUser user) {
		userService.addAdmin(user);
	}
	
	public void removeAdmin(IUser user) {
		try {
			userService.removeAdmin(MetaUtils.getUser(), user);
		} catch (IllegalRoleChangeException e) {
			MetaUtils.setMsg(e.getMessage(), FacesMessage.SEVERITY_ERROR);
		}
	}
	
	public void addManager(IUser user) {
		userService.addManager(user);
	}
	
	public void removeManager(IUser user) {
		userService.removeManager(user);
	}
	
	public void addInterviewer(IUser user) {
		userService.addInterviewer(user);
	}
	
	public void removeInterviewer(IUser user) {
		userService.removeInterviewer(user);
	}
	
	
	// filter actions
	public void addKeyword() {
		filter.setKeyword(keyword);
	}
	
	public void removeKeyword() {
		filter.setKeyword(null);
		keyword = null;
	}
	
	public void addRole() {
		filter.addRole(role);
	}
	
	public void removeRole(int index, int pos) {
		try {
			filter.removeRole(index, pos);
		} catch (IllegalFilterParamException e) {
			logger.error("Illegal filter parameters!!");
			MetaUtils.setMsg("Illegal filter parameters", FacesMessage.SEVERITY_ERROR);
		}
	}
	
	public void splitRoles(int index, int pos) {
		try {
			filter.splitRoles(index, pos);
		} catch (IllegalFilterParamException e) {
			logger.error("Illegal filter parameters!!");
			MetaUtils.setMsg("Illegal filter parameters", FacesMessage.SEVERITY_ERROR);
		}
	}
	
	public void mergeRoles(int index) {
		try {
			filter.mergeRoles(index);
		} catch (IllegalFilterParamException e) {
			logger.error("Illegal filter parameters!!");
			MetaUtils.setMsg("Illegal filter parameters", FacesMessage.SEVERITY_ERROR);
		}
	}
	
	public List<IUser> getUsers() {
		return userService.findUsersWithFilter(filter, 0, 10);
	}
	
	public List<Role> getPossibleRoles() {
		return userService.getRoles();
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
	
	public String getKeyword() {
		return keyword;
	}


	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}


	public WorkerFilter getFilter() {
		return filter;
	}




	public Role getRole() {
		return role;
	}

	public Collection<IUser> getAllManagers() {
		return userService.findAllManagers();
	}


	public void setRole(Role role) {
		this.role = role;
	}
	
	public List<List<Role>> getRoleSets() {
		return filter.getRoleSets();
	}

}

