package pt.uc.dei.aor.project.business.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import pt.uc.dei.aor.project.business.model.IPosition;
import pt.uc.dei.aor.project.business.model.IPublicationChannel;
import pt.uc.dei.aor.project.business.model.IScript;
import pt.uc.dei.aor.project.business.util.Localization;
import pt.uc.dei.aor.project.business.util.PositionState;
import pt.uc.dei.aor.project.business.util.TechnicalArea;

public interface IPositionBusinessService {

	IPosition createNewPosition(String title, Collection<Localization> localizations, PositionState state, int vacancies, Date closingDate, int sla, 
			String contactPerson, String company , Collection<TechnicalArea> technicalAreas, String description, IScript script, Collection<IPublicationChannel> channels);

	List<IPosition> getIPositions();
	
	long codeDefiningMethod();

	List<IPosition> findPositionByTitle(String filterPosition);
	
	IPosition updatePosition(IPosition position);
		
}
