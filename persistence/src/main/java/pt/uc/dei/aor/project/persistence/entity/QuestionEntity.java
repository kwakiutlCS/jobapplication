package pt.uc.dei.aor.project.persistence.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import pt.uc.dei.aor.project.business.util.QuestionType;



@Entity
@Table(name="question")
public class QuestionEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable=false)
	private String text;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	private QuestionType questionType;
	
	@ManyToOne(cascade=CascadeType.ALL)
	private QuestionTopicEntity topic;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private Set<AnswerChoiceEntity> answers;
	
	@OneToOne(cascade=CascadeType.ALL)
	private QuestionScaleEntity scale;

	public QuestionEntity(String questionText, String questionType) {
		text = questionText;
		this.questionType = QuestionType.toEnum(questionType);
	}

	public QuestionEntity() {
		
	}
	
	public QuestionEntity(String questionText, String questionType, int min, int max) {
		text = questionText;
		this.questionType = QuestionType.toEnum(questionType);
		scale = new QuestionScaleEntity(min, max);
	}

	public QuestionEntity(String questionText, String questionType, List<String> options) {
		text = questionText;
		this.questionType = QuestionType.toEnum(questionType);
		answers = new HashSet<>();
		for (String s : options) {
			answers.add(new AnswerChoiceEntity(s));
		}
	}

	public String getText() {
		return text;
	}

	public String getType() {
		return questionType.toString();
	}

	public int getMin() {
		return scale.getMin();
	}
	
	public int getMax() {
		return scale.getMax();
	}

	public Collection<String> getAnswers() {
		List<String> list = new ArrayList<>();
		for (AnswerChoiceEntity a : answers) {
			list.add(a.getAnswer());
		}
		return list;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setMin(int min) {
		scale.setMin(min);
	}
	
	public void setMax(int max) {
		scale.setMax(max);
	}
	
	
	// add connection to ScriptEntryEntity???
}
