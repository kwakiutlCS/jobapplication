package pt.uc.dei.aor.project.business.service;

import java.util.Date;
import java.util.List;

import pt.uc.dei.aor.project.business.model.IPosition;
import pt.uc.dei.aor.project.business.model.IScript;
import pt.uc.dei.aor.project.business.util.Localization;
import pt.uc.dei.aor.project.business.util.PositionState;
import pt.uc.dei.aor.project.business.util.TechnicalArea;

public interface IPositionBusinessService {

	IPosition createNewPosition(Date openingDate, String title, List<Localization> localizations, PositionState state, int vacancies, Date closingDate, String sla, 
			String contactPerson, String company ,List<TechnicalArea> technicalAreas, String description, IScript script);

}
