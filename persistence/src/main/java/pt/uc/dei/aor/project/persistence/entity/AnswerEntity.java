package pt.uc.dei.aor.project.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="answer")
@NamedQueries({
	@NamedQuery(name = "Answer.findAnswerByInterviewAndQuestion", 
			query = "from AnswerEntity u where u.question = :question and u.interview = :interview"),
})
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
	@JoinColumn(nullable=false)
	private InterviewEntity interview;
	
	@Column(nullable=false)
	private String question;
	
	@Column(nullable=false)
	private String answer;
	
	@Column(nullable=false)
	private int position;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((interview == null) ? 0 : interview.hashCode());
		result = prime * result + ((question == null) ? 0 : question.hashCode());
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
		AnswerEntity other = (AnswerEntity) obj;
		if (interview == null) {
			if (other.interview != null)
				return false;
		} else if (!interview.equals(other.interview))
			return false;
		if (question == null) {
			if (other.question != null)
				return false;
		} else if (!question.equals(other.question))
			return false;
		return true;
	}

	public String getQuestion() {
		return question;
	}
	
	public InterviewEntity getInterview() {
		return interview;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getAnswer() {
		return answer;
	}
	
}
