package pt.uc.dei.aor.project.persistence.entity;

import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;


@Entity
@Table(name="application")
@NamedQueries({
	@NamedQuery(name = "Application.dummyQuery", query="from ApplicationEntity u"),
})
public class ApplicationEntity {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable=false)
	private String cv;
	
	@Column(nullable=false)
	private String coverLetter;
	
	@Column(nullable=false)
	private String sourceInfo; 
	
	@ManyToOne
	@JoinColumn(nullable=false)
	private CandidateEntity candidate;
	
	@ManyToOne
	@JoinColumn(nullable=false)
	private PositionEntity position;
	
	@OrderBy("date")
	@OneToMany(mappedBy="application", fetch=FetchType.EAGER)
	private SortedSet<InterviewEntity> interviews = new TreeSet<>();
	
	@OneToOne
	private JobProposalEntity proposal;

	public SortedSet<InterviewEntity> getInterviews() {
		System.out.println("interview getter");
		System.out.println(interviews);
		return interviews;
	}

	public long getId() {
		return id;
	}

}
