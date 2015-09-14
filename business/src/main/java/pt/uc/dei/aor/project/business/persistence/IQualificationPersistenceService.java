package pt.uc.dei.aor.project.business.persistence;

import pt.uc.dei.aor.project.business.model.IDegree;
import pt.uc.dei.aor.project.business.model.ISchool;

public interface IQualificationPersistenceService {

	ISchool addSchool(ISchool school);
	
	IDegree addDegree(IDegree degree);

	long countSchools();

	ISchool findSchoolByName(String name);
	
}
