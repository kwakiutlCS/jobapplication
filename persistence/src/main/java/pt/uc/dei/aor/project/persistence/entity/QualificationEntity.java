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
@Table(name="qualification")
public class QualificationEntity {
	
	public QualificationEntity() {
		
	}

	public QualificationEntity(String school, String degree) {
		this.degree = degree;
		this.institution = school;
	}

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	@Column
	private String institution;
	
	@Column
	private String degree;

	public String getSchool() {
		return institution;
	}
	
	public String getDegree() {
		return degree;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((degree == null) ? 0 : degree.hashCode());
		result = prime * result + ((institution == null) ? 0 : institution.hashCode());
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
		QualificationEntity other = (QualificationEntity) obj;
		if (degree == null) {
			if (other.degree != null)
				return false;
		} else if (!degree.equals(other.degree))
			return false;
		if (institution == null) {
			if (other.institution != null)
				return false;
		} else if (!institution.equals(other.institution))
			return false;
		return true;
	}
	
	
	
}
