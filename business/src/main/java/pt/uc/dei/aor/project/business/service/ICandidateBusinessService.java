package pt.uc.dei.aor.project.business.service;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.Part;

import pt.uc.dei.aor.project.business.exception.DuplicatedUserException;
import pt.uc.dei.aor.project.business.model.IApplication;
import pt.uc.dei.aor.project.business.model.ICandidate;
import pt.uc.dei.aor.project.business.model.IQualification;

public interface ICandidateBusinessService {

	ICandidate createNewCandidate(String login, String name, String surname, String email, String encrypt, String address,
			String city, String country, String phone, String mobilePhone, Collection<IQualification> qualifications,
			String cv, Collection<IApplication> applications) throws DuplicatedUserException;

	void deleteCandidate(ICandidate candidate);

	public List<ICandidate> findAllCandidates();

	void uploadCV(ICandidate candidate, Part cv) throws IOException;

	ICandidate getCandidateByEmail(String email);

	ICandidate getCandidateByLogin(String login);

	ICandidate update(ICandidate user);
	
	
}


