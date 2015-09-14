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

	
	@Override
	public QualificationEntity getEntity() {
		return entity;
	}


	
}
