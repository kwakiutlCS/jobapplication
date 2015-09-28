package pt.uc.dei.aor.project.business.service;

import java.io.IOException;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.Part;

import pt.uc.dei.aor.project.business.filter.ApplicationFilter;
import pt.uc.dei.aor.project.business.model.IApplication;
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


	@Override
	public void uploadCV(IApplication application, Part cv) throws IOException {
		String filename = cv.getSubmittedFileName();
		String dir = "cv/applications/"+application.getId();
		
		upload.delete(dir);
		upload.upload(dir, filename, cv.getInputStream());
		
		application.setCv(filename);
		applicationPersistence.save(application);
	}
}
