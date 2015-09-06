package pt.uc.dei.aor.project.business.persistence;

import java.util.Collection;
import java.util.List;

import pt.uc.dei.aor.project.business.filter.InterviewFilter;
import pt.uc.dei.aor.project.business.model.IInterview;
import pt.uc.dei.aor.project.business.model.IWorker;

public interface IInterviewPersistenceService {

	IInterview save(IInterview interview);

	void delete(IInterview interview);

	Collection<IInterview> findAllInterviews();

	List<IInterview> findActiveInterviewsByUser(IWorker user);

	IInterview findInterviewById(long id);

	IInterview findInterview(IInterview interview);

	List<IInterview> findInterviews(int offset, int limit);

	List<IInterview> findInterviewsWithFilter(int offset, int limit, InterviewFilter filter);
}
