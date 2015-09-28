package pt.uc.dei.aor.project.business.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.uc.dei.aor.project.business.model.IDegree;
import pt.uc.dei.aor.project.business.model.IModelFactory;
import pt.uc.dei.aor.project.business.model.IQualification;
import pt.uc.dei.aor.project.business.model.ISchool;
import pt.uc.dei.aor.project.business.model.IUser;
import pt.uc.dei.aor.project.business.persistence.IQualificationPersistenceService;
import pt.uc.dei.aor.project.business.persistence.IUserPersistenceService;

@Stateless
public class QualificationBusinessService implements IQualificationBusinessService {

	private static Logger logger = LoggerFactory.getLogger(QualificationBusinessService.class);


	@Inject
	private IModelFactory factory;

	@Inject
	private IQualificationPersistenceService qualificationPersistence;

	@Inject
	private IUserPersistenceService userPersistence;

	
	@Override
	public void addQualification(String qualification) {
		String[] fields = qualification.split(":");
		ISchool school = factory.school(fields[0].trim());
		school = addSchool(school);

		IDegree degree = factory.degree(school, fields[1].trim(), fields[2].trim());
		addDegree(degree);
	}


	@Override
	public ISchool addSchool(ISchool school) {
		ISchool existing = qualificationPersistence.findSchoolByName(school.getName());
		if (existing != null)
			return existing;

		return qualificationPersistence.addSchool(school);
	}

	@Override
	public IDegree addDegree(IDegree degree) {
		return qualificationPersistence.addDegree(degree);
	}

	@Override
	public boolean isPopulated() {
		return qualificationPersistence.countSchools() > 0;
	}

	@Override
	public List<String> listSchools(String text) {
		List<ISchool> schools = qualificationPersistence.filterSchoolsByName(text);

		List<String> result = new ArrayList<>();

		for (ISchool school : schools) {
			result.add(school.getName());
		}

		return result;
	}

	@Override
	public List<String> listDegrees(String school) {
		ISchool proxy = qualificationPersistence.findSchoolByName(school);
		if (proxy == null) return null;

		List<IDegree> degrees = qualificationPersistence.findDegreeBySchool(proxy);

		List<String> result = new ArrayList<>();

		for (IDegree degree : degrees) {
			result.add(degree.getCompleteName());
		}

		return result;
	}

	@Override
	public void addQualification(IUser user, String school, String degree) {
		IQualification qualification = factory.qualification(school, degree);
		user.addQualification(qualification);

		userPersistence.save(user);
	}

	@Override
	public void removeQualification(IUser user, IQualification qualification) {
		user.removeQualification(qualification);

		userPersistence.save(user);
	}

	
	@Override
	public IQualification addQualification(String school, String degree) {
		return factory.qualification(school, degree);
	}

	
}
