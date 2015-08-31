package pt.uc.dei.aor.project.business.persistence;

import java.util.Collection;

import pt.uc.dei.aor.project.business.model.IInterview;
import pt.uc.dei.aor.project.business.model.IWorker;

public interface IInterviewPersistenceService {

	IInterview save(IInterview interview);

	void delete(IInterview interview);

	Collection<IInterview> findAllInterviews();

	Collection<IInterview> findActiveInterviewsByUser(IWorker user);
}
