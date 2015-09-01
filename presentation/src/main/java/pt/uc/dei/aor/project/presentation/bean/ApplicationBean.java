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
	
	@PostConstruct // isto é provisório para fazer as interviews
	public void init() {
		selected = applicationService.findActiveApplications().get(0);
	}
	
	public List<IApplication> getActiveApplications() {
		System.out.println("Application bean web");
		List<IApplication> list = applicationService.findActiveApplications();
		System.out.println(list);
		return list;
	}
	
	public String showDetails(IApplication application) {
		setSelected(application);
		return "application.xhtml?faces-redirect=true";
	}
	
	
	
			
	// getters and setters
	
	public IApplication getSelected() {
		return selected;
	}

	public void setSelected(IApplication selected) {
		this.selected = selected;
	}
}

