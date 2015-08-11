package pt.uc.dei.aor.project.business.service;

import java.time.LocalDate;

import pt.uc.dei.aor.project.business.model.IPosition;

public interface IPositionBusinessService {

	IPosition createNewPosition(LocalDate openingDate, int code, String title,int vacancies,
			LocalDate closingDate, String sla, String contactPerson, String company, String description);

}
