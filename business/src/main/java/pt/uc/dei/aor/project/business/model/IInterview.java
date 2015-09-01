package pt.uc.dei.aor.project.business.model;

import java.util.Collection;
import java.util.Date;

public interface IInterview {

	public Date getDate();
	
	public IApplication getApplication();
	
	public ICandidate getCandidate();
	
	public Collection<IWorker> getInterviewers();

	public long getId();
}
