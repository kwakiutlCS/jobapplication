package pt.uc.dei.aor.project.persistence.proxy;

import pt.uc.dei.aor.project.business.model.IDegree;
import pt.uc.dei.aor.project.business.model.ISchool;
import pt.uc.dei.aor.project.persistence.entity.DegreeEntity;
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


	@Override
	public String getName() {
		return entity.getName();
	}


	@Override
	public String getCompleteName() {
		if (entity.getType().contains("Licenciatura")) {
			return "Licenciatura - "+getName();
		}
		else if (entity.getType().contains("Mestrado")) {
			return "Mestrado - "+getName();
		}
		else if (entity.getType().contains("Doutoramento")) {
			return "Doutoramento - "+getName();
		}
		else return getName();
	}

	
}
