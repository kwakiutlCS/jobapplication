package pt.uc.dei.aor.project.persistence.entity;

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
	@OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.EAGER)
	private SortedSet<ScriptEntryEntity> entries;

	public ScriptEntity() {
		this.entries = new TreeSet<>();
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
		return entries.last().getPosition()+1;
	}
	
	
}
