package pt.uc.dei.aor.project.business.service;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import pt.uc.dei.aor.project.business.filter.ApplicationFilter;
import pt.uc.dei.aor.project.business.model.IApplication;
import pt.uc.dei.aor.project.business.model.IModelFactory;
import pt.uc.dei.aor.project.business.model.IPosition;
import pt.uc.dei.aor.project.business.model.IUser;
import pt.uc.dei.aor.project.business.persistence.IApplicationPersistenceService;
import pt.uc.dei.aor.project.business.util.UploadUtil;

@Stateless
public class ApplicationBusinessService implements IApplicationBusinessService {

	
	@Inject
	private IApplicationPersistenceService applicationPersistence;
	
	@Inject
	private UploadUtil upload;
	
	@Inject
	private IModelFactory factory;
	
	
	@Override
	public IApplication createApplication(String coverLetter,
			String sourceInfo, IUser candidate, IPosition position) {
	
		Date date = new Date();
		
		IApplication application = factory.application(coverLetter, sourceInfo, date, candidate, position);
		
		return applicationPersistence.save(application);
	}

		
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
			IUser worker) {
		return applicationPersistence.findApplicationsWithFilter(filter, offset, limit, worker);
	}

	@Override
	public IApplication refuse(IApplication selectedApplication) {
		selectedApplication.refuse();
		return applicationPersistence.save(selectedApplication);
	}

	@Override
	public boolean findApplicationByCandidateAndPosition(IUser candidate,
			IPosition position) {
		return applicationPersistence.findApplicationbyCandidateAndPosition(candidate, position);
	}


	
}
