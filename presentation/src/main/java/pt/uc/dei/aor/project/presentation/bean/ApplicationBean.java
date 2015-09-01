package pt.uc.dei.aor.project.presentation.bean;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
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
	
	@PostConstruct
	public void init() {
		selected = applicationService.findActiveApplications().get(0);
		System.out.println("Selected");
		System.out.println(selected);
	}
	
	public List<IApplication> getActiveApplications() {
		List<IApplication> list = applicationService.findActiveApplications();
		System.out.println(list.get(0));
		return list;
	}
	
	public String showDetails(IApplication application) {
		setSelected(application);
		return "application.xhtml?faces-redirect=true";
	}
	
	
	
	
	// helper methods
	
	private HttpSession getSession() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		return request.getSession();
	}

	
	
	// getters and setters
	
	public IApplication getSelected() {
		return selected;
	}

	public void setSelected(IApplication selected) {
		this.selected = selected;
	}
}

