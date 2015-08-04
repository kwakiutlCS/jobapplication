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
	
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column
	private int id_answer;

	@ManyToOne
	@Column
	private InterviewEntity interview;

}
