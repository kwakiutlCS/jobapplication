package pt.uc.dei.aor.project.business.service;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import pt.uc.dei.aor.project.business.model.IModelFactory;
import pt.uc.dei.aor.project.business.model.IPosition;
import pt.uc.dei.aor.project.business.persistence.IPositionPersistenceService;
import pt.uc.dei.aor.project.business.util.Localization;


@Stateless
public class PositionBusinessService implements IPositionBusinessService {

	@Inject
	private IModelFactory factory;
	
	@Inject
	private IPositionPersistenceService positionPersistence;
	
	
	
	
	@Override
	public IPosition createNewPosition(Date openingDate, String title, Localization localization, int vacancies, Date closingDate, String sla, 
			String contactPerson, String company ,String description) {
		
		IPosition position = factory.position(openingDate, title, localization,vacancies, closingDate, sla, 
				contactPerson, company, description);
		
		return positionPersistence.save(position);
	}
	


}
