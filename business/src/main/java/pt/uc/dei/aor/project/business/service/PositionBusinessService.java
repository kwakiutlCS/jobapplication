package pt.uc.dei.aor.project.business.service;

import java.time.LocalDate;

import javax.ejb.Stateless;
import javax.inject.Inject;

import pt.uc.dei.aor.project.business.model.IModelFactory;
import pt.uc.dei.aor.project.business.model.IPosition;
import pt.uc.dei.aor.project.business.persistence.IPositionPersistenceService;


@Stateless
public class PositionBusinessService implements IPositionBusinessService {

	@Inject
	private IModelFactory factory;
	
	@Inject
	private IPositionPersistenceService positionPersistence;
	
	
	@Override
	public IPosition createNewPosition(LocalDate openingDate, String title, int vacancies, LocalDate closingDate, String sla,
			String contactPerson, String company, String description) {
		
		IPosition position = factory.position(openingDate, title, vacancies, closingDate, sla, 
				contactPerson, company, description);
		
		return positionPersistence.save(position);
	}
	


}
