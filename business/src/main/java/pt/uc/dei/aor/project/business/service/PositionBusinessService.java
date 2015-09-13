package pt.uc.dei.aor.project.business.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import pt.uc.dei.aor.project.business.filter.PositionFilter;
import pt.uc.dei.aor.project.business.model.IModelFactory;
import pt.uc.dei.aor.project.business.model.IPosition;
import pt.uc.dei.aor.project.business.model.IPublicationChannel;
import pt.uc.dei.aor.project.business.model.IScript;
import pt.uc.dei.aor.project.business.persistence.IPositionPersistenceService;
import pt.uc.dei.aor.project.business.util.Localization;
import pt.uc.dei.aor.project.business.util.PositionState;
import pt.uc.dei.aor.project.business.util.TechnicalArea;


@Stateless
public class PositionBusinessService implements IPositionBusinessService {

	@Inject
	private IModelFactory factory;

	@Inject
	private IPositionPersistenceService positionPersistence;

	private long code;
	private Date openingDate;

	@Override
	public IPosition createNewPosition(String title, Collection<Localization> localizations,
			PositionState state, int vacancies, Date closingDate, int sla,
			String contactPerson, String company,
			Collection<TechnicalArea> technicalAreas, String description,
			IScript script, Collection<IPublicationChannel> channels) {
		
		openingDate = new Date();		
		
		code = codeDefiningMethod(); 


		IPosition position = factory.position(code, openingDate, title, localizations, state, vacancies, closingDate, sla, 
				contactPerson, company, technicalAreas, description, script, channels);

		return positionPersistence.save(position);
	}


	@Override
	public List<IPosition> getIPositions() {
	
		return positionPersistence.findAllPositions();
	}


	@Override
	public long codeDefiningMethod() {

		code =positionPersistence.findBiggestCode()+1;
				
		return code;
	}


	@Override
	public List<IPosition> findPositionByTitle(String title) {
		return positionPersistence.findPositionByTitle(title);
	}


	@Override
	public IPosition updatePosition(IPosition position) {
		return positionPersistence.save(position);
	}


	@Override
	public List<IPosition> findFilteredPositions(int offset, int limit, PositionFilter filter) {
		return positionPersistence.findFilteredPositions(offset, limit, filter);
	}



}
