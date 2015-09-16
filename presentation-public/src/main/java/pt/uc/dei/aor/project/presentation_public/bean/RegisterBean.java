package pt.uc.dei.aor.project.presentation_public.bean;

import java.util.Collection;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import pt.uc.dei.aor.project.business.model.IApplication;
import pt.uc.dei.aor.project.business.model.IQualification;
import pt.uc.dei.aor.project.business.service.ICandidateBusinessService;
import pt.uc.dei.aor.project.business.startup.Encryptor;

@Named
@RequestScoped
public class RegisterBean {
	
	@Inject
	private ICandidateBusinessService candidateService;
	
	private String login;
	private String password;
	private String email;
	private String name;
	private String surname;
	private String address;
	private String city;
	private String country;
	private String phone; 
	private String mobilePhone;
	private Collection<IQualification> qualifications; 
	private Collection<IApplication> applications;
	private String cv;
	
	public RegisterBean() {
	}
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public ICandidateBusinessService getCandidateService() {
		return candidateService;
	}

	public void setCandidateService(ICandidateBusinessService candidateService) {
		this.candidateService = candidateService;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public Collection<IQualification> getQualifications() {
		return qualifications;
	}

	public void setQualifications(Collection<IQualification> qualifications) {
		this.qualifications = qualifications;
	}

	public String getCv() {
		return cv;
	}

	public void setCv(String cv) {
		this.cv = cv;
	}

	public Collection<IApplication> getApplications() {
		return applications;
	}

	public void setApplications(Collection<IApplication> applications) {
		this.applications = applications;
	}

	public String register() {
		
		System.out.println(login);
		System.out.println(cv);

		candidateService.createNewCandidate(login, name, surname, email, Encryptor.encrypt(password), address, city,
				country, phone, mobilePhone, qualifications, cv, applications);
		
		System.out.println("Complete!");
		
		return "index.xhtml";
	}
}

