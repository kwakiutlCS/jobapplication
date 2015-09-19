package pt.uc.dei.aor.project.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="phase")
public class PhaseEntity implements Serializable, Comparable<PhaseEntity> {

	private static final long serialVersionUID = -5877196134761879608L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	@Column(nullable=false)
	private int orderNumber;
	
	@OneToOne
	private ScriptEntity script;
	
	public PhaseEntity() {
		
	}
	
	public PhaseEntity(ScriptEntity script, int order) {
		this.script = script;
		this.orderNumber = order;
	}


	@Override
	public int compareTo(PhaseEntity o) {
		return orderNumber-o.orderNumber;
	}

	public ScriptEntity getScript() {
		return script;
	}
	
	@Override
	public String toString() {
		return "PHASE: "+orderNumber;
	}
}
