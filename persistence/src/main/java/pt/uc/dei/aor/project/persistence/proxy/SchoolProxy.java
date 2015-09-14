package pt.uc.dei.aor.project.persistence.proxy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import pt.uc.dei.aor.project.business.model.IAnswer;
import pt.uc.dei.aor.project.business.model.IApplication;
import pt.uc.dei.aor.project.business.model.ICandidate;
import pt.uc.dei.aor.project.business.model.IInterview;
import pt.uc.dei.aor.project.business.model.IPosition;
import pt.uc.dei.aor.project.business.model.ISchool;
import pt.uc.dei.aor.project.business.model.IScriptEntry;
import pt.uc.dei.aor.project.persistence.entity.AnswerEntity;
import pt.uc.dei.aor.project.persistence.entity.ApplicationEntity;
import pt.uc.dei.aor.project.persistence.entity.InstitutionEntity;
import pt.uc.dei.aor.project.persistence.entity.InterviewEntity;
import pt.uc.dei.aor.project.persistence.entity.PositionEntity;
import pt.uc.dei.aor.project.persistence.service.GenericPersistenceService;

public class SchoolProxy implements ISchool, IProxyToEntity<InstitutionEntity> {

	private InstitutionEntity entity;
	
	public SchoolProxy(InstitutionEntity entity) {
		this.entity = entity != null ? entity : new InstitutionEntity();
	}


	public SchoolProxy() {
		this(new InstitutionEntity());
	}

	
	
	public SchoolProxy(String school) {
		this.entity = new InstitutionEntity(school);
	}


	@Override
	public InstitutionEntity getEntity() {
		return entity;
	}


	@Override
	public String getName() {
		return entity.getName();
	}


	
}
