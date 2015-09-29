package pt.uc.dei.aor.project.persistence.proxy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.resource.spi.IllegalStateException;

import pt.uc.dei.aor.project.business.exception.IllegalRoleException;
import pt.uc.dei.aor.project.business.model.IInterview;
import pt.uc.dei.aor.project.business.model.IQualification;
import pt.uc.dei.aor.project.business.model.IUser;
import pt.uc.dei.aor.project.business.util.Role;
import pt.uc.dei.aor.project.persistence.entity.InterviewEntity;
import pt.uc.dei.aor.project.persistence.entity.QualificationEntity;
import pt.uc.dei.aor.project.persistence.entity.UserEntity;
import pt.uc.dei.aor.project.persistence.service.GenericPersistenceService;

public class UserProxy implements IUser, IProxyToEntity<UserEntity> {

	private UserEntity entity;
	
	public UserProxy(UserEntity entity) {
		this.entity = entity != null ? entity : new UserEntity();
	}

	public UserProxy(String login, String email, String password, String name, 
			String surname, Collection<Role> roles) {
		entity = new UserEntity(login, email, password, name, surname, roles);
	}
	
	public UserProxy() {
		this(null);
	}

	

	public UserProxy(String login, String email, String password, String name, String surname,String phone, String mobilePhone, String address,
			String city, String country, List<IQualification> qualifications, String cv) {
		
		List<QualificationEntity> entities = new ArrayList<>();
		for (IQualification q : qualifications) {
			entities.add(GenericPersistenceService.getEntity(q));
		}
		
		entity = new UserEntity(login, email, password, name, surname, phone,mobilePhone, address, city, country, 
				entities, cv);
	}

	@Override
	public UserEntity getEntity() {
		return entity;
	}

	@Override
	public String getFullName() {
		return entity.getName()+" "+entity.getSurname();
	}

	@Override
	public List<String> getRoles() {
		List<String> roles = new LinkedList<>();
		System.out.println("nome da entity "+entity.getName());
		for (Role r : entity.getRoles()) {
			roles.add(r.toString());
			System.out.println("add roles");
		}
		return roles;
	}

	@Override
	public String getLogin() {
		return entity.getLogin();
	}
	
	@Override
	public long getId() {
		return entity.getId();
	}

	@Override
	public String getName() {
		return entity.getName();
	}

	@Override
	public String getSurname() {
		return entity.getSurname();
	}

	@Override
	public String getEmail() {
		return entity.getEmail();
	}

	@Override
	public String toString() {
		return entity.toString();
	}
	
	@Override
	public int hashCode() {
		return entity.hashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		UserEntity oe = null;
		
		if (o instanceof IProxyToEntity<?>)
			oe = GenericPersistenceService.getEntity(o);
		else return false;
		
		return entity.equals(oe);
	}

	@Override
	public void addInterview(IInterview interview) throws IllegalRoleException, IllegalStateException {
		if (!isInterviewer()) throw new IllegalRoleException();
		
		InterviewEntity interviewEntity = GenericPersistenceService.getEntity(interview);
		
		entity.addInterview(interviewEntity);
	}
	
		
	// helper methods
	@Override
	public void setPassword(String password) {
		entity.setPassword(password);
	}

	@Override
	public void setLogin(String login) {
		entity.setLogin(login);
	}

	@Override
	public void setName(String name) {
		entity.setName(name);
	}

	@Override
	public void setSurname(String surname) {
		entity.setSurname(surname);
	}

	@Override
	public void setEmail(String email) {
		entity.setEmail(email);
	}

	@Override
	public void setAddress(String address) {
		entity.setAddress(address);
	}

	@Override
	public void setCity(String city) {
		entity.setCity(city);
	}

	@Override
	public void setCountry(String country) {
		entity.setCountry(country);
	}

	@Override
	public void setMobile(String mobile) {
		entity.setMobile(mobile);
	}

	@Override
	public void setPhone(String phone) {
		entity.setPhone(phone);
	}

	
	@Override
	public String getAddress() {
		return entity.getAddress();
	}

	@Override
	public String getCity() {
		return entity.getCity();
	}

	
	@Override
	public String getCountry() {
		return entity.getCountry();
	}

	@Override
	public String getPhone() {
		return entity.getPhone();
	}

	@Override
	public String getMobile() {
		return entity.getMobilePhone();
	}

	@Override
	public List<IQualification> getQualifications() {
		List<IQualification> qualifications = new ArrayList<>();
		
		for (QualificationEntity qe : entity.getQualifications()) {
			qualifications.add(new QualificationProxy(qe));
		}
		
		return qualifications;
	}

	@Override
	public void addQualification(IQualification qualification) {
		entity.addQualification(GenericPersistenceService.getEntity(qualification));
	}

	@Override
	public void removeQualification(IQualification qualification) {
		entity.removeQualification(GenericPersistenceService.getEntity(qualification));
	}

	@Override
	public String getRolesAsString() {
		StringBuilder s = new StringBuilder();
		
		for (Role r : entity.getRoles()) {
			if (s.length() == 0) s.append(r.getLabel());
			else s.append(", "+r.getLabel());
		}
		
		return s.toString();
	}

	@Override
	public void setCv(String filename) {
		entity.setCv(filename);
	}

	@Override
	public String getCv() {
		return entity.getCv();
	}

	@Override
	public boolean isAdmin() {
		return entity.isAdmin();
	}

	@Override
	public boolean isManager() {
		return entity.isManager();
	}

	@Override
	public boolean isInterviewer() {
		return entity.isInterviewer();
	}

	@Override
	public void addRole(Role role) {
		entity.addRole(role);
	}

	@Override
	public void removeRole(Role role) {
		entity.removeRole(role);
	}

	
}
