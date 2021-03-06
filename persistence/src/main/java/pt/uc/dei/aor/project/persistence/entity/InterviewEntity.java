package pt.uc.dei.aor.project.persistence.entity;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="interview")
@NamedQueries({
	@NamedQuery(name = "Interview.findAllInterviews", query = "from InterviewEntity u"),
	@NamedQuery(name = "Interview.findActiveInterviewsByInterviewer", 
	query = "from InterviewEntity u where :user member of u.interviewers and u.date >= :date"),
	@NamedQuery(name = "Interview.findInterviewByDateAndApplication", 
	query = "from InterviewEntity u where u.date = :date and u.application = :application"),
	@NamedQuery(name = "Interview.findPastInterviewsByCandidate", 
	query = "from InterviewEntity u where u.date < :date and u.application in (:applications)"),
	@NamedQuery(name = "Interview.findPastInterviews", 
	query = "from InterviewEntity u where u.date < :date and u.application = :application"),
	@NamedQuery(name = "Interview.findInterviewsByApplication", 
	query = "from InterviewEntity u where u.application = :application"),
	@NamedQuery(name = "Interview.findInterviewsByDate", 
	query = "from InterviewEntity u where u.date between :startDate and :finishDate"),
	
	
})
public class InterviewEntity implements Comparable<InterviewEntity> {
	
	public InterviewEntity(Date date) {
		this.date = date;
	}

	public InterviewEntity() {
		
	}

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	@ManyToOne
	@JoinColumn(nullable=false)
	private ApplicationEntity application;
	
	
	@Column(nullable=false)
	private Date date;
	
	@ManyToMany(mappedBy="interviews", fetch=FetchType.EAGER)
	private Set<UserEntity> interviewers;

	@OneToMany(mappedBy="interview")
	private Set<AnswerEntity> answers;
	
	
	// getters and setters
	
	public Date getDate() {
		return date;
	}


	public Set<UserEntity> getInterviewers() {
		if (interviewers == null) interviewers = new HashSet<>();
		return interviewers;
	}
	
	public ApplicationEntity getApplication() {
		return application;
	}

	public long getId() {
		return id;
	}

	@Override
	public int compareTo(InterviewEntity o) {
		if (date == null || o.date == null) return 0;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		Calendar otherCal = Calendar.getInstance();
		otherCal.setTime(o.date);
		
		if (cal.after(otherCal)) return 1;
		else if (cal.before(otherCal)) return -1;
		return 0;
	}

	@Override
	public String toString() {
		return "InterviewEntity [application=" + application + ", date=" + date + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((application == null) ? 0 : application.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
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
		InterviewEntity other = (InterviewEntity) obj;
		if (application == null) {
			if (other.application != null)
				return false;
		} else if (!application.equals(other.application))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		return true;
	}

	public void setApplication(ApplicationEntity application) {
		this.application = application;
	}

	public void addInterviewer(UserEntity interviewer) {
		if (this.interviewers == null) this.interviewers = new HashSet<>();
		this.interviewers.add(interviewer);
	}
	
	public void removeInterviewer(UserEntity interviewer) {
		this.interviewers.remove(interviewer);
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setInterviewers(List<UserEntity> interviewers) {
		this.interviewers = new HashSet<>();
		this.interviewers.addAll(interviewers);
		
		for (UserEntity w : interviewers) {
			w.addInterview(this);
		}
	}

	public Collection<AnswerEntity> getAnswers() {
		return answers;
	}
}
