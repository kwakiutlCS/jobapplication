package pt.uc.dei.aor.project.presentation_public.bean;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.uc.dei.aor.project.business.exception.DuplicatedUserException;
import pt.uc.dei.aor.project.business.model.IApplication;
import pt.uc.dei.aor.project.business.model.ICandidate;
import pt.uc.dei.aor.project.business.model.IQualification;
import pt.uc.dei.aor.project.business.service.ICandidateBusinessService;
import pt.uc.dei.aor.project.business.service.IQualificationBusinessService;
import pt.uc.dei.aor.project.business.startup.Encryptor;
import pt.uc.dei.aor.project.presentation_public.util.MetaUtils;


@Named
@RequestScoped
public class RegisterBean {

	private static final Logger logger = LoggerFactory.getLogger(RegisterBean.class);

	@Inject
	private ICandidateBusinessService candidateService;

	@Inject
	private IQualificationBusinessService qualificationService;

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
	private String school;
	private String degree;
	private List<IQualification> choosenQualifications;
	private List<IApplication> applications;
	private Part cv;
	private String cvPath;;
	private String oldPassword;
	private boolean showExtra;


	@PostConstruct
	public void init() {
		ICandidate user = MetaUtils.getUser();
		login = user.getLogin();
		email = user.getEmail();
		name = user.getName();
		surname = user.getSurname();
		address = user.getAddress();
		city = user.getCity();
		country = user.getCountry();
		phone = user.getPhone();
		mobilePhone = user.getMobilePhone();
		
	}


	public RegisterBean() {
	}



	public void upload(AjaxBehaviorEvent event) {

		if (!cv.getContentType().equals("application/pdf")) {
			MetaUtils.setMsg("Please upload a pdf file", FacesMessage.SEVERITY_ERROR);
			return; 
		}

		try {
			candidateService.uploadCV(MetaUtils.getUser(), cv);
		} catch (IOException e) {
			MetaUtils.setMsg("Error uploading file", FacesMessage.SEVERITY_ERROR);
			cv = null;
			logger.error("Error uploading file: "+cv.getSubmittedFileName());
		}
	}

	public List<String> listSchools(String text) {
		return qualificationService.listSchools(text);
	}

	public List<String> listDegrees() {
		return qualificationService.listDegrees(school);
	}


	public void addQualification() {
		qualificationService.addQualification(MetaUtils.getUser(), school, degree);
	}

	public void removeQualification(IQualification qualification) {
		qualificationService.removeQualification(MetaUtils.getUser(), qualification);
	}


	public String register() throws DuplicatedUserException {

		candidateService.createNewCandidate(login, name, surname, email, Encryptor.encrypt(password), address, city,
				country, phone, mobilePhone, choosenQualifications, cvPath, applications);

		return "index.xhtml";
	}

	public void updateCandidate() {
			ICandidate user = MetaUtils.getUser();
			user.setName(name);
			user.setSurname(surname);
			user.setAddress(address);
			user.setCity(city);
			user.setCountry(country);
			user.setPhone(phone);
			user.setMobilePhone(mobilePhone);
			user = candidateService.update(user);
	}

	//getters and setters

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

	public List<IApplication> getApplications() {
		return applications;
	}

	public void setApplications(List<IApplication> applications) {
		this.applications = applications;
	}

	public String getQualification() {
		return school;
	}

	public void setQualification(String qualification) {
		this.school = qualification;
	}

	public String getDegree() {
		return degree;
	}


	public void setDegree(String degree) {
		this.degree = degree;
	}


	public IQualificationBusinessService getQualificationService() {
		return qualificationService;
	}


	public void setQualificationService(
			IQualificationBusinessService qualificationService) {
		this.qualificationService = qualificationService;
	}


	public String getSchool() {
		return school;
	}


	public void setSchool(String school) {
		this.school = school;
	}


	public List<IQualification> getChoosenQualifications() {
		return choosenQualifications;
	}


	public void setChoosenQualifications(List<IQualification> choosenQualifications) {
		this.choosenQualifications = choosenQualifications;
	}


	public Part getCv() {
		return cv;
	}


	public void setCv(Part cv) {
		this.cv = cv;
	}


	public String getCvPath() {
		return cvPath;
	}


	public void setCvPath(String cvPath) {
		this.cvPath = cvPath;
	}


	public String getOldPassword() {
		return oldPassword;
	}


	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}


	public boolean isShowExtra() {
		return showExtra;
	}


	public void setShowExtra(boolean showExtra) {
		this.showExtra = showExtra;
	}



}

