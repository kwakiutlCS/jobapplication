package pt.uc.dei.aor.project.presentation.bean;

import java.util.List;
import java.util.Random;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import pt.uc.dei.aor.project.business.model.IWorker;
import pt.uc.dei.aor.project.business.service.IWorkerBusinessService;
import pt.uc.dei.aor.project.business.service.WorkerBusinessService;

@Named
@RequestScoped
public class LoginBean {

	@Inject
	private IWorkerBusinessService workerService;
	
	private String login;
	private String password;
	
	public LoginBean() {
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
	
	
	public String login() {
		String result = "";
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		
		try {
			request.login(login, password);
			
			IWorker worker = workerService.getWorkerByLogin(login);
			request.getSession().setAttribute("full_name", worker.getFullName());
			
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
			System.out.println(e.getMessage());
			e.printStackTrace();
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
	
	public String getName() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		return (String) request.getSession().getAttribute("full_name");
	}
}
