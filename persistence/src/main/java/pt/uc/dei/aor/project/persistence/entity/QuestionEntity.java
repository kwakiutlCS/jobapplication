package pt.uc.dei.aor.project.persistence.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.SortedSet;
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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import pt.uc.dei.aor.project.business.util.QuestionType;



@Entity
@Table(name="question")
public class QuestionEntity implements Serializable {
	
	private static final long serialVersionUID = -5439985035795769733L;

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
	
	@OrderBy("answer ASC")
	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private SortedSet<AnswerChoiceEntity> answers;
	
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private QuestionScaleEntity scale;

	public QuestionEntity(String questionText, QuestionType questionType) {
		text = questionText;
		this.questionType = questionType;
	}

	public QuestionEntity() {
		
	}
	
	public QuestionEntity(String questionText, QuestionType questionType, int min, int max) {
		text = questionText;
		this.questionType = questionType;
		scale = new QuestionScaleEntity(min, max);
	}

	public QuestionEntity(String questionText, QuestionType questionType, List<AnswerChoiceEntity> list) {
		text = questionText;
		this.questionType = questionType;
		answers = new TreeSet<>();
		answers.addAll(list);
		
		System.out.println(answers);
	}

	public String getText() {
		return text;
	}

	public QuestionType getType() {
		return questionType;
	}

	public int getMin() {
		return scale.getMin();
	}
	
	public int getMax() {
		return scale.getMax();
	}

	public Collection<AnswerChoiceEntity> getAnswers() {
		List<AnswerChoiceEntity> list = new ArrayList<>();
		list.addAll(answers);
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

	public void addAnswer(AnswerChoiceEntity option) {
		System.out.println("OPTION ADD: "+option);
		for (AnswerChoiceEntity ace : answers) {
			System.out.println(ace+" -> "+ace.equals(option));
		}
		answers.add(option);
		
		System.out.println("OPTION AFTER: ");
		for (AnswerChoiceEntity ace : answers) {
			System.out.println(ace+" -> "+ace.equals(option));
		}
	}

	public void removeAnswer(AnswerChoiceEntity answer) {
		System.out.println("OPTION REMOVE: "+answer);
		for (AnswerChoiceEntity ace : answers) {
			System.out.println(ace+" -> "+ace.equals(answer));
		}
		
		answers.remove(answer);
		
		System.out.println("OPTION AFTER: ");
		for (AnswerChoiceEntity ace : answers) {
			System.out.println(ace+" -> "+ace.equals(answer));
		}
	}
	
	@Override
	public String toString() {
		return id+" -> "+text;
	}
	
	// add connection to ScriptEntryEntity???
}
