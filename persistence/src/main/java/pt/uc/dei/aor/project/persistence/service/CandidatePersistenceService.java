package pt.uc.dei.aor.project.persistence.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

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
		
		System.out.println("Candidate proxy "+candidateProxy);
		
		CandidateEntity entity = GenericPersistenceService.getEntity(candidateProxy);

		entity = em.merge(entity);
		
		System.out.println("merge update --- user"+candidateProxy);

		return new CandidateProxy(entity);
	}


	@Override
	public ICandidateNotification notify(ICandidate person, String msg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ICandidate> findAll() {

		TypedQuery<CandidateEntity> q =  em.createNamedQuery("candidate.findAll", CandidateEntity.class);

		List<CandidateEntity> candidates = q.getResultList();

		List<ICandidate> icandidates = new ArrayList<ICandidate>();

		for(CandidateEntity c : candidates){
			icandidates.add(new CandidateProxy(c));
		}
		return icandidates;
	}


	@Override
	public boolean findCandidateByEmailorLogin(String email, String login) {

		TypedQuery<CandidateEntity> q = em.createNamedQuery("candidate.findCandidateByEmailorLogin",CandidateEntity.class );
		q.setParameter("email", email);
		q.setParameter("login", login);

		List<CandidateEntity> candidates = q.getResultList();

		if(candidates.isEmpty()){
			return false;
		}
		return true;
	}


	@Override
	public void delete(ICandidate candidate) {
		CandidateEntity entity = GenericPersistenceService.getEntity(candidate); 

		em.remove(em.merge(entity));
	}


	@Override
	public ICandidate getCandidateByEmail(String email) {
		
		TypedQuery<CandidateEntity> q =  em.createNamedQuery("candidate.findCandidateByEmail", CandidateEntity.class);
		q.setParameter("email", email);

		List<CandidateEntity> candidates = q.getResultList();

		if(candidates.isEmpty())
			return null;
		
		return new CandidateProxy(candidates.get(0));
	}


	@Override
	public ICandidate getCandidateByLogin(String login) {
		
		TypedQuery<CandidateEntity> q =  em.createNamedQuery("candidate.findCandidateByLogin", CandidateEntity.class);
		q.setParameter("login", login);
		
		List<CandidateEntity> candidates = q.getResultList();
		
		if(candidates.isEmpty())
			return null;

		return new CandidateProxy(candidates.get(0));
	}

}
