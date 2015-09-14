package pt.uc.dei.aor.project.persistence.proxy;

import pt.uc.dei.aor.project.business.model.IQualification;
import pt.uc.dei.aor.project.persistence.entity.QualificationEntity;

public class QualificationProxy implements IQualification, IProxyToEntity<QualificationEntity> {

	private QualificationEntity entity;
	
	public QualificationProxy(QualificationEntity entity) {
		this.entity = entity != null ? entity : new QualificationEntity();
	}


	public QualificationProxy() {
		this(null);
	}

	
	public QualificationProxy(String school, String degree) {
		entity = new QualificationEntity(school, degree);
	}


	@Override
	public QualificationEntity getEntity() {
		return entity;
	}


	@Override
	public String getSchool() {
		return entity.getSchool();
	}


	@Override
	public String getDegree() {
		return entity.getDegree();
	}


	
}
