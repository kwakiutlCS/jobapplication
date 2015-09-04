package pt.uc.dei.aor.project.persistence.entity;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
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
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name="interview")
@NamedQueries({
	@NamedQuery(name = "Interview.findAllInterviews", query="from InterviewEntity u"),
	@NamedQuery(name = "Interview.findActiveInterviewsByInterviewer", 
	query="from InterviewEntity u where :user member of u.interviewers and u.date >= :date"),
})
public class InterviewEntity implements Comparable<InterviewEntity> {
	
	public InterviewEntity(ApplicationEntity application, Date date) {
		this.application = application;
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
	
	@Column
	private String classification;
	
	@Column(nullable=false)
	private Date date;
	
	@ManyToMany(mappedBy="interviews", fetch=FetchType.EAGER)
	private Set<WorkerEntity> interviewers;

	
	
	// getters and setters
	
	public Date getDate() {
		return date;
	}



	public Set<WorkerEntity> getInterviewers() {
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
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		Calendar otherCal = Calendar.getInstance();
		otherCal.setTime(o.date);
		
		if (cal.after(otherCal)) return 1;
		else if (cal.before(otherCal)) return -1;
		return 0;
	}

}
