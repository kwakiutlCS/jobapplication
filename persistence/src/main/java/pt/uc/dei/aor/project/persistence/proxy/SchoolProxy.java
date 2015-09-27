package pt.uc.dei.aor.project.persistence.proxy;

import pt.uc.dei.aor.project.business.model.ISchool;
import pt.uc.dei.aor.project.persistence.entity.InstitutionEntity;

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
