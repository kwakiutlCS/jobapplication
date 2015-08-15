package pt.uc.dei.aor.project.persistence.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import pt.uc.dei.aor.project.business.model.IScriptEntry;

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

	
	public ScriptEntryEntity(String questionText, String questionType) {
		position = 1;
		question = new QuestionEntity(questionText, questionType);
	}


	public ScriptEntryEntity() {
	}


	public QuestionEntity getQuestion() {
		return question;
	}


	@Override
	public int compareTo(ScriptEntryEntity o) {
		return position-o.position;
	}
	
	
	// add connection to script ???
}
