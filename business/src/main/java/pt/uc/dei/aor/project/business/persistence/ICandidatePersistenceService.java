package pt.uc.dei.aor.project.business.persistence;

import pt.uc.dei.aor.project.business.model.ICandidate;
import pt.uc.dei.aor.project.business.model.ICandidateNotification;

public interface ICandidatePersistenceService {

	ICandidate save(ICandidate candidateProxy);

	ICandidateNotification notify(ICandidate person, String msg);
	
}
