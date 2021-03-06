package pt.uc.dei.aor.project.presentation.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import pt.uc.dei.aor.project.business.filter.ApplicationFilter;
import pt.uc.dei.aor.project.business.model.IApplication;
import pt.uc.dei.aor.project.business.service.IApplicationBusinessService;
import pt.uc.dei.aor.project.business.util.PositionState;
import pt.uc.dei.aor.project.presentation.util.MetaUtils;

@Named
@ViewScoped
public class ApplicationBean implements Serializable {
	
	private static final long serialVersionUID = -71560379068779861L;
	
	@Inject
	private IApplicationBusinessService applicationService;
	
	
	// filter
	private ApplicationFilter filter;
	private Date startDate;
	private Date endDate;
	private String code;
	private String candidate;
	private PositionState state;
	private String type;

	private String spontaneous;
	
	
	@PostConstruct
	public void init() {
		filter = new ApplicationFilter();
		state = PositionState.OPEN;
	}
	
	
	
	
	public List<IApplication> getActiveApplications() {
		return applicationService.findApplicationsWithFilter(filter, 0, 10);
	}

	public List<IApplication> getActiveApplicationsByManager() {
		return applicationService.findApplicationsWithFilterByManager(filter, 0, 10, MetaUtils.getUser());
	}

	
	
	// filter functions
	
	public void addDateFilter() {
		filter.setStartDate(startDate);
		filter.setFinishDate(endDate);
	}
	
	public void removeStartDate() {
		filter.setStartDate(null);
		startDate = null;
	}
	
	public void removeFinishDate() {
		filter.setFinishDate(null);
		endDate = null;
	}
	
	public void addCode() {
		filter.setCode(code);
	}
	
	public void removeCode() {
		filter.setCode(null);
		code = null;
	}
	
	public void addCandidate() {
		filter.setCandidate(candidate);
	}
	
	public void removeCandidate() {
		filter.setCandidate(null);
		candidate = null;
	}
	
	public void addState() {
		filter.setState(state);
	}
	
	public void removeState() {
		filter.setState(null);
		state = null;
	}
	
	public void addSpontaneous() {
		if (spontaneous.equals("True"))
			filter.setSpontaneous(true);
		else filter.setSpontaneous(false);
	}
	
	public String getSpontaneous() {
		return spontaneous;
	}




	public void setSpontaneous(String spontaneous) {
		this.spontaneous = spontaneous;
	}




	public void removeSpontaneous() {
		filter.setSpontaneous(null);
		spontaneous = null;
	}
	
	
	// getters and setters
	
	public ApplicationFilter getFilter() {
		return filter;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCandidate() {
		return candidate;
	}

	public void setCandidate(String candidate) {
		this.candidate = candidate;
	}

	public PositionState getState() {
		return state;
	}

	public void setState(PositionState state) {
		this.state = state;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}

