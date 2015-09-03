package pt.uc.dei.aor.project.presentation.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import pt.uc.dei.aor.project.business.model.IApplication;
import pt.uc.dei.aor.project.business.service.IApplicationBusinessService;

@Named
@ViewScoped
public class ApplicationBean implements Serializable {
	
	private static final long serialVersionUID = 1604486476339956215L;
	
	private IApplication selectedApplication;
	private long selectedId;
	
	@Inject
	private IApplicationBusinessService applicationService;
	
	
	
	public List<IApplication> getActiveApplications() {
		List<IApplication> list = applicationService.findActiveApplications();
		return list;
	}
	
	public String showDetails(IApplication application) {
		return "application.xhtml?faces-redirect=true";
	}

	
	public void onload() {
		System.out.println("hello");
		System.out.println(selectedId);
		selectedApplication = applicationService.findApplicationById(selectedId);
		System.out.println(selectedApplication);
	}
	
	
	// getters and setters
	
	public IApplication getSelectedApplication() {
		return selectedApplication;
	}

	public void setSelectedApplication(IApplication selectedApplication) {
		this.selectedApplication = selectedApplication;
	}

	public long getSelectedId() {
		return selectedId;
	}

	public void setSelectedId(long selectedId) {
		this.selectedId = selectedId;
	}
	
	
}

