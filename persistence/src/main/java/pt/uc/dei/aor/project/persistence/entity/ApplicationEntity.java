package pt.uc.dei.aor.project.persistence.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name="application")
public class ApplicationEntity {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(nullable=false, unique=true)
	private int id_application;
	@Column
	private String cv;
	@Column
	private String coverLetter;
	@Column
	private String sourceInfo; 
	
	@ManyToOne
	@Column(nullable=false)
	private CandidateEntity candidate;
//	@ManyToOne
//	@Column(nullable=false)
//	private PositionEntity position;
	@OneToMany(mappedBy="application")
	@Column
	private List<InterviewEntity> interviews;
	@OneToOne
	@Column
	private JobProposalEntity proposal;
	

}
