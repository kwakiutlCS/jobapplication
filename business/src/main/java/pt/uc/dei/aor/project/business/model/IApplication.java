package pt.uc.dei.aor.project.business.model;

import java.util.Date;
import java.util.List;

public interface IApplication {

	ICandidate getCandidate();
	
	IPosition getPosition();
	
	List<IInterview> getInterviews();

	long getId();

	void addInterview(IInterview interview);

	IInterview getInterviewByDate(Date date);

	void remove(IInterview interview);
	
	String getDate();

	boolean reachedAllPhases();

	String getLetter();

}
