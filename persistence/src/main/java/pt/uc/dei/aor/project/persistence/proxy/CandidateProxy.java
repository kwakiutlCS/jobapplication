package pt.uc.dei.aor.project.persistence.proxy;

import java.util.LinkedList;
import java.util.List;

import pt.uc.dei.aor.project.business.model.ICandidate;
import pt.uc.dei.aor.project.business.model.IWorker;
import pt.uc.dei.aor.project.persistence.entity.CandidateEntity;
import pt.uc.dei.aor.project.persistence.entity.WorkerEntity;
import pt.uc.dei.aor.project.persistence.util.Role;

public class CandidateProxy implements ICandidate, IProxyToEntity<CandidateEntity> {

	private CandidateEntity entity;
	
	public CandidateProxy(CandidateEntity entity) {
		this.entity = entity != null ? entity : new CandidateEntity();
	}
	
	public CandidateProxy(String login, String email, String password, String name, String surname) {
		entity = new CandidateEntity(login, email, password, name, surname);
	}

	@Override
	public CandidateEntity getEntity() {
		return entity;
	}

	
}
