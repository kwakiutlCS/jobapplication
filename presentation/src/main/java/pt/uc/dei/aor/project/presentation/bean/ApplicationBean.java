package pt.uc.dei.aor.project.presentation.bean;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import pt.uc.dei.aor.project.business.model.IApplication;
import pt.uc.dei.aor.project.business.service.IApplicationBusinessService;

@Named
@RequestScoped
public class ApplicationBean {
	
	@Inject
	private IApplicationBusinessService applicationService;
	
	public List<IApplication> getActiveApplications() {
		return applicationService.findActiveApplications();
	}

}

