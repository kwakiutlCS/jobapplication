package pt.uc.dei.aor.project.persistence.entity;

import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.CascadeType;
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
	@NamedQuery(name = "Application.dummyQuery", query = "from ApplicationEntity u"),
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
	
	@Column(nullable=false)
	private Date date;
	
	@ManyToOne
	private CandidateEntity candidate;
	
	@ManyToOne
	private WorkerEntity internalCandidate;
	
	@ManyToOne
	@JoinColumn(nullable=false)
	private PositionEntity position;
	
	@OrderBy("date")
	@OneToMany(mappedBy="application", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private SortedSet<InterviewEntity> interviews = new TreeSet<>();
	
	@OneToOne
	private JobProposalEntity proposal;

	
	public SortedSet<InterviewEntity> getInterviews() {
		return interviews;
	}

	public long getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((candidate == null) ? 0 : candidate.hashCode());
		result = prime * result + ((position == null) ? 0 : position.hashCode());
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
		ApplicationEntity other = (ApplicationEntity) obj;
		if (candidate == null) {
			if (other.candidate != null)
				return false;
		} else if (!candidate.equals(other.candidate))
			return false;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		return true;
	}

	public PositionEntity getPosition() {
		return position;
	}

	public void addInterview(InterviewEntity interview) {
		interview.setApplication(this);
		interviews.add(interview);
	}

	public void remove(InterviewEntity interview) {
		interviews.remove(interview);
	}

	public CandidateEntity getCandidate() {
		return candidate;
	}
	
	public Date getDate() {
		return date;
	}
}
