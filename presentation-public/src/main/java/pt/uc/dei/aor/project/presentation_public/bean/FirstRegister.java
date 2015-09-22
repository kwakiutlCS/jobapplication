package pt.uc.dei.aor.project.presentation_public.bean;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.uc.dei.aor.project.business.exception.DuplicatedUserException;
import pt.uc.dei.aor.project.business.service.ICandidateBusinessService;
import pt.uc.dei.aor.project.business.service.IQualificationBusinessService;
import pt.uc.dei.aor.project.business.startup.Encryptor;



@Named
@RequestScoped
public class FirstRegister {



	private static final Logger logger = LoggerFactory.getLogger(FirstRegister.class);

	@Inject
	private ICandidateBusinessService candidateService;

	@Inject
	private IQualificationBusinessService qualificationService;
	
	private LoginBean loginbean= new LoginBean();
	private String login;
	private String email;
	private String password;
	private String temp;


	
	
	public FirstRegister() {
	}

	public String defaultString(){
		
		return "defaultString";
	}
	
	
	public String register() throws DuplicatedUserException {

		temp=defaultString();

		candidateService.createNewCandidate(login,temp,temp,email , Encryptor.encrypt(password),temp,temp,temp,temp,temp,null,null, null);
		
		return "index.xhtml";
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
}

