package pt.uc.dei.aor.project.business.model;

import java.util.Date;
import java.util.List;

import pt.uc.dei.aor.project.business.util.ProposalSituation;

public interface IApplication {

	IUser getCandidate();
	
	IPosition getPosition();
	
	List<IInterview> getInterviews();

	long getId();

	void addInterview(IInterview interview);

	IInterview getInterviewByDate(Date date);

	void remove(IInterview interview);
	
	String getDate();

	boolean reachedAllPhases();

	String getLetter();

	boolean getAnalyzed();
	
	void changeAnalyzed(boolean value);
	
	boolean isRefused();
	
	boolean isAccepted();
	
	boolean isPropositionSent();

	boolean isRefusedByCandidate();
	
	boolean isProposed();
	
	void refuse();

	void sendProposition(IProposition proposition);

	ProposalSituation getProposition();

	void setCv(String filename);

	void addPositon(IPosition positionToAdd);

	String getCv();
	
	boolean isSpontaneous();

	Date getDateObject();

	Date getPropositionDate();
}
