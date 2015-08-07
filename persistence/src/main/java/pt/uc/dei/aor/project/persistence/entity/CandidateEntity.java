package pt.uc.dei.aor.project.persistence.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
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
	
	// change string
	@Column
	private String qualifications;
	
	// change ??
	@Column
	private String institution;
	
	@Column
	private String cv;
	
	@OneToMany(mappedBy="candidate")
	private List<ApplicationEntity> applications;
}
