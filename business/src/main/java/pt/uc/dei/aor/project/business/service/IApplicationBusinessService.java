package pt.uc.dei.aor.project.business.service;

import java.util.Collection;
import java.util.List;

import pt.uc.dei.aor.project.business.model.IApplication;


public interface IApplicationBusinessService {

	List<IApplication> findActiveApplications();
	
}
