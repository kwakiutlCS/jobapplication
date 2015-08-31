package pt.uc.dei.aor.project.business.service;

import java.util.Collection;

import pt.uc.dei.aor.project.business.model.IApplication;


public interface IApplicationBusinessService {

	Collection<IApplication> findActiveApplications();
	
}
