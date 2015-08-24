package pt.uc.dei.aor.project.business.service;

import java.util.Collection;
import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

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


	@Override
	public IPosition createNewPosition(String code, Date openingDate,
			String title, Collection<Localization> localizations,
			PositionState state, int vacancies, Date closingDate, String sla,
			String contactPerson, String company,
			Collection<TechnicalArea> technicalAreas, String description,
			IScript script, Collection<IPublicationChannel> channels) {

		IPosition position = factory.position(code, openingDate, title, localizations, state, vacancies, closingDate, sla, 
				contactPerson, company, technicalAreas, description, script, channels);

		return positionPersistence.save(position);

	}


}
