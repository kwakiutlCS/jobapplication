package pt.uc.dei.aor.project.persistence.entity;

import java.text.Normalizer;

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
@NamedQueries({
	@NamedQuery(name = "Degree.findDegreesBySchool", 
			query = "from DegreeEntity u where u.institution = :school"),
})
public class DegreeEntity {
	
	public DegreeEntity() {
		
	}

	public DegreeEntity(InstitutionEntity institution, String degree, String typeDegree) {
		this.institution = institution;
		this.name = degree;
		this.type = typeDegree;
		this.normalized = Normalizer.normalize(degree, Normalizer.Form.NFD);
		this.normalized = normalized.replaceAll("[^\\p{ASCII}]", "");
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
	
	@Column
	private String normalized;

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}
	
}
