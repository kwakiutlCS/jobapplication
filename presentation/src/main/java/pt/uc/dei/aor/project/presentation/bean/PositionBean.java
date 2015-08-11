package pt.uc.dei.aor.project.presentation.bean;

import java.io.Serializable;
import java.time.LocalDate;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import pt.uc.dei.aor.project.business.model.IPublicationChanhel;
import pt.uc.dei.aor.project.business.service.IPositionBusinessService;


@Named
@RequestScoped
public class PositionBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private IPositionBusinessService position;
	
	//@Inject 
	private IPublicationChanhel channel;
	
	private LocalDate openingDate;
	private int code; 
	private String title;
	private int vacancies; 
	private LocalDate closingDate;
	private String sla;
	private String contactPerson;
	private String company;
	private String description;
	private String localization;
	private String positionState;
	private String technicalArea;
	
	public PositionBean() {
	}

	public IPositionBusinessService getPosition() {
		return position;
	}

	public void setPosition(IPositionBusinessService position) {
		this.position = position;
	}

	public IPublicationChanhel getChannel() {
		return channel;
	}

	public void setChannel(IPublicationChanhel channel) {
		this.channel = channel;
	}

	public LocalDate getOpeningDate() {
		return openingDate;
	}

	public void setOpeningDate(LocalDate openingDate) {
		this.openingDate = openingDate;
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

	public int getVacancies() {
		return vacancies;
	}

	public void setVacancies(int vacancies) {
		this.vacancies = vacancies;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocalization() {
		return localization;
	}

	public void setLocalization(String localization) {
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
		position.createNewPosition(openingDate, code, title, vacancies, 
				closingDate, sla, contactPerson, company, description);
		String result="new position";
		
		return  result;
	}
	
	
	
	
	
	

}
