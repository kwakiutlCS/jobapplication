package pt.uc.dei.aor.project.business.filter;

import java.util.Date;

import pt.uc.dei.aor.project.business.util.PositionState;

public class ApplicationFilter extends GenericFilter {
	
	private String code;
	private Date startDate;
	private Date finishDate;
	private String candidate;
	private ApplicationType applicationType;
	private PositionState positionState;
	
	
	public ApplicationFilter() {
		setCode(null);
		setStartDate(null);
		setFinishDate(null);
		setCandidate(null);
		setApplicationType(ApplicationType.BOTH);
		setPositionState(PositionState.OPEN);
	}
	
	
	
	
	
	
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = prepareStartDate(startDate);
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = prepareFinishDate(finishDate);
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

	public ApplicationType getApplicationType() {
		return applicationType;
	}

	public void setApplicationType(ApplicationType applicationType) {
		this.applicationType = applicationType;
	}

	public PositionState getPositionState() {
		return positionState;
	}

	public void setPositionState(PositionState positionState) {
		this.positionState = positionState;
	}






	private static enum ApplicationType {
		INTERNAL, EXTERNAL, BOTH;
	}
}
