 package pt.uc.dei.aor.project.presentation.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import pt.uc.dei.aor.project.business.service.IPositionBusinessService;
import pt.uc.dei.aor.project.business.util.Localization;
import pt.uc.dei.aor.project.business.util.PositionState;
import pt.uc.dei.aor.project.business.util.TechnicalArea;


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
	private PositionState state;
	private List<String> technicalAreas;
	
	public PositionBean() {
	}

	
	public List<Localization> getLocalizations(){
		
		return Arrays.asList(Localization.values());
	}
	
	public List<PositionState> getPositionStates(){
		
		return Arrays.asList(PositionState.values());
	}
	
	public List<TechnicalArea> getTechnicalAreas(){
		
		List<TechnicalArea> technicalAreas =
                new ArrayList<TechnicalArea>(EnumSet.allOf(TechnicalArea.class));
		
		return technicalAreas;
				
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


	public PositionState getPositionState() {
		return state;
	}


	public void setPositionState(PositionState state) {
		this.state = state;
	}


	public void setTechnicalAreas(List<String> technicalAreas) {
		this.technicalAreas = technicalAreas;
	}


	public String createPosition() {
		
		position.createNewPosition(openingDate, title, localization, state, vacancies, closingDate, sla, contactPerson,
				company, technicalAreas, description);
		
		String result="new position";
		
		return  result;
	}


}
