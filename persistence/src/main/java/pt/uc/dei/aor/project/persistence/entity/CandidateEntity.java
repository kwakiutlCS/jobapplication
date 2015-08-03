package pt.uc.dei.aor.project.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
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
	
	@Column
	private String qualifications;
	
	@Column
	private String cv;
	
	@Column
	private String motivationLetter;
	
}
