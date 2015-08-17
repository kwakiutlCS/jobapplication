package pt.uc.dei.aor.project.business.service;

import java.util.Date;

import pt.uc.dei.aor.project.business.model.IPosition;
import pt.uc.dei.aor.project.business.util.Localization;

public interface IPositionBusinessService {

	IPosition createNewPosition(Date openingDate, String title, Localization localization, int vacancies, Date closingDate, String sla, 
			String contactPerson, String company ,String description);

}
