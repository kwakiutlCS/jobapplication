package pt.uc.dei.aor.project.persistence.proxy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import pt.uc.dei.aor.project.business.model.IAnswer;
import pt.uc.dei.aor.project.business.model.IApplication;
import pt.uc.dei.aor.project.business.model.ICandidate;
import pt.uc.dei.aor.project.business.model.IDegree;
import pt.uc.dei.aor.project.business.model.IInterview;
import pt.uc.dei.aor.project.business.model.IPosition;
import pt.uc.dei.aor.project.business.model.ISchool;
import pt.uc.dei.aor.project.business.model.IScriptEntry;
import pt.uc.dei.aor.project.persistence.entity.AnswerEntity;
import pt.uc.dei.aor.project.persistence.entity.ApplicationEntity;
import pt.uc.dei.aor.project.persistence.entity.DegreeEntity;
import pt.uc.dei.aor.project.persistence.entity.InterviewEntity;
import pt.uc.dei.aor.project.persistence.entity.PositionEntity;
import pt.uc.dei.aor.project.persistence.service.GenericPersistenceService;

public class DegreeProxy implements IDegree, IProxyToEntity<DegreeEntity> {

	private DegreeEntity entity;
	
	public DegreeProxy(DegreeEntity entity) {
		this.entity = entity != null ? entity : new DegreeEntity();
	}


	public DegreeProxy() {
		this(null);
	}


	public DegreeProxy(ISchool school, String degree, String typeDegree) {
		entity = new DegreeEntity(GenericPersistenceService.getEntity(school), degree, typeDegree);
	}


	@Override
	public DegreeEntity getEntity() {
		return entity;
	}

	
}
