package pt.uc.dei.aor.project.persistence.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import pt.uc.dei.aor.project.business.model.ICandidate;
import pt.uc.dei.aor.project.business.persistence.ICandidatePersistenceService;
import pt.uc.dei.aor.project.persistence.entity.CandidateEntity;
import pt.uc.dei.aor.project.persistence.proxy.CandidateProxy;
import pt.uc.dei.aor.project.persistence.proxy.IProxyToEntity;

@Stateless
public class CandidatePersistenceService implements ICandidatePersistenceService {
	
	@PersistenceContext(unitName = "jobs")
    private EntityManager em;
	
	
	@Override
	public ICandidate save(ICandidate candidateProxy) {
		CandidateEntity entity = getEntity(candidateProxy);
		
		entity = em.merge(entity);
		
		return new CandidateProxy(entity);
	}
	
	@SuppressWarnings("unchecked")
    private CandidateEntity getEntity(ICandidate candidateProxy) {
        if (candidateProxy instanceof IProxyToEntity<?>) {
            return ((IProxyToEntity<CandidateEntity>) candidateProxy).getEntity();
        }

        throw new IllegalStateException();
    }




	
}
