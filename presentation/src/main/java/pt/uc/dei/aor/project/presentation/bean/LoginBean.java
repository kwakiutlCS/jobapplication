package pt.uc.dei.aor.project.presentation.bean;

import java.util.Random;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Named
@RequestScoped
public class LoginBean {

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
			if (x < 0.4) {
				result = "admin/index.xhtml";
			}
			else if (x < 0.7) {
				result = "manager/index.xhtml";
			}
			else 
				result = "interview/index.xhtml";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			result = "loginerror";
		}
		return result;
	}
}
