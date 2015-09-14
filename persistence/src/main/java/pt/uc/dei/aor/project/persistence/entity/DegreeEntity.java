package pt.uc.dei.aor.project.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="degree")
public class DegreeEntity {
	
	public DegreeEntity() {
		
	}

	public DegreeEntity(InstitutionEntity institution, String degree, String typeDegree) {
		this.institution = institution;
		this.name = degree;
		this.type = typeDegree;
	}

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	@Column
	private String name;
	
	@ManyToOne
	private InstitutionEntity institution;
	
	@Column
	private String type;
	
}
