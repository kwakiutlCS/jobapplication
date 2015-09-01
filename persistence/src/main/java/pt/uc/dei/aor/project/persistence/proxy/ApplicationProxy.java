package pt.uc.dei.aor.project.persistence.proxy;

import pt.uc.dei.aor.project.business.model.IApplication;
import pt.uc.dei.aor.project.business.model.ICandidate;
import pt.uc.dei.aor.project.business.model.IPosition;
import pt.uc.dei.aor.project.persistence.entity.ApplicationEntity;

public class ApplicationProxy implements IApplication, IProxyToEntity<ApplicationEntity> {

	private ApplicationEntity entity;
	
	public ApplicationProxy(ApplicationEntity entity) {
		this.entity = entity != null ? entity : new ApplicationEntity();
	}


	public ApplicationProxy() {
		this(null);
	}


	@Override
	public ApplicationEntity getEntity() {
		return entity;
	}


	@Override
	public ICandidate getCandidate() {
		// TODO Auto-generated method stub
		// precisarei de um método que devolva o candidato desta candidatura
		
		// neste momento devolve null
		return null;
	}


	@Override
	public IPosition getPosition() {
		// TODO Auto-generated method stub
		// precisarei de um método que devolva a position
		
		// neste momento devolve null
		return null;
	}
	
}
