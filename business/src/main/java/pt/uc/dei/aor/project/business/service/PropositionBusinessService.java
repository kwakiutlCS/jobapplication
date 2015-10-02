package pt.uc.dei.aor.project.business.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import pt.uc.dei.aor.project.business.model.IApplication;
import pt.uc.dei.aor.project.business.model.IModelFactory;
import pt.uc.dei.aor.project.business.model.IPosition;
import pt.uc.dei.aor.project.business.model.IProposition;
import pt.uc.dei.aor.project.business.persistence.IApplicationPersistenceService;
import pt.uc.dei.aor.project.business.persistence.IPositionPersistenceService;
import pt.uc.dei.aor.project.business.util.PositionState;

@Stateless
public class PropositionBusinessService implements IPropositionBusinessService {

	@Inject
	private IModelFactory factory;
	
	@Inject
	private IApplicationPersistenceService applicationPersistence;
	
	@Inject 
	private IPositionPersistenceService positionPersistence;
	
	@Override
	public void sendProposition(IApplication application) {
		IProposition proposition = factory.proposition();
		application.sendProposition(proposition);
		applicationPersistence.save(application);
		
		//application = applicationPersistence.sendProposition(application, proposition);
		
		IPosition position = application.getPosition();
		List<IApplication> applications = applicationPersistence.findAllApplicationsByPosition(position);
		
		int counter = 0;
		for (IApplication a : applications) {
			if (a.isProposed()) counter++;
		}
		
		if (counter >= position.getVacancies()) {
			position.setState(PositionState.CLOSED);
			positionPersistence.save(position);
		}
	}

}
