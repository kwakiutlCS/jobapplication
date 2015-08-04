package pt.uc.dei.aor.project.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="question_topic")
public class QuestionTopicEntity {
	
	@Id
	private int id;
	
	@Column
	private String title;
}
