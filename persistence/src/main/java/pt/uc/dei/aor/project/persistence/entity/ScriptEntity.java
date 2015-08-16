package pt.uc.dei.aor.project.persistence.entity;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="script")
@NamedQueries({
	@NamedQuery(name = "Script.findAllScripts",query="from ScriptEntity u"),
})
public class ScriptEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@OrderBy("position ASC")
	@OneToMany(cascade={CascadeType.MERGE, CascadeType.PERSIST}, fetch=FetchType.EAGER)
	private SortedSet<ScriptEntryEntity> entries;

	@Transient
	private int nextPosition;
	
	public ScriptEntity() {
		this.entries = new TreeSet<>();
		nextPosition = 0;
	}
	
	public long getId() {
		return id;
	}

	public SortedSet<ScriptEntryEntity> getEntries() {
		return entries;
	}

	public void setEntries(SortedSet<ScriptEntryEntity> entries) {
		this.entries = entries;
	}

	public int getNextPosition() {
		return nextPosition++;
	}
	
	
}
