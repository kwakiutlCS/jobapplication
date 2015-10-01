package pt.uc.dei.aor.project.presentation_public.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import pt.uc.dei.aor.project.business.model.IApplication;
import pt.uc.dei.aor.project.business.model.IPosition;
import pt.uc.dei.aor.project.business.model.IUser;
import pt.uc.dei.aor.project.business.service.IApplicationBusinessService;
import pt.uc.dei.aor.project.business.service.IPositionBusinessService;
import pt.uc.dei.aor.project.presentation_public.util.MetaUtils;


@Named
@ViewScoped
public class MyPageBean implements Serializable{


	private static final long serialVersionUID = 1L;

	@Inject
	private IApplicationBusinessService applicationService;

	@Inject
	private IPositionBusinessService positionService;


	private IPosition position;
private IApplication selectedApp;



	public List<IApplication> getMyApplications() {

		IUser candidate = MetaUtils.getUser();

		return applicationService.findAllApplicationByCandidate(candidate);
	}

	public String getStatePositionByApplication(IApplication application) {
		return application.getPosition().getStateFormatted();
	}


	public IPosition getPosition() {
		return position;
	}

	public void setPosition(IPosition position) {
		this.position = position;
	}

	public void setMyApplications(List<IApplication> myApplications) {
	}

	public IPositionBusinessService getPositionService() {
		return positionService;
	}

	public void setPositionService(IPositionBusinessService positionService) {
		this.positionService = positionService;
	}

	public String getDateInterview(IApplication application) {
		
		int sizeList = application.getInterviews().size();
		
		if(sizeList==0)
			return "-";
		
		return application.getInterviews().get(sizeList-1).getDate();
	}
	
public String getProposition(IApplication application) {
		
		if (application.isAccepted()) return "Application accepted";
		else if (application.isRefusedByCandidate()) return "Application refused by candidate";
		else if (application.isProposed()) return "Negotiation process";
		else return "-";
	}

public String getPositionTitle(IApplication application) {
	
	IPosition position = application.getPosition();
	if (position == null || position.getId() == 0) return "Spontaneous";
	else return position.getTitle();
}



	public IApplication getSelectedApp() {
		return selectedApp;
	}

	public void setSelectedAppp(IApplication selectedApp) {
		this.selectedApp = selectedApp;
	}

}
