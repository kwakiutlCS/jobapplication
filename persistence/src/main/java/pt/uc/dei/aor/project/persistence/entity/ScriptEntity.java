package pt.uc.dei.aor.project.persistence.entity;

import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
	
	@Column(nullable=false)
	private String title;
	
	@OrderBy("position ASC")
	@OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.EAGER)
	private SortedSet<ScriptEntryEntity> entries;

	public ScriptEntity() {
		this.entries = new TreeSet<>();
	}
	
	public ScriptEntity(String title) {
		this.entries = new TreeSet<>();
		this.title = title;
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
		 if (entries.size() == 0) return 0;
		  return entries.last().getPosition()+1;

	}

	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
}
