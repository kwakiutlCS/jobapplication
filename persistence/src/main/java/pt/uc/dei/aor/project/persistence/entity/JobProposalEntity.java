package pt.uc.dei.aor.project.persistence.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import pt.uc.dei.aor.project.business.util.ProposalSituation;




@Entity
@Table(name="proposal")
@NamedQueries({
	@NamedQuery(name = "Proposition.findByDate", 
			query = "from JobProposalEntity p where p.date >= :startDate"),
	@NamedQuery(name = "Proposition.findOrphans", 
			query = "from JobProposalEntity p where p.application is null"),
	@NamedQuery(name = "Proposition.findAll", 
			query = "from JobProposalEntity p"),
})
public class JobProposalEntity {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column
	private long id;
	
	@Enumerated(EnumType.STRING) 
	@Column
	private ProposalSituation situation;
	
	@Column
	private Date date;
	
	@OneToOne(mappedBy="proposal")
	private ApplicationEntity application;
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((application == null) ? 0 : application.hashCode());
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
		JobProposalEntity other = (JobProposalEntity) obj;
		if (application == null) {
			if (other.application != null)
				return false;
		} else if (!application.equals(other.application))
			return false;
		return true;
	}


	public JobProposalEntity() {
		situation = ProposalSituation.ONHOLD;
		date = new Date();
	}
	
	
	public ProposalSituation getSituation() {
		return situation;
	}


	public ApplicationEntity getApplication() {
		return application;
	}

	
}
