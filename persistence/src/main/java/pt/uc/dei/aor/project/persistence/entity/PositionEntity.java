package pt.uc.dei.aor.project.persistence.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import pt.uc.dei.aor.project.persistence.util.Localization;
import pt.uc.dei.aor.project.persistence.util.PositionState;
import pt.uc.dei.aor.project.persistence.util.TechnicalArea;

@Entity
@Table(name="position")
public class PositionEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable=false, unique=true)
	private String code;
	
	@Column(nullable=false)
	private String title;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	private Localization localization;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	private PositionState state;
	
	@Column(nullable=false)
	private int numPositions;
	
	@Column(nullable=false)
	private LocalDate openingDate;
	
	@Column(nullable=false)
	private LocalDate closingDate;
	
	@Column(nullable=false) 
	private String sla;
	
	@Column(nullable=false)
	private String contactPerson;
	
	@Column(nullable=false)
	private String company;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name="technical_area", indexes={@Index(columnList="positionentity_id")})
	@Enumerated(EnumType.STRING)
	private List<TechnicalArea> technicalAreas;
	
	@Column
	private String description;
	
	@ManyToMany
	private Set<PublicationChannelEntity> publications;
	
    @ManyToOne
    private ScriptEntity script;
}
