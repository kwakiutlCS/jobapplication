package pt.uc.dei.aor.project.presentation_public.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.uc.dei.aor.project.business.exception.DuplicatedUserException;
import pt.uc.dei.aor.project.business.model.IQualification;
import pt.uc.dei.aor.project.business.model.IUser;
import pt.uc.dei.aor.project.business.service.IQualificationBusinessService;
import pt.uc.dei.aor.project.business.service.IUserBusinessService;
import pt.uc.dei.aor.project.business.startup.Encryptor;
import pt.uc.dei.aor.project.presentation_public.util.MetaUtils;


@Named
@ViewScoped
public class RegisterBean implements Serializable {

	private static final long serialVersionUID = -6335117518506384458L;

	private static final Logger logger = LoggerFactory.getLogger(RegisterBean.class);

	@Inject
	private IUserBusinessService candidateService;

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
	private List<IQualification> choosenQualifications;


	private String school;
	private String degree;


	private Part cv;
	private String cvPath;
	private String provisoryCv;
	
	private String oldPassword;

	
	@PostConstruct
	public void init() {
		choosenQualifications = new ArrayList<>();
	}
	
	public String register() {
		if (cvPath == null) {
			MetaUtils.setMsg("Upload a cv to register", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		System.out.println(choosenQualifications);
		if (choosenQualifications == null || choosenQualifications.isEmpty()) {
			MetaUtils.setMsg("Add qualifications to register", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		try {
			candidateService.createNewCandidate(login,name,surname,email, Encryptor.encrypt(password),phone, mobilePhone, 
					address, city, country, choosenQualifications, cvPath, provisoryCv);
			MetaUtils.setMsg("User registered with success", FacesMessage.SEVERITY_INFO);
			return "index.xhtml";
		} catch (IOException e) {
			MetaUtils.setMsg("Error uploading file", FacesMessage.SEVERITY_ERROR);
		}
		return null;
	}

	public void upload(AjaxBehaviorEvent event) {

		if (!cv.getContentType().equals("application/pdf")) {
			MetaUtils.setMsg("Please upload a pdf file", FacesMessage.SEVERITY_ERROR);
			return; 
		}

		try {
			provisoryCv = candidateService.uploadTempCV(cv);
			cvPath = cv.getSubmittedFileName();
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
		if (choosenQualifications == null) choosenQualifications = new ArrayList<>();
		choosenQualifications.add(qualificationService.addQualification(school, degree));
		school = null;
		degree = null;
	}

	public void removeQualification(IQualification qualification){
		choosenQualifications.remove(qualification);
	}

	public void updateCandidate() throws DuplicatedUserException {
		IUser user = MetaUtils.getUser();
		user.setName(name);
		user.setSurname(surname);
		user.setAddress(address);
		user.setCity(city);
		user.setCountry(country);
		user.setPhone(phone);
		user.setMobile(mobilePhone);

		choosenQualifications = user.getQualifications();

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

	public IUserBusinessService getCandidateService() {
		return candidateService;
	}

	public void setCandidateService(IUserBusinessService candidateService) {
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


}

