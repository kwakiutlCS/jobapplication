package pt.uc.dei.aor.project.persistence.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import pt.uc.dei.aor.project.business.model.IAnswerChoice;
import pt.uc.dei.aor.project.business.util.QuestionType;
import pt.uc.dei.aor.project.persistence.proxy.IProxyToEntity;

@Entity
@Table(name="script_entry")
@NamedQueries({
	@NamedQuery(name = "ScriptEntry.findAnswer",query="from ScriptEntity u where id = :id"),
})
public class ScriptEntryEntity implements Comparable<ScriptEntryEntity>, Serializable {

	private static final long serialVersionUID = 8782324129568358490L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable=false)
	private int position;
	
	@OneToOne(cascade={CascadeType.ALL}, fetch=FetchType.EAGER)
	private QuestionEntity question;
	
	public ScriptEntryEntity(String questionText, QuestionType questionType, int position) {
		this.position = position;
		question = new QuestionEntity(questionText, questionType);
	}

	public ScriptEntryEntity(String questionText, QuestionType questionType, int position, int min, int max) {
		this.position = position;
		question = new QuestionEntity(questionText, questionType, min, max);
	}

	public ScriptEntryEntity() {
	}

	public ScriptEntryEntity(String questionText, QuestionType questionType, int position, List<AnswerChoiceEntity> list) {
		this.position = position;
		question = new QuestionEntity(questionText, questionType, list);
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

	public void setPosition(int position) {
		this.position = position;
	}
	
	public String toString() {
		return position+" -> "+question.getText();
	}

	public long getId() {
		return id;
	}

	public void addAnswer(AnswerChoiceEntity option) {
		question.addAnswer(option);
	}

	public void removeAnswer(AnswerChoiceEntity answer) {
		question.removeAnswer(answer);
	}
}
