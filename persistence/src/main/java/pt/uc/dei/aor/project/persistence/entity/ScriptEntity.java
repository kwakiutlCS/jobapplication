package pt.uc.dei.aor.project.persistence.entity;

import java.io.Serializable;
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
	@NamedQuery(name = "Script.findAllScripts", 
			query="from ScriptEntity u where u.entries is not empty and u.deleted = false"),
})
public class ScriptEntity implements Serializable {

	private static final long serialVersionUID = -5877196134761879608L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable=false, unique=true)
	private String title;
	
	@Column
	private boolean deleted = false;
	
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ScriptEntity other = (ScriptEntity) obj;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ScriptEntity [title=" + title + "]";
	}

	public void setDeleted(boolean b) {
		deleted = b;
	}
	
	
}
