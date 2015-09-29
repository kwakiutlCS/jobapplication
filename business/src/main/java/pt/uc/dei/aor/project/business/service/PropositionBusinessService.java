package pt.uc.dei.aor.project.business.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import pt.uc.dei.aor.project.business.model.IApplication;
import pt.uc.dei.aor.project.business.model.IModelFactory;
import pt.uc.dei.aor.project.business.model.IProposition;
import pt.uc.dei.aor.project.business.persistence.IApplicationPersistenceService;

@Stateless
public class PropositionBusinessService implements IPropositionBusinessService {

	@Inject
	private IModelFactory factory;
	
	@Inject
	private IApplicationPersistenceService applicationPersistence;
	
	@Override
	public void sendProposition(IApplication application) {
		IProposition proposition = factory.proposition();
		application.sendProposition(proposition);
		applicationPersistence.save(application);
	}

}
