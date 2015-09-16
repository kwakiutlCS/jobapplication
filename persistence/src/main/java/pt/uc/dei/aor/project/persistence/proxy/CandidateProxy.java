package pt.uc.dei.aor.project.persistence.proxy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import pt.uc.dei.aor.project.business.model.IApplication;
import pt.uc.dei.aor.project.business.model.ICandidate;
import pt.uc.dei.aor.project.business.model.IQualification;
import pt.uc.dei.aor.project.persistence.entity.ApplicationEntity;
import pt.uc.dei.aor.project.persistence.entity.CandidateEntity;
import pt.uc.dei.aor.project.persistence.entity.QualificationEntity;
import pt.uc.dei.aor.project.persistence.service.GenericPersistenceService;

public class CandidateProxy implements ICandidate, IProxyToEntity<CandidateEntity> {

	private CandidateEntity entity;

	public CandidateProxy(CandidateEntity entity) {
		this.entity = entity != null ? entity : new CandidateEntity();
	}

	public CandidateProxy(String login, String email, String password, String name, String surname, String address, String city,
			String country, String phone, String mobilePhone, Collection<IQualification> qualifications, String cv,
			Collection<IApplication> applications) {

		List<QualificationEntity> qualificationEntities = new ArrayList<>();
		if (qualifications != null) {
			for(IQualification qualif: qualifications){
				qualificationEntities.add(GenericPersistenceService.getEntity(qualif));
			}
		}

		List<ApplicationEntity> applicationEntities = new ArrayList<>();
		if (applications != null) {
			for(IApplication applies: applications){
				applicationEntities.add(GenericPersistenceService.getEntity(applies));
			}
		}

		entity = new CandidateEntity(login, email, password, name, surname, address, city, country, phone,
				mobilePhone, qualificationEntities, cv, applicationEntities);
	}

	@Override
	public CandidateEntity getEntity() {
		return entity;
	}

	@Override
	public String getAddress() {
		return entity.getAddress();
	}

	@Override
	public void setAddress(String address) {
		entity.setAddress(address);		
	}

	@Override
	public String getCity() {
		return entity.getCity();
	}

	@Override
	public void setCity(String city) {
		entity.setCity(city);
	}

	@Override
	public String getCountry() {
		return entity.getCountry();
	}

	@Override
	public void setCountry(String country) {
		entity.setCountry(country);
	}

	@Override
	public String getPhone() {
		return entity.getPhone();
	}

	@Override
	public void setPhone(String phone) {
		entity.setPhone(phone);	
	}

	@Override
	public String getMobilePhone() {
		return entity.getMobilePhone();
	}

	@Override
	public void setMobilePhone(String mobilephone) {
		entity.setMobilePhone(mobilephone);

	}

	@Override
	public List<IQualification> getQualifications() {

		List<QualificationEntity> qualificationentities = entity.getQualifications(); 
		List<IQualification> qualifications = new ArrayList<IQualification>();

		for(QualificationEntity qualif : qualificationentities)
			qualifications.add(new QualificationProxy(qualif));

		return qualifications;
	}



	@Override
	public void setQualifications(List<IQualification> qualifications) {

		List<QualificationEntity> qualificationentities = new ArrayList<QualificationEntity>();

		for(IQualification qualif : qualifications)
			qualificationentities.add(GenericPersistenceService.getEntity(qualif));	
	}

	@Override
	public String getCv() {
		return entity.getCv();
	}

	@Override
	public void setCv(String cv) {
		entity.setCv(cv);
	}

	@Override
	public List<IApplication> getApplications() {

		List<ApplicationEntity> applicationentities = entity.getApplications(); 

		List<IApplication> applications = new ArrayList<IApplication>();

		for(ApplicationEntity application : applicationentities)
			applications.add(new ApplicationProxy(application));

		return applications;
	}

	@Override
	public void setApplications(List<IApplication> applications) {

		List<ApplicationEntity> applicationentities = new ArrayList<ApplicationEntity>();

		for(IApplication application : applications)
			applicationentities.add(GenericPersistenceService.getEntity(application));	
	}


}
