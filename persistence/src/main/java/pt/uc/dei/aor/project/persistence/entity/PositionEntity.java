package pt.uc.dei.aor.project.persistence.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import pt.uc.dei.aor.project.business.util.Localization;
import pt.uc.dei.aor.project.business.util.PositionState;
import pt.uc.dei.aor.project.business.util.TechnicalArea;


@Entity
@Table(name="position")
@NamedQueries({
	@NamedQuery(name="position.findAll", query="from PositionEntity u"),
	@NamedQuery(name="position.findMaxCode", query="SELECT max(u.code) FROM PositionEntity u"),
	@NamedQuery(name = "Position.findPositionByTitle", 
	query = "from PositionEntity u where u.title like :title"),
})
public class PositionEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable=false, unique=true)
	private long code;
	
	@Column(nullable=false)
	private String title;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name="localization", indexes={@Index(columnList="positionentity_id")})
	@Enumerated(EnumType.STRING)
	private List<Localization> localizations;
	
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
	private int sla;
	
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
	

	@ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	private Set<PublicationChannelEntity> publications;
	
    @ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    private Set<PhaseEntity> phases;
    
    
	public PositionEntity(long code,String title, Collection<Localization> localizations,
			PositionState state, int vacancies, Date openingDate,
			Date closingDate, int sla, String contactPerson, String company,
			Collection<TechnicalArea> technicalAreas, String description,
			Collection<PublicationChannelEntity> publications, List<ScriptEntity> scripts) {
		super();
		this.code=code;
		this.title = title;
		this.localizations = new ArrayList<>();
		this.localizations.addAll(localizations);
		this.state = state;
		this.vacancies = vacancies;
		this.openingDate = openingDate;
		this.closingDate = closingDate;
		this.sla = sla;
		this.contactPerson = contactPerson;
		this.company = company;
		this.technicalAreas = new ArrayList<>();
		this.technicalAreas.addAll(technicalAreas);
		this.description = description;
		this.publications = new TreeSet<>();
		this.publications.addAll(publications);
		
		phases = new TreeSet<>();
		int i = 1;
		for (ScriptEntity s : scripts)
			this.phases.add(new PhaseEntity(s, i++));
		
	}

	public PositionEntity() {
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCode() {
		return code;
	}

	public void setCode(long code) {
		this.code = code;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Localization> getLocalizations() {
		return new ArrayList<>(localizations);
	}

	public void setLocalizations(List<Localization> localizations) {
		this.localizations.addAll(localizations);
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

	public int getSla() {
		return sla;
	}

	public void setSla(int sla) {
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
		return new ArrayList<>(technicalAreas);
	}

	public void setTechnicalAreas(List<TechnicalArea> technicalAreas) {
		this.technicalAreas.addAll(technicalAreas);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<PublicationChannelEntity> getPublications() {
		return new TreeSet<>(publications);
	}

	public void setPublications(Set<PublicationChannelEntity> publications) {
		this.publications.addAll(publications);
	}

	public List<ScriptEntity> getScripts() {
		List<ScriptEntity> scripts = new ArrayList<>();
		
		for (PhaseEntity p : phases)
			scripts.add(p.getScript());
		
		return scripts;
	}

	public void setScripts(List<ScriptEntity> scripts) {
		phases = new TreeSet<>();
		
		int i = 1;
		for (ScriptEntity s : scripts) {
			phases.add(new PhaseEntity(s, i++));
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((openingDate == null) ? 0 : openingDate.hashCode());
		result = prime * result + ((technicalAreas == null) ? 0 : technicalAreas.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		PositionEntity other = (PositionEntity) obj;
		if (openingDate == null) {
			if (other.openingDate != null)
				return false;
		} else if (!openingDate.equals(other.openingDate))
			return false;
		if (technicalAreas == null) {
			if (other.technicalAreas != null)
				return false;
		} else if (!technicalAreas.equals(other.technicalAreas))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
    
    
    
    
}

