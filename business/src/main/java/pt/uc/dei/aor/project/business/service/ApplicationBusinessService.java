package pt.uc.dei.aor.project.business.service;


import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.Part;

import pt.uc.dei.aor.project.business.exception.IllegalApplicationException;
import pt.uc.dei.aor.project.business.exception.IllegalPositionAssignmentException;
import pt.uc.dei.aor.project.business.filter.ApplicationFilter;
import pt.uc.dei.aor.project.business.model.IApplication;
import pt.uc.dei.aor.project.business.model.IModelFactory;
import pt.uc.dei.aor.project.business.model.IPosition;
import pt.uc.dei.aor.project.business.model.IUser;
import pt.uc.dei.aor.project.business.persistence.IApplicationPersistenceService;
import pt.uc.dei.aor.project.business.util.EmailUtil;
import pt.uc.dei.aor.project.business.util.UploadUtil;

@Stateless
public class ApplicationBusinessService implements IApplicationBusinessService {

	
	@Inject
	private IApplicationPersistenceService applicationPersistence;
	
	@Inject
	private UploadUtil upload;
	
	@Inject
	private IModelFactory factory;
	
	@Inject
	private EmailUtil emailUtil;

	@Inject
	private INotificationBusinessService notificationService;
	
	
	@Override
	public IApplication createApplication(String tmpLetter, Part letter, String tmpCv, Part cv,
			String sourceInfo, IUser candidate, IPosition position) throws IOException, IllegalApplicationException {
	
		if (applicationPersistence.findApplicationbyCandidateAndPosition(candidate, position))
			throw new IllegalApplicationException();
			
		Date date = new Date();
		
		String cvName;
		if (cv != null) {
			cvName = cv.getSubmittedFileName();
		}
		else {
			cvName = candidate.getCv();
		}
		
		IApplication application = factory.application(letter.getSubmittedFileName(), 
				cvName,sourceInfo, date, candidate, position);
		
		// notify user
		IUser user = position.getContactPerson();
		String title = "Application to a position created";
		String msg = "Position  "+
				position.getTitle()+" had an application "+
				"\nCandidate: "+candidate.getFullName();

		String msgEmail = "<h1>"+position.getCompany()+"</h1>"+
				"<p>Position "+position.getTitle()+" had an application</p>"+
				"<p>\nCandidate: "+candidate.getFullName()+"</p>";


		notificationService.notify(user, msg, title);

		emailUtil.send(user.getEmail(), msgEmail, title, null);
		
		return createApplication(application, tmpLetter, letter, tmpCv, cv, candidate);
	}

	
	@Override
	public IApplication createApplication(String tmpLetter, Part letter, String tmpCv, Part cv,
			String sourceInfo, IUser candidate) throws IOException {
		
		Date date = new Date();
		
		String cvName;
		if (cv != null) {
			cvName = cv.getSubmittedFileName();
		}
		else {
			cvName = candidate.getCv();
		}
		
		IApplication application = factory.application(letter.getSubmittedFileName(), 
				cvName,sourceInfo, date, candidate);
		
		return createApplication(application, tmpLetter, letter, tmpCv, cv, candidate);
	}
	
	
	private IApplication createApplication(IApplication application, String tmpLetter, Part letter, 
			String tmpCv, Part cv, IUser candidate) throws IOException {
		
		application = applicationPersistence.save(application);
		
		upload.mv("letter/temp/"+tmpLetter, "letter/"+application.getId(), letter.getSubmittedFileName());
		if (cv != null) {
			upload.mv("cv/temp/"+tmpCv, "cv/applications/"+application.getId(), cv.getSubmittedFileName());
		}
		else {
			upload.cp("cv/users/"+candidate.getLogin(), "cv/applications/"+application.getId(), 
					candidate.getCv());
		}
		
		return application;
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


	@Override
	public void uploadCV(IApplication application, Part cv) throws IOException {
		String filename = cv.getSubmittedFileName();
		String dir = "cv/applications/"+application.getId();
		
		upload.delete(dir);
		upload.upload(dir, filename, cv.getInputStream());
		
		application.setCv(filename);
		applicationPersistence.save(application);
	}


	@Override
	public String uploadTempLetter(Part letter) throws IOException {
		return uploadTemp(letter, "letter/temp/");
	}


	@Override
	public String uploadTempCV(Part cv) throws IOException {
		return uploadTemp(cv, "cv/temp/");
	}
	
	private String uploadTemp(Part file, String path) throws IOException {
		Random rand = new Random();
		
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < 11; i++) {
			s.append(""+rand.nextInt(10));
		}
		
		String tmpDir = s.toString();
		
		String filename = file.getSubmittedFileName();
		String dir = path+tmpDir;
		
		upload.delete(dir);
		upload.upload(dir, filename, file.getInputStream());
		
		return tmpDir;
	}


	@Override
	public IApplication addPositionToApplication(IPosition positionToAdd, IApplication selectedApplication)
			throws IllegalApplicationException, IllegalPositionAssignmentException {
		if (applicationPersistence
				.findApplicationbyCandidateAndPosition(selectedApplication.getCandidate(), positionToAdd)) 
			throw new IllegalPositionAssignmentException();
		
		selectedApplication.addPositon(positionToAdd);
		
		// notify user
		IUser user = positionToAdd.getContactPerson();
		String title = "Application added to a position";
		String msg = "Application added to a position "+
				positionToAdd.getTitle()+" had an application associated"+
				"\nCandidate: "+selectedApplication.getCandidate().getFullName();

		String msgEmail = "<h1>"+positionToAdd.getCompany()+"</h1>"+
				"<p>Application added to a position </p>"+
				"<p>\nCandidate: "+selectedApplication.getCandidate().getFullName()+"</p>";
				

		notificationService.notify(user, msg, title);

		emailUtil.send(user.getEmail(), msgEmail, title, null);
		
		
		return applicationPersistence.save(selectedApplication);
	}


	@Override
	public List<IApplication> findAllApplicationByCandidate(IUser candidate) {
		return applicationPersistence.findAllApplicationsByCandidate(candidate);
	}

	public boolean hasSpontaneous(IUser user) {
		return applicationPersistence.hasSpontaneous(user);
	}


	@Override
	public IApplication acceptProposition(IApplication selectedApplication) {
		selectedApplication.acceptByCandidate();
		return applicationPersistence.save(selectedApplication);
	}


	@Override
	public IApplication refuseProposition(IApplication selectedApplication) {
		selectedApplication.refuseByCandidate();
		return applicationPersistence.save(selectedApplication);
	}
}
