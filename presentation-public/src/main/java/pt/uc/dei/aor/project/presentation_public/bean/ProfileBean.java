package pt.uc.dei.aor.project.presentation_public.bean;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.uc.dei.aor.project.business.exception.WrongPasswordException;
import pt.uc.dei.aor.project.business.model.IQualification;
import pt.uc.dei.aor.project.business.model.IUser;
import pt.uc.dei.aor.project.business.service.IQualificationBusinessService;
import pt.uc.dei.aor.project.business.service.IUserBusinessService;
import pt.uc.dei.aor.project.business.startup.Encryptor;
import pt.uc.dei.aor.project.presentation_public.util.MetaUtils;


@Named
@ViewScoped
public class ProfileBean implements Serializable {

	private static final long serialVersionUID = -6335117518506384458L;

	private static final Logger logger = LoggerFactory.getLogger(ProfileBean.class);
	
	@Inject
	private IUserBusinessService userService;
	
	@Inject
	private IQualificationBusinessService qualificationService;
	
	
	private String login;
	private String oldPassword;
	private String newPassword;
	
	private IUser user;
	
	private String cvLink;
	
	public IUser getUser() {
		return user;
	}

	private Part cv;
	
	private String school;
	private String degree;

	
	@PostConstruct
	private void init() {
		user = MetaUtils.getUser();
		
		login = user.getLogin();
	}
	
	public void updatePassword() {
		try {
			IUser user = MetaUtils.getUser();

			user.setPassword(Encryptor.encrypt(newPassword));
			user = userService.update(user, oldPassword);

			newPassword = null;
			oldPassword = null;
			MetaUtils.getSession().setAttribute("user", user);
			MetaUtils.setMsg("Password updated", FacesMessage.SEVERITY_INFO);
		} catch (WrongPasswordException e) {
			MetaUtils.setMsg("Password is incorrect", FacesMessage.SEVERITY_ERROR);
		}
	}
	
	public void updateUser() {
		user = userService.update(user);
		MetaUtils.getSession().setAttribute("user", user);
		MetaUtils.setMsg("User updated", FacesMessage.SEVERITY_INFO);
	}

	public void removeQualification(IQualification qualification) {
		user.removeQualification(qualification);
		user = userService.update(user);
		MetaUtils.getSession().setAttribute("user", user);
	}
	
	public void addQualification() {
		if (school == null || degree == null) return;
		user = qualificationService.addQualification(user, school, degree);
		MetaUtils.getSession().setAttribute("user", user);
	}
	
	public void upload(AjaxBehaviorEvent event) {
		
		if (!cv.getContentType().equals("application/pdf")) {
			MetaUtils.setMsg("Please upload a pdf file", FacesMessage.SEVERITY_ERROR);
			return; 
		}
		
		try {
			user = userService.uploadCV(MetaUtils.getUser(), cv);
			MetaUtils.getSession().setAttribute("user", user);
		} catch (IOException e) {
			MetaUtils.setMsg("Error uploading file", FacesMessage.SEVERITY_ERROR);
			logger.error("Error uploading file: "+cv.getSubmittedFileName());
		}
		
		cv = null;
	}
	
	
	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getCvLink() {
		return cvLink;
	}

	public void setCvLink(String cvLink) {
		this.cvLink = cvLink;
	}

	public Part getCv() {
		return cv;
	}

	public void setCv(Part cv) {
		this.cv = cv;
	}

	public void updateCandidate() {
		user = userService.update(user);
		MetaUtils.getSession().setAttribute("user", user);
		MetaUtils.setMsg("User updated", FacesMessage.SEVERITY_INFO);
	}
	
	
	// getters and setters
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

		
}

