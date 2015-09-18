 package pt.uc.dei.aor.project.persistence.entity;


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
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import pt.uc.dei.aor.project.business.model.IWorkerNotification;
import pt.uc.dei.aor.project.business.util.Role;

@Entity
@Table(name="worker")
@NamedQueries({
	@NamedQuery(name = "Worker.findWorkerByLogin", query="from WorkerEntity u where u.login = :login"),
	@NamedQuery(name = "Worker.findWorkerByEmail", query="from WorkerEntity u where u.email = :email"),
	@NamedQuery(name = "Worker.findAllWorkers", query="from WorkerEntity u"),
	@NamedQuery(name = "Worker.findAllAdmins", query="from WorkerEntity u where 'ADMIN' member of u.roles"),
	@NamedQuery(name = "Worker.findAllManagers", 
	query="from WorkerEntity u where 'MANAGER' member of u.roles"),
	@NamedQuery(name = "Worker.findAllInterviewers", 
	query = "from WorkerEntity u where 'INTERVIEWER' member of u.roles"),
	@NamedQuery(name = "Worker.findWorkerByEmailOrLogin", 
	query = "from WorkerEntity u where u.login = :login or u.email = :email"),
	@NamedQuery(name = "Worker.verifyWorker", 
	query = "from WorkerEntity u where u.id = :id and u.password = :password"),
})
@NamedNativeQueries({
	@NamedNativeQuery(name = "Worker.createSuperUser", 
	query="insert into worker values (0, 'SU', 'SU', 'SU', 'SU', 'SU','SU')"),
	@NamedNativeQuery(name = "Worker.createSuperUserRole", 
	query="insert into role values (0, 'CANDIDATE')"),
})
public class WorkerEntity extends User {
	
	public WorkerEntity(String login, String email, String password, String name, 
			String surname, Collection<Role> roles) {
		super(login, email, password, name, surname);
		this.roles = new HashSet<>();
		this.roles.addAll(roles);
	}

	public WorkerEntity() {
		super();
	}

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name="role", indexes={@Index(columnList="workerentity_id")})
	@Enumerated(EnumType.STRING)
	@Column(name="role")
	private Set<Role> roles;

	
	@ManyToMany
	private Set<InterviewEntity> interviews;
	
	@OneToMany(mappedBy="worker")
	private Set<WorkerNotificationEntity> notification;
	
	
	
	// applications attributes
	
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
	
	@Column
	private String cv;
	
	@OneToMany(mappedBy="internalCandidate")
	private List<ApplicationEntity> applications;
	
	
	
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
}
