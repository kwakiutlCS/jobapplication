package pt.uc.dei.aor.project.business.service;

import pt.uc.dei.aor.project.business.model.IDegree;
import pt.uc.dei.aor.project.business.model.ISchool;


public interface IQualificationBusinessService {

	boolean isPopulated();
	
	void addQualification(String qualification);
	
	ISchool addSchool(ISchool school);
	
	IDegree addDegree(IDegree degree);
	
}
