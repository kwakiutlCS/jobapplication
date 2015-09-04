package pt.uc.dei.aor.project.business.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import pt.uc.dei.aor.project.business.model.IApplication;
import pt.uc.dei.aor.project.business.model.IModelFactory;
import pt.uc.dei.aor.project.business.persistence.IApplicationPersistenceService;

@Stateless
public class ApplicationBusinessService implements IApplicationBusinessService {

	@Inject
	private IModelFactory factory;
	
	@Inject
	private IApplicationPersistenceService applicationPersistence;
		
	@Override
	public List<IApplication> findActiveApplications() {
		// TODO 
		// eventualmente precisarei desta função para implementar as interviews
		// deverá devolver a lista de applications ainda sem resolução para que o admin
		// possa marcar uma entrevista
		
		// neste momento devolve provisoriamente uma lista de applications vazias
		return applicationPersistence.dummyQuery();
	}

	@Override
	public IApplication findApplicationById(long id) {
		System.out.println("business find application by id");
		IApplication application = applicationPersistence.find(id);
		System.out.println("business: "+application.getInterviews());
		return application;
	}
	
}
