package pt.uc.dei.aor.project.presentation.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import pt.uc.dei.aor.project.business.exception.AllPhasesCompletedException;
import pt.uc.dei.aor.project.business.model.IApplication;
import pt.uc.dei.aor.project.business.model.IInterview;
import pt.uc.dei.aor.project.business.service.IApplicationBusinessService;
import pt.uc.dei.aor.project.business.service.IInterviewBusinessService;
import pt.uc.dei.aor.project.business.service.IPropositionBusinessService;
import pt.uc.dei.aor.project.presentation.util.MetaUtils;

@Named
@ViewScoped
public class PropositionBean implements Serializable {

	private static final long serialVersionUID = 3173876033100081343L;

	@Inject 
	private IPropositionBusinessService propositionService;
	
	@Inject
	private IApplicationBusinessService applicationService;
	
	@Inject
	private IInterviewBusinessService interviewService;
	
	
	public void sendProposition(IApplication application) {
		List<IInterview> interviews = application.getInterviews();
		for (IInterview i : interviews) {
			try {
				if (!interviewService.isCompleted(i)) {
					MetaUtils.setMsg("An interview is still scheduled for this candidate", FacesMessage.SEVERITY_ERROR);
					return;
				}
			} catch (AllPhasesCompletedException e) {
				MetaUtils.setMsg("Error sending proposion", FacesMessage.SEVERITY_ERROR);
			}
		}
		
		propositionService.sendProposition(application);
		applicationService.changeAnalyzed(application, true);
	}
}
