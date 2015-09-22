package pt.uc.dei.aor.project.business.service;

import java.util.Collection;
import java.util.List;

import pt.uc.dei.aor.project.business.filter.ApplicationFilter;
import pt.uc.dei.aor.project.business.model.IApplication;
import pt.uc.dei.aor.project.business.model.IWorker;


public interface IApplicationBusinessService {

	IApplication findApplicationById(long id);

	List<IApplication> findApplicationsWithFilter(ApplicationFilter filter, int offset, int limit);

	IApplication changeAnalyzed(IApplication selectedApplication);

	List<IApplication> findApplicationsWithFilterByManager(ApplicationFilter filter, int offset, int limit, IWorker user);
	
}
