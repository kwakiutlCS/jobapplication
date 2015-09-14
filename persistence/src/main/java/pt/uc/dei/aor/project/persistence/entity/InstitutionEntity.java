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
@Table(name="instituition")
@NamedQueries({
	@NamedQuery(name = "Institution.countSchools", query = "select count(u) from InstitutionEntity u"),
	@NamedQuery(name = "Institution.findSchoolByName", 
	query = "from InstitutionEntity u where u.name = :name"),
})
public class InstitutionEntity {
	
	public InstitutionEntity() {
		
	}
	
	public InstitutionEntity(String school) {
		this.name = school;
		this.normalized = Normalizer.normalize(school, Normalizer.Form.NFD);
		this.normalized = normalized.replaceAll("[^\\p{ASCII}]", "");
	}

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	@Column
	private String name;
	
	@Column
	private String normalized;

	public String getName() {
		return name;
	}
	
}
