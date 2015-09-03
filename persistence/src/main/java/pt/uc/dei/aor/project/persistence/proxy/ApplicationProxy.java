package pt.uc.dei.aor.project.persistence.proxy;

import java.util.ArrayList;
import java.util.List;

import pt.uc.dei.aor.project.business.model.IApplication;
import pt.uc.dei.aor.project.business.model.ICandidate;
import pt.uc.dei.aor.project.business.model.IInterview;
import pt.uc.dei.aor.project.business.model.IPosition;
import pt.uc.dei.aor.project.persistence.entity.ApplicationEntity;
import pt.uc.dei.aor.project.persistence.entity.InterviewEntity;

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


	@Override
	public List<IInterview> getInterviews() {
		List<IInterview> proxies = new ArrayList<>();
		List<InterviewEntity> entities = entity.getInterviews();
		System.out.println("interviews");
		System.out.println(entities);
		for (InterviewEntity ie : entities) {
			proxies.add(new InterviewProxy(ie));
		}
		
		return proxies;
	}


	@Override
	public long getId() {
		return entity.getId();
	}
	
}
