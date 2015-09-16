package pt.uc.dei.aor.project.persistence.entity;

import java.util.ArrayList;
import java.util.Collection;
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
	
	public CandidateEntity(String login, String email, String password,
			String name, String surname, String address, String city,
			String country, String phone, String mobilePhone,
			Collection<QualificationEntity> qualifications, String cv,
			Collection<ApplicationEntity> applications) {
		super(login, email, password, name, surname);
		this.address = address;
		this.city = city;
		this.country = country;
		this.phone = phone;
		this.mobilePhone = mobilePhone;
		this.qualifications = new ArrayList<>();
		this.qualifications.addAll(qualifications);
		this.cv = cv;
		this.applications = new ArrayList<>();
		this.applications.addAll(applications);
	}



	public CandidateEntity() {	
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

	public List<QualificationEntity> getQualifications() {
		return new ArrayList<QualificationEntity>(qualifications);
	}

	public void setQualifications(List<QualificationEntity> qualifications) {
		this.qualifications.addAll(qualifications);
	}

	public String getCv() {
		return cv;
	}

	public void setCv(String cv) {
		this.cv = cv;
	}

	public List<ApplicationEntity> getApplications() {
		return new ArrayList<ApplicationEntity>(applications);
	}

	public void setApplications(List<ApplicationEntity> applications) {
		this.applications.addAll(applications);
	}
	
}
