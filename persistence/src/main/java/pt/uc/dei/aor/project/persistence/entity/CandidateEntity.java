package pt.uc.dei.aor.project.persistence.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="candidate")
public class CandidateEntity extends User {

	@Column(nullable=false)
	private String address;
	
	@Column(nullable=false)
	private String city;
	
	@Column(nullable=false)
	private String country;
	
	@Column
	private String phone;
	
	@Column(nullable=false)
	private String mobilePhone;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private List<QualificationEntity> qualifications;
	
	@Column
	private String cv;
	
	@OneToMany(mappedBy="candidate")
	private List<ApplicationEntity> applications;
	
	
	public CandidateEntity(String login, String email, String password, String name, String surname) {
		super(login, email, password, name, surname);
		city = "a";
		address = "a";
		country = "a";
		phone = "a";
		mobilePhone = "a";
		qualifications = null;
		cv = "a";
	}

	public CandidateEntity() {
		
	}
	
}
