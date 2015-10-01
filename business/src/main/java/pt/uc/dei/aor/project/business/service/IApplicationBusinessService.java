package pt.uc.dei.aor.project.business.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.Part;

import pt.uc.dei.aor.project.business.exception.IllegalApplicationException;
import pt.uc.dei.aor.project.business.filter.ApplicationFilter;
import pt.uc.dei.aor.project.business.model.IApplication;
import pt.uc.dei.aor.project.business.model.IPosition;
import pt.uc.dei.aor.project.business.model.IUser;


public interface IApplicationBusinessService {
	
	IApplication createApplication(String tmpLetter, Part letter, String tmpCv, Part cv,
			String sourceInfo, IUser candidate, IPosition position) throws IOException;
	
	IApplication createApplication(String provisoryLetter, Part coverLetter, String provisoryCv, Part cv, String sourceInfo,
			IUser candidate) throws IOException;

	IApplication findApplicationById(long id);

	List<IApplication> findApplicationsWithFilter(ApplicationFilter filter, int offset, int limit);

	List<IApplication> findApplicationsWithFilterByManager(ApplicationFilter filter, int offset, int limit, IUser user);

	IApplication changeAnalyzed(IApplication application, boolean value);

	IApplication refuse(IApplication selectedApplication);
	
	boolean findApplicationByCandidateAndPosition(IUser candidate , IPosition position);

	void uploadCV(IApplication application, Part cv) throws IOException;

	String uploadTempLetter(Part letter) throws IOException;

	String uploadTempCV(Part cv)  throws IOException;

	IApplication addPositionToApplication(IPosition positionToAdd, IApplication selectedApplication) throws
		IllegalApplicationException;


	List<IApplication> findAllApplicationByCandidate(IUser candidate);

	boolean hasSpontaneous(IUser user);

	

}
