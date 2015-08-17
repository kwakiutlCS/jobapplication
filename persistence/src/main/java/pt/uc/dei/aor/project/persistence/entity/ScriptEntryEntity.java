package pt.uc.dei.aor.project.persistence.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="script_entry")
public class ScriptEntryEntity implements Comparable<ScriptEntryEntity> {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable=false)
	private int position;
	
	@OneToOne(cascade={CascadeType.MERGE, CascadeType.PERSIST})
	private QuestionEntity question;
	
	public ScriptEntryEntity(String questionText, String questionType, int position) {
		this.position = position;
		question = new QuestionEntity(questionText, questionType);
	}

	public ScriptEntryEntity(String questionText, String questionType, int position, int min, int max) {
		this.position = position;
		question = new QuestionEntity(questionText, questionType, min, max);
	}

	public ScriptEntryEntity() {
	}

	public ScriptEntryEntity(String questionText, String questionType, int position, List<String> options) {
		this.position = position;
		question = new QuestionEntity(questionText, questionType, options);
	}

	public QuestionEntity getQuestion() {
		return question;
	}


	@Override
	public int compareTo(ScriptEntryEntity o) {
		return position-o.position;
	}


	public int getPosition() {
		return position;
	}
	
}
