 package pt.uc.dei.aor.project.presentation.bean;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import pt.uc.dei.aor.project.business.service.IPositionBusinessService;
import pt.uc.dei.aor.project.business.util.Localization;


@Named
@SessionScoped
public class PositionBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private IPositionBusinessService position;
	
	
	private Date openingDate;
	private String title;
	private int vacancies; 
	private Date closingDate;
	private String sla;
	private String contactPerson;
	private String company;
	private String description;
	private Localization localization;
	private String positionState;
	private String technicalArea;
	
	
	public PositionBean() {
	}

	
	public List<Localization> getLocalizations(){
		
		return Arrays.asList(Localization.values());
	}
	
	
	public IPositionBusinessService getPosition() {
		return position;
	}

	public void setPosition(IPositionBusinessService position) {
		this.position = position;
	}

	public Date getOpeningDate() {
		return openingDate;
	}

	public void setOpeningDate(Date openingDate) {
		this.openingDate = openingDate;
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getVacancies() {
		return vacancies;
	}

	public void setVacancies(int vacancies) {
		this.vacancies = vacancies;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public Localization getLocalization() {
		return localization;
	}


	public void setLocalization(Localization localization) {
		this.localization = localization;
	}


	public String getPositionState() {
		return positionState;
	}

	public void setPositionState(String positionState) {
		this.positionState = positionState;
	}

	public String getTechnicalArea() {
		return technicalArea;
	}

	public void setTechnicalArea(String technicalArea) {
		this.technicalArea = technicalArea;
	}
	
	public String createPosition() {
		
		technicalArea = "um";
		positionState = "três";
		
		position.createNewPosition(openingDate, title, localization, vacancies, closingDate, sla, contactPerson, company, description);
		String result="new position";
		System.out.println("Dat ab "+openingDate+" Title "+title+" Vacancies "+vacancies+" closingdate "+ closingDate+" sla "+sla+
				" contact "+contactPerson+" company "+company+" description "+description);
		return  result;
	}
	
	
	
	
	
	

}
