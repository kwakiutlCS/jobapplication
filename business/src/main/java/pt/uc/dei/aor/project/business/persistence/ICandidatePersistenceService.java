package pt.uc.dei.aor.project.business.persistence;

import java.util.List;

import pt.uc.dei.aor.project.business.model.ICandidate;
import pt.uc.dei.aor.project.business.model.ICandidateNotification;

public interface ICandidatePersistenceService {

	ICandidate save(ICandidate candidateProxy);

	ICandidateNotification notify(ICandidate person, String msg);

	boolean findCandidateByEmailorLogin(String email, String login);

	List<ICandidate> findAll();

	void delete(ICandidate candidate);

	ICandidate getCandidateByEmail(String email);

	ICandidate getCandidateByLogin(String login);

	ICandidate verifyUser(long id, String encrypt);

}
