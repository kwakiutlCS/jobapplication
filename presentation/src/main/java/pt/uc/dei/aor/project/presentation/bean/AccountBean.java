package pt.uc.dei.aor.project.presentation.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.FacesComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.uc.dei.aor.project.business.exception.WrongPasswordException;
import pt.uc.dei.aor.project.business.model.IQualification;
import pt.uc.dei.aor.project.business.model.IWorker;
import pt.uc.dei.aor.project.business.service.IQualificationBusinessService;
import pt.uc.dei.aor.project.business.service.IWorkerBusinessService;
import pt.uc.dei.aor.project.business.startup.Encryptor;
import pt.uc.dei.aor.project.presentation.util.MetaUtils;

@Named
@ViewScoped
public class AccountBean implements Serializable {

	private static final long serialVersionUID = -785077512441625997L;
	
	private static final Logger logger = LoggerFactory.getLogger(AccountBean.class);

	@Inject
	private IWorkerBusinessService workerService;
	
	@Inject
	private IQualificationBusinessService qualificationService;
	
	private String login;
	private String name;
	private String surname;
	private String email;
	private String password;
	private String oldPassword;
	
	private String address;
	private String city;
	private String country;
	private String phone;
	private String mobile;
	private String school;
	private String degree;
	private boolean showExtra;
	
	private Part cv;
	
	
	
	@PostConstruct
	public void init() {
		IWorker user = MetaUtils.getUser();
		login = user.getLogin();
		name = user.getName();
		surname = user.getSurname();
		email = user.getEmail();
		
		address = user.getAddress();
		city = user.getCity();
		country = user.getCountry();
		phone = user.getPhone();
		mobile = user.getMobile();
		
		//cv = user.getCv();
	}
	
	
	public void updatePassword() {
		try {
			IWorker user = MetaUtils.getUser();
			System.out.println(Encryptor.encrypt(password));
			user.setPassword(Encryptor.encrypt(password));
			user = workerService.update(user, oldPassword);
			
			password = null;
			oldPassword = null;
			MetaUtils.getSession().setAttribute("user", user);
			MetaUtils.setMsg("Password updated", FacesMessage.SEVERITY_INFO);
		} catch (WrongPasswordException e) {
			MetaUtils.setMsg("Password is incorrect", FacesMessage.SEVERITY_ERROR);
		}
	}
	
	public void updatePersonal() {
		try {
			IWorker user = MetaUtils.getUser();
			user.setLogin(login);
			user.setName(name);
			user.setSurname(surname);
			user.setEmail(email);
			user = workerService.update(user, password);
			password = null;
			MetaUtils.getSession().setAttribute("user", user);
			MetaUtils.setMsg("User details updated", FacesMessage.SEVERITY_INFO);
		} catch(WrongPasswordException e) {
			MetaUtils.setMsg("Password is incorrect", FacesMessage.SEVERITY_ERROR);
		}
	}
	
	public void updateExtra() {
		IWorker user = MetaUtils.getUser();
		user.setAddress(address);
		user.setCity(city);
		user.setCountry(country);
		user.setPhone(phone);
		user.setMobile(mobile);
	
		user = workerService.update(user);
		password = null;
		MetaUtils.getSession().setAttribute("user", user);
		MetaUtils.setMsg("User details updated", FacesMessage.SEVERITY_INFO);
	}
	
	
	public void upload(AjaxBehaviorEvent event) {
		
		if (!cv.getContentType().equals("application/pdf")) {
			MetaUtils.setMsg("Please upload a pdf file", FacesMessage.SEVERITY_ERROR);
			return; 
		}
		
		try {
			workerService.uploadCV(MetaUtils.getUser(), cv);
		} catch (IOException e) {
			MetaUtils.setMsg("Error uploading file", FacesMessage.SEVERITY_ERROR);
			logger.error("Error uploading file: "+cv.getSubmittedFileName());
		}
		
		cv = null;
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
	
	
	
	// getters and setters
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
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


	public String getMobile() {
		return mobile;
	}


	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public String getQualification() {
		return school;
	}


	public void setQualification(String qualification) {
		this.school = qualification;
	}


	public boolean isShowExtra() {
		return showExtra;
	}


	public void setShowExtra(boolean showExtra) {
		this.showExtra = showExtra;
	}


	public String getDegree() {
		return degree;
	}


	public void setDegree(String degree) {
		this.degree = degree;
	}


	public Part getCv() {
		return cv;
	}


	public void setCv(Part cv) {
		this.cv = cv;
	}

	public String getCvLink() {
		String app = ((HttpServletRequest) FacesContext.getCurrentInstance()
				.getExternalContext().getRequest()).getContextPath();
		String cv = MetaUtils.getUser().getCv();
		
		return "https://localhost:8443"+app+"/cv/"+cv;
	}
}
