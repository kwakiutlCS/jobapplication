package pt.uc.dei.aor.project.business.persistence;

import java.util.List;

import pt.uc.dei.aor.project.business.filter.ApplicationFilter;
import pt.uc.dei.aor.project.business.model.IApplication;
import pt.uc.dei.aor.project.business.model.ICandidate;
import pt.uc.dei.aor.project.business.model.IPosition;
import pt.uc.dei.aor.project.business.model.IWorker;

public interface IApplicationPersistenceService {

	IApplication save(IApplication application);

	IApplication find(long id);

	List<IApplication> findApplicationsWithFilter(ApplicationFilter filter, int offset, int limit, IWorker manager);


	long findApplicationsByPosition(IPosition p);

	boolean findApplicationbyCandidateAndPosition(ICandidate candidate,
			IPosition position);


	
}
