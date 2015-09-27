package pt.uc.dei.aor.project.presentation.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import pt.uc.dei.aor.project.business.exception.IllegalFilterParamException;
import pt.uc.dei.aor.project.business.filter.InterviewFilter;
import pt.uc.dei.aor.project.business.model.IInterview;
import pt.uc.dei.aor.project.business.model.IPosition;
import pt.uc.dei.aor.project.business.model.IUser;
import pt.uc.dei.aor.project.business.service.IInterviewBusinessService;
import pt.uc.dei.aor.project.business.service.IPositionBusinessService;
import pt.uc.dei.aor.project.business.service.IUserBusinessService;
import pt.uc.dei.aor.project.business.service.IUserBusinessService;
import pt.uc.dei.aor.project.presentation.util.MetaUtils;

@Named
@ViewScoped
public class InterviewListBean implements Serializable {
	
	private static final long serialVersionUID = -5948435434591689631L;
	
	private int offset;
	private InterviewFilter filter;
		
	private List<IInterview> interviews;
	
	// filter params
	private IUser filterInterviewer;
	private String filterPosition;
	private String filterCandidate;
	private Date startDate;
	private Date endDate;
	
	@Inject
	private IInterviewBusinessService interviewService;
	
	@Inject
	private IUserBusinessService userService;
	
	@Inject
	private IPositionBusinessService positionService;
	
	
	@PostConstruct
	public void init() {
		filter = new InterviewFilter();
	}
	
	
	
	// public methods
	
	public void onload() {
		interviews = interviewService.findInterviews(offset, 10);
	}
	
	
			// filter functions
	
	public List<IInterview> getActiveInterviews() {
		return interviewService.findActiveInterviewsByUser(MetaUtils.getUser());
	}

	public List<IInterview> getInterviewsWithFilter() {
		return interviewService.findInterviews(offset, 10, filter);
	}
	
	
	
			// prepare filter functions
	
	public void addInterviewerToFilter() {
		filter.addInterviewerSet(filterInterviewer);
		interviews = getInterviewsWithFilter();
	}
	
	public void addPositionToFilter() {
		filter.addPosition(filterPosition);
		interviews = getInterviewsWithFilter();
		filterPosition = null;
	}
	
	public void addCandidateToFilter() {
		filter.addCandidate(filterCandidate);
		filterCandidate = null;
		interviews = getInterviewsWithFilter();
	}
	
	public InterviewFilter getFilter() {
		return filter;
	}



	public void addDateFilter() {
		filter.setStartDate(startDate);
		filter.setFinishDate(endDate);
		startDate = null;
		endDate = null;
		interviews = getInterviewsWithFilter();
	}
	
	public void deleteInterviewer(int setPos, int pos) {
		try {
			filter.deleteInterviewer(setPos, pos);
			interviews = getInterviewsWithFilter();
		} catch (IllegalFilterParamException e) {
			MetaUtils.setMsg("todo", FacesMessage.SEVERITY_ERROR);
		}
	}
	
	public void deletePosition(int index) {
		filter.deletePosition(index);
		interviews = getInterviewsWithFilter();
	}
	
	public void deleteCandidate() {
		filter.deleteCandidate();
		interviews = getInterviewsWithFilter();
	}
	
	
	public void mergeInterviewers(int setPos) {
		try {
			filter.mergeInterviewers(setPos);
			interviews = getInterviewsWithFilter();
		} catch (IllegalFilterParamException e) {
			MetaUtils.setMsg("todo", FacesMessage.SEVERITY_ERROR);
		}
	}
	
	public void splitInterviewers(int setPos, int pos) {
		try {
			filter.splitInterviewers(setPos, pos);
			interviews = getInterviewsWithFilter();
		} catch (IllegalFilterParamException e) {
			MetaUtils.setMsg("todo", FacesMessage.SEVERITY_ERROR);
		}
	}
	
	public void removeStartDate() {
		filter.setStartDate(null);
		interviews = getInterviewsWithFilter();
	}
	
	public void removeFinishDate() {
		filter.setFinishDate(null);
		interviews = getInterviewsWithFilter();
	}
	
	// complete functions
	public List<String> completePosition(String text) {
		try {
			Long.parseLong(text);
			return null;
		}
		catch (Exception e) {
			List<String> results = new ArrayList<>();
			for (IPosition p : positionService.findPositionByTitle(text)) {
				results.add(p.getTitle());
				if (results.size() == 4) return results;
			}
			return results;
		}
	}
	
	public List<String> completeCandidate(String text) {
		// TODO
		return null;
	}
	
	
	// getters and setters
	
	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}


	public List<IInterview> getInterviews() {
		return interviews;
	}


	public void setInterviews(List<IInterview> interviews) {
		this.interviews = interviews;
	}
	
	public Collection<IUser> getAllInterviewers() {
		return userService.findAllInterviewers();
	}


	public IUser getFilterInterviewer() {
		return filterInterviewer;
	}


	public void setFilterInterviewer(IUser filterInterviewer) {
		this.filterInterviewer = filterInterviewer;
	}

		
	public List<List<IUser>> getInterviewerSets() {
		return filter.getInterviewerSets();
	}

	public List<String> getPositionSets() {
		return filter.getPositions();
	}

	public String getCandidate() {
		return filter.getCandidate();
	}
	
	public String getFilterPosition() {
		return filterPosition;
	}



	public void setFilterPosition(String filterPosition) {
		this.filterPosition = filterPosition;
	}



	public String getFilterCandidate() {
		return filterCandidate;
	}



	public void setFilterCandidate(String filterCandidate) {
		this.filterCandidate = filterCandidate;
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
}

