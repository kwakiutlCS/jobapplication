package pt.uc.dei.aor.project.business.service;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import pt.uc.dei.aor.project.business.model.IModelFactory;
import pt.uc.dei.aor.project.business.model.IPosition;
import pt.uc.dei.aor.project.business.persistence.IPositionPersistenceService;
import pt.uc.dei.aor.project.business.util.Localization;
import pt.uc.dei.aor.project.business.util.PositionState;


@Stateless
public class PositionBusinessService implements IPositionBusinessService {

	@Inject
	private IModelFactory factory;
	
	@Inject
	private IPositionPersistenceService positionPersistence;
	
		
	@Override
	public IPosition createNewPosition(Date openingDate, String title, Localization localization, PositionState state, int vacancies, Date closingDate, String sla, 
			String contactPerson, String company , List<String> technicalAreas, String description) {
		
		IPosition position = factory.position(openingDate, title, localization, state, vacancies, closingDate, sla, 
				contactPerson, company, technicalAreas, description);
		
		return positionPersistence.save(position);
	}


}
