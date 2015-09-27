package pt.uc.dei.aor.project.business.model;

import java.util.Collection;
import java.util.Date;

import pt.uc.dei.aor.project.business.exception.AllPhasesCompletedException;

public interface IInterview {

	public String getDate();
	
	public Date getDateObject();
	
	public IApplication getApplication();
	
	public IUser getCandidate();
	
	public Collection<IUser> getInterviewers();

	public long getId();
	
	public boolean isEditable();
	
	public String getInterviewersFormatted();
	
	public int getInterviewPhase() throws AllPhasesCompletedException;

	public void addInterviewer(IUser interviewer);
	
	public void removeInterviewer(IUser interviewer);

	public void setDate(Date date);

	public void setInterviewers(Collection<IUser> selectedInterviewers);

	
}
