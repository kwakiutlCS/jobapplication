package pt.uc.dei.aor.project.presentation.bean;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.uc.dei.aor.project.business.model.IWorker;
import pt.uc.dei.aor.project.business.service.IWorkerBusinessService;
import pt.uc.dei.aor.project.presentation.util.MetaUtils;

@Named
@RequestScoped
public class LoginBean {

	private static final Logger logger = LoggerFactory.getLogger(LoginBean.class);
	
	@Inject
	private IWorkerBusinessService workerService;
	
	private String username;
	private String password;
	private String email;
	
	
	
	public String login() {
		String result = "";
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		
		try {
			request.login(username, password);
			
			IWorker worker = workerService.getWorkerByLogin(username);
			request.getSession().setAttribute("user", worker);
			
			List<String> roles = worker.getRoles();
			
			if (roles.contains("ADMIN")) {
				result = "/admin/index.xhtml?faces-redirect=true";
			}
			else if (roles.contains("MANAGER")) {
				result = "/manager/index.xhtml?faces-redirect=true";
			}
			else 
				result = "/interview/index.xhtml?faces-redirect=true";
		} catch (Exception e) {
			logger.error(e.getMessage(), FacesMessage.SEVERITY_ERROR);
			result = "loginerror";
		}
		return result;
	}
	
	
	public String logout() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		try {
			request.getSession().invalidate();
			request.logout();
			return "/index.xhtml?faces-redirect=true";
		} catch (Exception e) {
			context.addMessage(null, new FacesMessage("Logout falhado"));
		}
		return null;
	}
	
	public void recover() {
		workerService.recoverPassword(email);
	}
	
	
	
	public IWorker getUser() {
		return MetaUtils.getUser();	
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
