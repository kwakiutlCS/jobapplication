package pt.uc.dei.aor.project.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="script")
public class ScriptEntity {

	@Id
	private int id;
}
