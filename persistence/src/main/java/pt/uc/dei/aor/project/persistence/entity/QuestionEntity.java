package pt.uc.dei.aor.project.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="question")
public class QuestionEntity {
	
	@Id
	private int id;
	
	@Column
	private String text;
	
	// to change
	@Column
	private String responseType;
	
	@ManyToOne
	private QuestionTopicEntity topic;
	
	// add connection to ScriptEntryEntity???
}
