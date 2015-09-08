package pt.uc.dei.aor.project.persistence.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import pt.uc.dei.aor.project.business.model.ICandidate;
import pt.uc.dei.aor.project.business.model.ICandidateNotification;
import pt.uc.dei.aor.project.business.persistence.ICandidatePersistenceService;
import pt.uc.dei.aor.project.persistence.entity.CandidateEntity;
import pt.uc.dei.aor.project.persistence.proxy.CandidateProxy;

@Stateless
public class CandidatePersistenceService implements ICandidatePersistenceService {
	
	@PersistenceContext(unitName = "jobs")
    private EntityManager em;
	
	
	@Override
	public ICandidate save(ICandidate candidateProxy) {
		CandidateEntity entity = GenericPersistenceService.getEntity(candidateProxy);
		
		entity = em.merge(entity);
		
		return new CandidateProxy(entity);
	}


	@Override
	public ICandidateNotification notify(ICandidate person, String msg) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
