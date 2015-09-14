package pt.uc.dei.aor.project.business.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.uc.dei.aor.project.business.exception.DuplicatedUserException;
import pt.uc.dei.aor.project.business.exception.NoRoleException;
import pt.uc.dei.aor.project.business.exception.WrongPasswordException;
import pt.uc.dei.aor.project.business.model.IDegree;
import pt.uc.dei.aor.project.business.model.IModelFactory;
import pt.uc.dei.aor.project.business.model.ISchool;
import pt.uc.dei.aor.project.business.model.IWorker;
import pt.uc.dei.aor.project.business.persistence.IQualificationPersistenceService;
import pt.uc.dei.aor.project.business.persistence.IWorkerPersistenceService;
import pt.uc.dei.aor.project.business.startup.Encryptor;
import pt.uc.dei.aor.project.business.util.EmailUtil;
import pt.uc.dei.aor.project.business.util.PasswordUtil;
import pt.uc.dei.aor.project.business.util.Role;

@Stateless
public class QualificationBusinessService implements IQualificationBusinessService {
	
	private static Logger logger = LoggerFactory.getLogger(QualificationBusinessService.class);
	
	
	@Inject
	private IModelFactory factory;
	
	@Inject
	private IQualificationPersistenceService qualificationPersistence;
	
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
	
	
}
