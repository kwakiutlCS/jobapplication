package pt.uc.dei.aor.project.persistence.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
	
	@ManyToOne
	private QuestionTopicEntity topic;
	
	@OneToMany
	private Set<AnswerChoiceEntity> answers;

	public QuestionEntity(String questionText, String questionType) {
		text = questionText;
		this.questionType = QuestionType.toEnum(questionType);
	}

	public QuestionEntity() {
		
	}
	
	public String getText() {
		return text;
	}
	
	
	// add connection to ScriptEntryEntity???
}
