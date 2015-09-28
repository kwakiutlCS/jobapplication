package pt.uc.dei.aor.project.persistence.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import pt.uc.dei.aor.project.business.util.Role;

@Entity
@Table(name="userentity")
@NamedQueries({
	@NamedQuery(name = "User.findUserByLogin", query="from UserEntity u where u.login = :login"),
	@NamedQuery(name = "User.findUserByEmail", query="from UserEntity u where u.email = :email"),
	@NamedQuery(name = "User.findAllUsers", query="from UserEntity u"),
	@NamedQuery(name = "User.findAllAdmins", query="from UserEntity u where 'ADMIN' member of u.roles"),
	@NamedQuery(name = "User.findAllManagers", 
		query="from UserEntity u where 'MANAGER' member of u.roles"),
	@NamedQuery(name = "User.findAllInterviewers", 
		query = "from UserEntity u where 'INTERVIEWER' member of u.roles"),
	@NamedQuery(name = "User.findUserByEmailOrLogin", 
		query = "from UserEntity u where u.login = :login or u.email = :email"),
	@NamedQuery(name = "User.verifyUser", 
		query = "from UserEntity u where u.id = :id and u.password = :password"),
	@NamedQuery(name = "User.countAdmins", 
		query = "select count(u) from UserEntity u where 'ADMIN' member of u.roles"),
})
public class UserEntity {
	
	public UserEntity(String login, String email, String password, String name, String surname, 
			Collection<Role> roles) {
		this.login = login;
		this.password = password;
		this.email = email;
		this.name = name;
		this.surname = surname;
		this.completeName = name+" "+surname;
		this.roles = new HashSet<>(roles);
		this.roles.add(Role.CANDIDATE);
	}
	
	public UserEntity() {
		
	}



	public UserEntity(String login, String email, String password, String name, String surname, String phone, String mobilePhone, String address,
			String city, String country, List<QualificationEntity> qualifications, String cv) {
		this.login = login;
		this.email = email;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.address = address;
		this.city = city;
		this.country = country;
		this.qualifications = new HashSet<>();
		this.qualifications.addAll(qualifications);
		this.cv = cv;
		this.completeName = name+" "+surname;
		this.phone=phone;
		this.mobilePhone=mobilePhone;
	}



	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	protected long id;
	
	@Column(nullable=false, unique=true)
	protected String login;
	
	@Column(nullable=false)
	protected String password;
	
	@Column(nullable=false)
	protected String name;
	
	@Column(nullable=false)
	protected String surname;
	
	@Column(nullable=false) 
	protected String completeName;
	
	@Column(nullable=false, unique=true)
	protected String email;
	
	@Column
	private String address;
	
	@Column
	private String city;
	
	@Column
	private String country;
	
	@Column
	private String phone;
	
	@Column
	private String mobilePhone;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private Set<QualificationEntity> qualifications;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name="role", indexes={@Index(columnList="userentity_id")})
	@Enumerated(EnumType.STRING)
	@Column(name="role")
	private Set<Role> roles;

	
	@ManyToMany
	private Set<InterviewEntity> interviews;
	
	@OneToMany(mappedBy="contactPerson")
	private Set<PositionEntity> positions;
	
	
	@OneToMany(mappedBy="user")
	private Set<NotificationEntity> notification;
	
	@Column
	private String cv;
	
	@OneToMany(mappedBy="candidate")
	private Set<ApplicationEntity> applications;
	
	
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getCompleteName() {
		return completeName;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserEntity other = (UserEntity) obj;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [login=" + login + "]";
	}
	
	
	public Set<Role> getRoles() {
		return roles;
	}

	public void addInterview(InterviewEntity interviewEntity) {
		if (interviews == null) interviews = new HashSet<>();
		interviewEntity.addInterviewer(this);
		interviews.add(interviewEntity);
	}
	
	public boolean isInterviewer() {
		return roles.contains(Role.INTERVIEWER);
	}
	
	public boolean isManager() {
		return roles.contains(Role.MANAGER);
	}
	
	public boolean isAdmin() {
		return roles.contains(Role.ADMIN);
	}

	public void removeInterview(InterviewEntity interview) {
		interview.removeInterviewer(this);
		interviews.remove(interview);
	}

	public Collection<InterviewEntity> getInterviews() {
		return interviews;
	}

	public void setQualification(List<QualificationEntity> qualifications) {
		this.qualifications.addAll(qualifications);
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setMobile(String mobile) {
		this.mobilePhone = mobile;
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

	public Set<QualificationEntity> getQualifications() {
		return qualifications;
	}

	public String getAddress() {
		return address;
	}

	public String getCity() {
		return city;
	}

	public String getCountry() {
		return country;
	}

	public String getPhone() {
		return phone;
	}

	public void addQualification(QualificationEntity qualification) {
		qualifications.add(qualification);
	}

	public void removeQualification(QualificationEntity qualification) {
		qualifications.remove(qualification);
	}

	public void setCv(String cv) {
		this.cv = cv;
	}
	
	public String getCv() {
		return cv;
	}

	public void addRole(Role role) {
		roles.add(role);
	}
	
	public void removeRole(Role role) {
		roles.remove(role);
	}

	public List<PositionEntity> getPositions() {
		return new ArrayList<>(positions);
	}

	public List<NotificationEntity> getNotification() {
		return new ArrayList<>(notification);
	}

	public List<ApplicationEntity> getApplications() {
		return new ArrayList<>(applications);
	}
	
	
}
