package pt.uc.dei.aor.project.persistence.entity;

import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="script")
@NamedQueries({
	@NamedQuery(name = "Script.findAllScripts",query="from ScriptEntity u"),
})
public class ScriptEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@OneToMany(cascade={CascadeType.MERGE, CascadeType.PERSIST})
	private Set<ScriptEntryEntity> entries;

	public long getId() {
		return id;
	}

	public Set<ScriptEntryEntity> getEntries() {
		return entries;
	}

	public void setEntries(Set<ScriptEntryEntity> entries) {
		this.entries = entries;
	}
}
