package pt.uc.dei.aor.project.presentation_public.bean;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.context.RequestContext;

import pt.uc.dei.aor.project.business.model.ICandidate;
import pt.uc.dei.aor.project.business.service.ICandidateBusinessService;
import pt.uc.dei.aor.project.business.startup.Encryptor;
import pt.uc.dei.aor.project.presentation_public.util.MetaUtils;


@Named
@RequestScoped
public class LoginBean {

	@Inject
	private ICandidateBusinessService candidateService;

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

	public ICandidate getUser() {
		return MetaUtils.getUser();	
	}

	
	public String login() {
		
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

		try {
			System.out.println(login+" "+password);
			System.out.println(Encryptor.encrypt(password));
			request.login(login, password);

			ICandidate candidate = candidateService.getCandidateByLogin(login);
			request.getSession().setAttribute("user", candidate);
			
			return "success";
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			
			return "error";		
		}
	}

	
	public String headerLogin(){
		
		if(login().equals("error")){
			RequestContext requestContext = RequestContext.getCurrentInstance();
			requestContext.execute("PF('loginDlg').show()");
			MetaUtils.setMsg("Login or password wrong", FacesMessage.SEVERITY_INFO);
			return "";
		}
		
		else
			return "/authorized/index.xhtml?faces-redirect=true";
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
	

	
	public boolean isLoggedIn() {
		return MetaUtils.getUser() != null;
	}

}
