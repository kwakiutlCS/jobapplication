package pt.uc.dei.aor.project.persistence.entity;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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
	
	@OneToMany(cascade=CascadeType.ALL)
	private Set<AnswerChoiceEntity> answers;
	
	@OneToOne(cascade=CascadeType.ALL)
	private QuestionScale scale;

	public QuestionEntity(String questionText, String questionType) {
		text = questionText;
		this.questionType = QuestionType.toEnum(questionType);
	}

	public QuestionEntity() {
		
	}
	
	public QuestionEntity(String questionText, String questionType, int min, int max) {
		text = questionText;
		this.questionType = QuestionType.toEnum(questionType);
		scale = new QuestionScale(min, max);
	}

	public QuestionEntity(String questionText, String questionType, List<String> options) {
		text = questionText;
		this.questionType = QuestionType.toEnum(questionType);
		answers = new TreeSet<>();
		for (String s : options) {
			answers.add(new AnswerChoiceEntity(s));
		}
	}

	public String getText() {
		return text;
	}
	
	
	// add connection to ScriptEntryEntity???
}
