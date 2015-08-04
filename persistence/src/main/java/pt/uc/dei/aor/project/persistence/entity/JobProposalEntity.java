package pt.uc.dei.aor.project.persistence.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name="proposal")
public class JobProposalEntity {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column
	private String id_proposal;
	@Enumerated(EnumType.STRING)
	@Column
	private String situation;
	@Column
	private LocalDate date;
	
	
	@OneToOne
	private ApplicationEntity application;
	

}
