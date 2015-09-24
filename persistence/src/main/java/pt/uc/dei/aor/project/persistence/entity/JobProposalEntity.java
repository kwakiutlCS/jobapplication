package pt.uc.dei.aor.project.persistence.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import pt.uc.dei.aor.project.business.util.ProposalSituation;




@Entity
@Table(name="proposal")
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
	
	
	public JobProposalEntity() {
		situation = ProposalSituation.ONHOLD;
		date = new Date();
	}
	
	
	public ProposalSituation getSituation() {
		return situation;
	}

}
