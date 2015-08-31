package pt.uc.dei.aor.project.business.persistence;

import pt.uc.dei.aor.project.business.model.ICandidate;

public interface ICandidatePersistenceService {

	ICandidate save(ICandidate candidateProxy);
	
}
