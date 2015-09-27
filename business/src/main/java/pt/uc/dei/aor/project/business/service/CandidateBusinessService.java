package pt.uc.dei.aor.project.business.service;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.Part;

import pt.uc.dei.aor.project.business.exception.DuplicatedUserException;
import pt.uc.dei.aor.project.business.exception.WrongPasswordException;
import pt.uc.dei.aor.project.business.model.IApplication;
import pt.uc.dei.aor.project.business.model.ICandidate;
import pt.uc.dei.aor.project.business.model.IModelFactory;
import pt.uc.dei.aor.project.business.model.IQualification;
import pt.uc.dei.aor.project.business.persistence.ICandidatePersistenceService;
import pt.uc.dei.aor.project.business.startup.Encryptor;
import pt.uc.dei.aor.project.business.util.PasswordUtil;
import pt.uc.dei.aor.project.business.util.UploadUtil;

@Stateless
public class CandidateBusinessService implements ICandidateBusinessService {

	@Inject
	private IModelFactory factory;

	@Inject
	private ICandidatePersistenceService candidatePersistence;

	@Inject
	private UploadUtil upload;


	@Override
	public ICandidate createNewCandidate(String login, String name, String surname, String email, String encrypt, String address,
			String city, String country, String phone, String mobilePhone, Collection<IQualification> qualifications, String cv,
			Collection<IApplication> applications) throws DuplicatedUserException {
		System.out.println("facade");
		if (findCandidateByEmailorLogin(email, login)) 
			throw new DuplicatedUserException();

		ICandidate candidateProxy = factory.candidate(login, name, surname, email, encrypt,address,
				city, country, phone, mobilePhone, qualifications, cv, applications);
		System.out.println(candidateProxy);
		return candidatePersistence.save(candidateProxy);
	}


	private boolean findCandidateByEmailorLogin(String email, String login){
		return candidatePersistence.findCandidateByEmailorLogin(email, login);
	}

	@Override
	public void deleteCandidate(ICandidate candidate) {
		candidatePersistence.delete(candidate);	
	}

	@Override
	public List<ICandidate> findAllCandidates() {
		return candidatePersistence.findAll();
	}

	@Override
	@Asynchronous
	public void uploadCV(ICandidate candidate, Part cv) throws IOException {
		String filename = cv.getSubmittedFileName();
		String dir = "cv/"+candidate.getLogin();

		upload.delete(dir);
		upload.upload(dir, filename, cv.getInputStream());

		candidate.setCv(filename);
		candidatePersistence.save(candidate);
	}


	@Override
	public ICandidate getCandidateByEmail(String email) {
		return candidatePersistence.getCandidateByEmail(email);
	}


	@Override
	public ICandidate getCandidateByLogin(String login) {
		return candidatePersistence.getCandidateByLogin(login);
	}


	@Override
	public ICandidate update(ICandidate user) {
		return candidatePersistence.save(user);
	}


	@Override
	public ICandidate updatePassword(ICandidate updatedUser, String oldPassword) throws WrongPasswordException{
		ICandidate user = candidatePersistence.verifyUser(updatedUser.getId(), Encryptor.encrypt(oldPassword));

		if (user == null) throw new WrongPasswordException(); 

		return candidatePersistence.save(updatedUser);
	}

	@Override
	public void recoverPassword(String email) {
		ICandidate user = candidatePersistence.getCandidateByEmail(email);
		if (user == null) 
			return;

		String password = PasswordUtil.generate(8);
		user.setPassword(Encryptor.encrypt(password));
		candidatePersistence.save(user);

	}


}


