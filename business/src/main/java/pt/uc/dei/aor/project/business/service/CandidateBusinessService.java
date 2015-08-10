package pt.uc.dei.aor.project.business.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import pt.uc.dei.aor.project.business.model.ICandidate;
import pt.uc.dei.aor.project.business.model.IModelFactory;
import pt.uc.dei.aor.project.business.persistence.ICandidatePersistenceService;

@Stateless
public class CandidateBusinessService implements ICandidateBusinessService {
	
	@Inject
	private IModelFactory factory;
	
	@Inject
	private ICandidatePersistenceService candidatePersistence;

	@Override
	public ICandidate createNewCandidate(String login, String name, String surname, String email, String encrypt) {
		ICandidate candidateProxy = factory.candidate(login, name, surname, email, encrypt);
		return candidatePersistence.save(candidateProxy);
	}
	
	

}
