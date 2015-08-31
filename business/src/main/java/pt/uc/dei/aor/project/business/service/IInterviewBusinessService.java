package pt.uc.dei.aor.project.business.service;

import java.util.Collection;

import pt.uc.dei.aor.project.business.model.IInterview;
import pt.uc.dei.aor.project.business.model.IWorker;

public interface IInterviewBusinessService {
	
	Collection<IInterview> findActiveInterviewsByUser(IWorker user);
	
}
