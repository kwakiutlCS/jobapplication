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
import javax.persistence.JoinColumn;
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
	@Column(nullable=false, unique=true)
	private int id;
	
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(nullable=false, unique=true)
	private int code;
	
	@Column(nullable=false)
	private String title;
	
	@ElementCollection
	@CollectionTable(name="position_localization", joinColumns = @JoinColumn(name="id_position"))
	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	private List<Localization> localization;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	private PositionState state;
	
	@Column(nullable=false)
	private int vacancies;
	
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Localization> getLocalization() {
		return localization;
	}

	public void setLocalization(List<Localization> localization) {
		this.localization = localization;
	}

	public PositionState getState() {
		return state;
	}

	public void setState(PositionState state) {
		this.state = state;
	}

	public int getVacancies() {
		return vacancies;
	}

	public void setVacancies(int vacancies) {
		this.vacancies = vacancies;
	}

	public LocalDate getOpeningDate() {
		return openingDate;
	}

	public void setOpeningDate(LocalDate openingDate) {
		this.openingDate = openingDate;
	}

	public LocalDate getClosingDate() {
		return closingDate;
	}

	public void setClosingDate(LocalDate closingDate) {
		this.closingDate = closingDate;
	}

	public String getSla() {
		return sla;
	}

	public void setSla(String sla) {
		this.sla = sla;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public List<TechnicalArea> getTechnicalAreas() {
		return technicalAreas;
	}

	public void setTechnicalAreas(List<TechnicalArea> technicalAreas) {
		this.technicalAreas = technicalAreas;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<PublicationChannelEntity> getPublications() {
		return publications;
	}

	public void setPublications(Set<PublicationChannelEntity> publications) {
		this.publications = publications;
	}

	public ScriptEntity getScript() {
		return script;
	}

	public void setScript(ScriptEntity script) {
		this.script = script;
	}
    
    
    
    
}

