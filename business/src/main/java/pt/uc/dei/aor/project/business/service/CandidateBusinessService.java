package pt.uc.dei.aor.project.business.service;

import java.util.Collection;

import javax.ejb.Stateless;
import javax.inject.Inject;

import pt.uc.dei.aor.project.business.model.IApplication;
import pt.uc.dei.aor.project.business.model.ICandidate;
import pt.uc.dei.aor.project.business.model.IModelFactory;
import pt.uc.dei.aor.project.business.model.IQualification;
import pt.uc.dei.aor.project.business.persistence.ICandidatePersistenceService;

@Stateless
public class CandidateBusinessService implements ICandidateBusinessService {
	
	@Inject
	private IModelFactory factory;
	
	@Inject
	private ICandidatePersistenceService candidatePersistence;


	@Override
	public ICandidate createNewCandidate(String login, String name, String surname, String email, String encrypt, String address,
			String city, String country, String phone, String mobilePhone, Collection<IQualification> qualifications, String cv,
			Collection<IApplication> applications) {
		
		ICandidate candidateProxy = factory.candidate(login, name, surname, email, encrypt,address,
				city, country, phone, mobilePhone, qualifications, cv, applications);
		
		return candidatePersistence.save(candidateProxy);
	}
}
