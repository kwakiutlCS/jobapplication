package pt.uc.dei.aor.project.business.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import pt.uc.dei.aor.project.business.filter.PositionFilter;
import pt.uc.dei.aor.project.business.model.IPosition;
import pt.uc.dei.aor.project.business.model.IPublicationChannel;
import pt.uc.dei.aor.project.business.model.IScript;
import pt.uc.dei.aor.project.business.model.IUser;
import pt.uc.dei.aor.project.business.util.Localization;
import pt.uc.dei.aor.project.business.util.PositionState;
import pt.uc.dei.aor.project.business.util.TechnicalArea;

public interface IPositionBusinessService {

	List<IPosition> getIPositions();
	
	long codeDefiningMethod();

	List<IPosition> findPositionByTitle(String filterPosition);
	
	IPosition updatePosition(IPosition position);

	List<IPosition> findFilteredPositions(int offset, int limit, PositionFilter filter);

	List<IPosition> findFilteredPositionsByManager(int offset, int i, PositionFilter filter, IUser manager);

	IPosition createNewPosition(String title, Collection<Localization> localizations, PositionState state,
			int vacancies, Date closingDate, int sla, IUser contactPerson, String company,
			Collection<TechnicalArea> technicalAreas, String description, List<IScript> scripts,
			Collection<IPublicationChannel> channels);

	IPosition findPositionById(long posId);
	
	List<IPosition> findOpenPosition();
		
}
