package pt.uc.dei.aor.project.business.service;

import java.util.Collection;

import pt.uc.dei.aor.project.business.model.IApplication;
import pt.uc.dei.aor.project.business.model.ICandidate;
import pt.uc.dei.aor.project.business.model.IQualification;

public interface ICandidateBusinessService {

	ICandidate createNewCandidate(String login, String name, String surname, String email, String encrypt, String address,
			String city, String country, String phone, String mobilePhone, Collection<IQualification> qualifications,
			String cv, Collection<IApplication> applications);

}


