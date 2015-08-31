package pt.uc.dei.aor.project.business.service;

import pt.uc.dei.aor.project.business.model.ICandidate;

public interface ICandidateBusinessService {

	ICandidate createNewCandidate(String login, String name, String surname, String email, String encrypt);
	
}
