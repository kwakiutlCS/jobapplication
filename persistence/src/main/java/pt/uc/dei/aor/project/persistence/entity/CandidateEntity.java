package pt.uc.dei.aor.project.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="candidate")
public class CandidateEntity extends User {

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
	
	// change string
	@Column
	private String qualifications;
	
	// change ??
	@Column
	private String institution;
	
	@Column
	private String cv;
	
	//@OneToMany(mappedBy="candidate")
	//private List<ApplicationEntity> applications;
}
