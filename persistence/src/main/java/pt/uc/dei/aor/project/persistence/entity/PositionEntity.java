package pt.uc.dei.aor.project.persistence.entity;

import java.util.Date;
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

import pt.uc.dei.aor.project.business.util.Localization;
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
	

	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	private Localization localization;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable=true)
	private PositionState state;
	
	@Column(nullable=false)
	private int vacancies;
	
	@Column(nullable=false)
	private Date openingDate;
	
	@Column(nullable=false)
	private Date closingDate;
	
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
    
    
    

	public PositionEntity(String title, Localization localization,
			PositionState state, int vacancies, Date openingDate,
			Date closingDate, String sla, String contactPerson, String company,
			List<TechnicalArea> technicalAreas, String description,
			Set<PublicationChannelEntity> publications, ScriptEntity script) {
		super();
		this.title = title;
		this.localization = localization;
		this.state = state;
		this.vacancies = vacancies;
		this.openingDate = openingDate;
		this.closingDate = closingDate;
		this.sla = sla;
		this.contactPerson = contactPerson;
		this.company = company;
		this.technicalAreas = technicalAreas;
		this.description = description;
		this.publications = publications;
		this.script = script;
	}

	public PositionEntity() {
		// TODO Auto-generated constructor stub
	}

	public PositionEntity(Date openingDate2, String title2,
			Localization localization2, int vacancies2, Date closingDate2,
			String sla2, String contactPerson2, String company2,
			String description2, Set<PublicationChannelEntity> publications2,
			ScriptEntity script2) {
		// TODO Auto-generated constructor stub
	}

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

	public Localization getLocalization() {
		return localization;
	}

	public void setLocalization(Localization localization) {
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

	public Date getOpeningDate() {
		return openingDate;
	}

	public void setOpeningDate(Date openingDate) {
		this.openingDate = openingDate;
	}

	public Date getClosingDate() {
		return closingDate;
	}

	public void setClosingDate(Date closingDate) {
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

