package pt.uc.dei.aor.project.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="script_entry")
public class ScriptEntryEntity {

	@Id
	private int id;
	
	@Column
	private int position;
	
	@OneToOne
	private QuestionEntity question;
	
	
	// add connection to script ???
}
