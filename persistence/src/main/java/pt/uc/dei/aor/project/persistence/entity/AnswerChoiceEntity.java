package pt.uc.dei.aor.project.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="answer_choice")
public class AnswerChoiceEntity implements Comparable<AnswerChoiceEntity>, Serializable {
	
	private static final long serialVersionUID = -7918665450611888841L;

	public AnswerChoiceEntity(String s) {
		answer = s;
	}

	public AnswerChoiceEntity() {
	}
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable=false, unique=false)
	private String answer;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((answer == null) ? 0 : answer.hashCode());
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
		AnswerChoiceEntity other = (AnswerChoiceEntity) obj;
		if (answer == null) {
			if (other.answer != null)
				return false;
		} else if (!answer.equals(other.answer))
			return false;
		return true;
	}

	public long getId() {
		return id;
	}

	public String getAnswer() {
		return answer;
	}

	@Override
	public int compareTo(AnswerChoiceEntity o) {
		return answer.compareTo(o.answer);
	}
	
	@Override
	public String toString() {
		return answer;
	}
}
