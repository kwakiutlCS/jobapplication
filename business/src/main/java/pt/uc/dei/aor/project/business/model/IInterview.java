package pt.uc.dei.aor.project.business.model;

import java.util.Collection;
import java.util.Date;

import pt.uc.dei.aor.project.business.exception.AllPhasesCompletedException;

public interface IInterview {

	public String getDate();
	
	public Date getDateObject();
	
	public IApplication getApplication();
	
	public ICandidate getCandidate();
	
	public Collection<IWorker> getInterviewers();

	public long getId();
	
	public boolean isEditable();
	
	public String getInterviewersFormatted();
	
	public int getInterviewPhase() throws AllPhasesCompletedException;

	public void addInterviewer(IWorker interviewer);
	
	public void removeInterviewer(IWorker interviewer);

	public void setDate(Date date);

	public void setInterviewers(Collection<IWorker> selectedInterviewers);

	
}
