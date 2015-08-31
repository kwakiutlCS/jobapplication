package pt.uc.dei.aor.project.presentation.bean;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import pt.uc.dei.aor.project.business.model.IApplication;
import pt.uc.dei.aor.project.business.service.IApplicationBusinessService;

@Named
@SessionScoped
public class ApplicationBean implements Serializable {
	
	private static final long serialVersionUID = 1604486476339956215L;
	
	private IApplication selected;
	
	@Inject
	private IApplicationBusinessService applicationService;
	
	
	public Collection<IApplication> getActiveApplications() {
		return applicationService.findActiveApplications();
	}
	
	public String showDetails(IApplication application) {
		selected = application;
		return "application.xhtml?faces-redirect=true";
	}
	
	// helper methods
	
	private HttpSession getSession() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		return request.getSession();
	}
}

