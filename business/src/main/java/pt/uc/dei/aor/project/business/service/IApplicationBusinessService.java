package pt.uc.dei.aor.project.business.service;

import java.util.List;

import pt.uc.dei.aor.project.business.filter.ApplicationFilter;
import pt.uc.dei.aor.project.business.model.IApplication;
import pt.uc.dei.aor.project.business.model.ICandidate;
import pt.uc.dei.aor.project.business.model.IPosition;
import pt.uc.dei.aor.project.business.model.IWorker;


public interface IApplicationBusinessService {

	IApplication findApplicationById(long id);

	List<IApplication> findApplicationsWithFilter(ApplicationFilter filter, int offset, int limit);

	List<IApplication> findApplicationsWithFilterByManager(ApplicationFilter filter, int offset, int limit, IWorker user);

	IApplication changeAnalyzed(IApplication application, boolean value);

	IApplication refuse(IApplication selectedApplication);
	
	boolean findApplicationByCandidateAndPosition(ICandidate candidate , IPosition position);

//	void uploadCV(ICandidate user, Part cv) throws IOException;
}
