package pt.uc.dei.aor.project.presentation.bean;

import java.util.Random;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
		//HttpSession session = request.getSession();
		try {
			request.login(login, password);
			request.setAttribute("full_name", workerService.getWorkerFullName(login));
//			user.setUser(ejb.getUserEntity(email));
//			UserDetail ud = (UserDetail) dozering(user.getUser());
//			single.add(ud);
//			session.setAttribute("user", ud);
//			if (user.getUser().getRoles().contains(Role.USER)) {
//				result = "Authorized/entry.xhtml?faces-redirect=true";
//			} else if (user.getUser().getRoles().contains(Role.ADMIN)) {
//				result = "Admin/index.xhtml";
//			} else result = "Admin/index.xhtml";
			Random rand = new Random();
			double x = rand.nextDouble();
			if (x < 1) {
				result = "/admin/index.xhtml?faces-redirect=true";
			}
			else if (x < 0.7) {
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
		return (String) request.getAttribute("full_name");
	}
}
