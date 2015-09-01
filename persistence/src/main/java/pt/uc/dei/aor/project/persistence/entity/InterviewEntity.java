package pt.uc.dei.aor.project.persistence.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import pt.uc.dei.aor.project.business.model.IApplication;
import pt.uc.dei.aor.project.business.model.IWorker;


@Entity
@Table(name="interview")
@NamedQueries({
	@NamedQuery(name = "Interview.findAllInterviews", query="from InterviewEntity u"),
	@NamedQuery(name = "Interview.findActiveInterviewsByInterviewer", 
	query="from InterviewEntity u where :user member of u.interviewers and u.date >= :date"),
	
})
public class InterviewEntity {
	
	public InterviewEntity(ApplicationEntity application, Date date, Collection<WorkerEntity> interviewers) {
		this.application = application;
		this.interviewers = new ArrayList<>();
		this.interviewers.addAll(interviewers);
		this.date = date;
	}

	public InterviewEntity() {
		
	}

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(nullable=false)
	private ApplicationEntity application;
	
	@Column
	private String classification;
	
	@Column(nullable=false)
	private Date date;
	
	@ManyToMany(mappedBy="interviews")
	private List<WorkerEntity> interviewers;

	
	
	// getters and setters
	
	public Date getDate() {
		return date;
	}



	public Collection<WorkerEntity> getInterviewers() {
		return interviewers;
	}
	
	public ApplicationEntity getApplication() {
		return application;
	}

}
