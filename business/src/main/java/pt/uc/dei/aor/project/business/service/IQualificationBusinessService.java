package pt.uc.dei.aor.project.business.service;

import java.util.List;

import pt.uc.dei.aor.project.business.model.ICandidate;
import pt.uc.dei.aor.project.business.model.IDegree;
import pt.uc.dei.aor.project.business.model.IQualification;
import pt.uc.dei.aor.project.business.model.ISchool;
import pt.uc.dei.aor.project.business.model.IWorker;


public interface IQualificationBusinessService {

	boolean isPopulated();
	
	void addQualification(String qualification);
	
	ISchool addSchool(ISchool school);
	
	IDegree addDegree(IDegree degree);

	List<String> listSchools(String text);

	List<String> listDegrees(String school);

	void addQualification(IWorker user, String school, String degree);

	void removeQualification(IWorker user, IQualification qualification);
	
	void addQualification(ICandidate user, String school, String degree);

	void removeQualification(ICandidate user, IQualification qualification);
}
