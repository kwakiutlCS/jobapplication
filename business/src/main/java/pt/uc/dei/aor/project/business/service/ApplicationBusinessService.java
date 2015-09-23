package pt.uc.dei.aor.project.business.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import pt.uc.dei.aor.project.business.filter.ApplicationFilter;
import pt.uc.dei.aor.project.business.model.IApplication;
import pt.uc.dei.aor.project.business.model.IModelFactory;
import pt.uc.dei.aor.project.business.model.IWorker;
import pt.uc.dei.aor.project.business.persistence.IApplicationPersistenceService;

@Stateless
public class ApplicationBusinessService implements IApplicationBusinessService {

	
	@Inject
	private IApplicationPersistenceService applicationPersistence;
		
	@Override
	public IApplication findApplicationById(long id) {
		IApplication application = applicationPersistence.find(id);
		return application;
	}

	@Override
	public List<IApplication> findApplicationsWithFilter(ApplicationFilter filter, int offset, int limit) {
		return applicationPersistence.findApplicationsWithFilter(filter, offset, limit, null);
	}

	@Override
	public IApplication changeAnalyzed(IApplication application, boolean value) {
		application.changeAnalyzed(value);
		return applicationPersistence.save(application);
	}

	@Override
	public List<IApplication> findApplicationsWithFilterByManager(ApplicationFilter filter, int offset, int limit,
			IWorker user) {
		return applicationPersistence.findApplicationsWithFilter(filter, offset, limit, user);
	}

	@Override
	public IApplication refuse(IApplication selectedApplication) {
		selectedApplication.refuse();
		return applicationPersistence.save(selectedApplication);
	}
	
}
