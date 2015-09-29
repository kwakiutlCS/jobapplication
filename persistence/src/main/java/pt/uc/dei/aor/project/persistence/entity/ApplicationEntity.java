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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import pt.uc.dei.aor.project.business.util.ProposalSituation;


@Entity
@Table(name="application")
@NamedQueries({
	@NamedQuery(name = "Application.dummyQuery", query = "from ApplicationEntity u"),
	@NamedQuery(name = "Application.numberCandidatesByPeriod", 
	query = "select count(u) from ApplicationEntity u where u.date >= :startDate and u.date < :finishDate"),
	@NamedQuery(name = "Application.numberCandidatesByPosition", 
	query = "select count(u) from ApplicationEntity u where u.position = :position"),
	@NamedQuery(name = "Application.numberSpontaneousByPeriod", 
	query = "select count(u) from ApplicationEntity u where u.spontaneous is true and"
			+ " u.date >= :startDate and u.date < :finishDate"),
	@NamedQuery(name = "application.findApplicationbyCandidateAndPosition", 
	query = "from ApplicationEntity u where u.candidate = :candidate and u.position = :position")
})
public class ApplicationEntity {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable=false)
	private String coverLetter;
	
	@Column(nullable=false)
	private String sourceInfo; 
	
	@Column(nullable=false)
	private String cv;
	
	@Column(nullable=false)
	private Date date;
	
	@Column(nullable=false)
	private boolean analyzed = false;
	
	@Column(nullable=false)
	private boolean refused = false;
	
	@ManyToOne
	private UserEntity candidate;
	
	@ManyToOne
	private PositionEntity position;
	
	@Column
	private boolean spontaneous = false;
	
	@OrderBy("date")
	@OneToMany(mappedBy="application", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private SortedSet<InterviewEntity> interviews = new TreeSet<>();
	
	@OneToOne(cascade=CascadeType.ALL)
	private JobProposalEntity proposal;

	
	public ApplicationEntity(String coverLetter, String cv, String sourceInfo, Date date, UserEntity candidate,
			PositionEntity position) {
		this.coverLetter = coverLetter;
		this.sourceInfo = sourceInfo;
		this.date = date;
		this.candidate = candidate;
		this.position = position;
		this.cv=cv;
	}

	public ApplicationEntity() {
	}

	
	public SortedSet<InterviewEntity> getInterviews() {
		return new TreeSet<>(interviews);
	}

	public long getId() {
		return id;
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

	public UserEntity getCandidate() {
		return candidate;
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

	public Date getDate() {
		return date;
	}

	public String getLetter() {
		return coverLetter;
	}

	public boolean getAnalyzed() {
		return analyzed;
	}

	public void changeAnalyzed(boolean value) {
		analyzed = value;
	}

	public boolean isRefused() {
		return refused;
	}

	public boolean isAccepted() {
		if (proposal == null) return false;
		return proposal.getSituation() == ProposalSituation.ACCEPTED;
	}

	public boolean isPropositionSent() {
		if (proposal == null) return false;
		return proposal.getSituation() == ProposalSituation.ONHOLD;
	}
	
	public boolean isRefusedByCandidate() {
		if (proposal == null) return false;
		return proposal.getSituation() == ProposalSituation.REFUSED;
	}

	public void setRefused(boolean b) {
		refused = b;
	}

	public void setProposition(JobProposalEntity proposition) {
		proposal = proposition;
	}

	public JobProposalEntity getProposition() {
		return proposal;
	}

	public void setCv(String filename) {
		cv = filename;
	}
}
