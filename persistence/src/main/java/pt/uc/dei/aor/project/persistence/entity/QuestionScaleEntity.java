package pt.uc.dei.aor.project.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="question_scale")
public class QuestionScaleEntity {
	
	public QuestionScaleEntity(int min, int max) {
		this.min = min;
		this.max = max;
	}
	
	public QuestionScaleEntity() {
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable=false)
	private int min;
	
	@Column(nullable=false)
	private int max;

	public int getMin() {
		return min;
	}

	public int getMax() {
		return max;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setMin(int min) {
		this.min = min;
	}
	
	public void setMax(int max) {
		this.max = max;
	}
	
	
}
