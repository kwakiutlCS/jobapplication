package pt.uc.dei.aor.project.presentation.bean;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.uc.dei.aor.project.business.model.IApplication;
import pt.uc.dei.aor.project.business.model.IWorker;
import pt.uc.dei.aor.project.business.service.IApplicationBusinessService;
import pt.uc.dei.aor.project.business.service.IPropositionBusinessService;
import pt.uc.dei.aor.project.business.service.IWorkerBusinessService;
import pt.uc.dei.aor.project.presentation.util.MetaUtils;

@Named
@ViewScoped
public class PropositionBean implements Serializable {

	private static final long serialVersionUID = 3173876033100081343L;

	@Inject 
	private IPropositionBusinessService propositionService;
	
	@Inject
	private IApplicationBusinessService applicationService;
	
	
	public void sendProposition(IApplication application) {
		propositionService.sendProposition(application);
		applicationService.changeAnalyzed(application, true);
	}
}
