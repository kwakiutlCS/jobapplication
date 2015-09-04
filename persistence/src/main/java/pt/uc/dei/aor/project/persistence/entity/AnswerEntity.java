package pt.uc.dei.aor.project.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="answer")
public class AnswerEntity {
	
	public AnswerEntity(InterviewEntity interview, String question, String answer, int position) {
		this.interview = interview;
		this.question = question;
		this.answer = answer;
		this.position = position;
	}
	
	public AnswerEntity() {
		
	}

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	@ManyToOne
	private InterviewEntity interview;
	
	@Column
	private String question;
	
	@Column
	private String answer;
	
	@Column
	private int position;
	

}
