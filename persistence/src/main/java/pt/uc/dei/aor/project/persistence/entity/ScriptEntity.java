package pt.uc.dei.aor.project.persistence.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="script")
public class ScriptEntity {

	@Id
	private int id;
	
	@OneToMany
	private List<ScriptEntryEntity> entries;
}
