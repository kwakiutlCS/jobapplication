package pt.uc.dei.aor.project.business.service;

import java.util.Collection;

import javax.ejb.Stateless;
import javax.inject.Inject;

import pt.uc.dei.aor.project.business.model.IInterview;
import pt.uc.dei.aor.project.business.model.IWorker;
import pt.uc.dei.aor.project.business.persistence.IInterviewPersistenceService;


@Stateless
public class InterviewBusinessService implements IInterviewBusinessService {

	@Inject
	private IInterviewPersistenceService interviewPersistence;
	
	
	@Override
	public Collection<IInterview> findActiveInterviewsByUser(IWorker user) {
		return interviewPersistence.findActiveInterviewsByUser(user);
	}
	
}
