package pt.uc.dei.aor.project.persistence.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import pt.uc.dei.aor.project.persistence.util.Localization;
import pt.uc.dei.aor.project.persistence.util.PositionState;

@Entity
@Table(name="position")
public class PositionEntity {
	
	@Id
	private int id;
	
	@Column
	private String code;
	
	@Column
	private String title;
	
	@Enumerated
	@Column
	private Localization localization;
	
	@Column
	private PositionState state;
	
	@Column
	private int numPositions;
	
	@Column
	private LocalDate openingDate;
	
	@Column
	private LocalDate closingDate;
	
	@Column 
	private String sla;
	
	// change variable name ??
	@Column
	private String contactPerson;
	
	@Column
	private String company;
	
	@ManyToMany
	private List<TechnicalAreaEntity> technicalAreas;
	
	@Column
	private String description;
	
//	@Column
//	private List<String> publication;
	
    @ManyToOne
    private ScriptEntity script;
}
