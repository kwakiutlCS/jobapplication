package pt.uc.dei.aor.project.persistence.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name="interview")
@NamedQueries({
	@NamedQuery(name = "Interview.findAllInterviews", query="from InterviewEntity u"),
})
public class InterviewEntity {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id_interview;

	@ManyToOne
	private ApplicationEntity application;
	
	@Column
	private String classification;
	
	@Column(nullable=false)
	private Date date;

}
