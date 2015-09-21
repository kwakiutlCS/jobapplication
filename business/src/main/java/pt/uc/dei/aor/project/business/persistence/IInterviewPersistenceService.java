package pt.uc.dei.aor.project.business.persistence;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import pt.uc.dei.aor.project.business.filter.InterviewFilter;
import pt.uc.dei.aor.project.business.model.IApplication;
import pt.uc.dei.aor.project.business.model.ICandidate;
import pt.uc.dei.aor.project.business.model.IInterview;
import pt.uc.dei.aor.project.business.model.IWorker;

public interface IInterviewPersistenceService {

	IInterview save(IInterview interview);

	void delete(IInterview interview);

	Collection<IInterview> findAllInterviews();

	List<IInterview> findActiveInterviewsByUser(IWorker user);

	IInterview findInterviewById(long id);

	IInterview findInterview(IInterview interview);

	List<IInterview> findInterviewsWithFilter(int offset, int limit, InterviewFilter filter);

	List<IInterview> findPastInterviewsByUser(ICandidate candidate, IApplication application, Date date);

	List<IInterview> findPastInterviews(IApplication application);

	List<IInterview> findPresentInterviews(IApplication application);

	List<IInterview> findFutureInterviews(IApplication application);

	boolean isCompleted(IInterview interview);
}
