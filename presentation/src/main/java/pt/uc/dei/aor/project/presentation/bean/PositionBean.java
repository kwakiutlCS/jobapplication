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

import pt.uc.dei.aor.project.business.model.IPublicationChannel;
import pt.uc.dei.aor.project.business.model.IScript;
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
	
	private String code;
	private Date openingDate;
	private String title;
	private int vacancies; 
	private Date closingDate;
	private String sla;
	private String contactPerson;
	private String company;
	private String description;
	private List<Localization> localizations;
	private PositionState state;
	private List<TechnicalArea> technicalAreas;
	private IScript script;
	private List<IPublicationChannel> channels;
	
	public PositionBean() {
	}

	
	public List<Localization> getLocalizations(){
		
		List<Localization> localizations = new ArrayList<Localization>(EnumSet.allOf(Localization.class));
		
		return localizations;
	}
	
	public List<IPublicationChannel> getChannels() {
				
		return channels;
	}


	public void setChannels(List<IPublicationChannel> channels) {
		this.channels = channels;
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

	
	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
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


	public void setLocalizations(List<Localization> localizations) {
		this.localizations = localizations;
	}


	public PositionState getPositionState() {
		return state;
	}


	public void setPositionState(PositionState state) {
		this.state = state;
	}


	public void setTechnicalAreas(List<TechnicalArea> technicalAreas) {
		this.technicalAreas = technicalAreas;
	}


	public String createPosition() {
		
		position.createNewPosition(code, openingDate, title, localizations, state, vacancies, closingDate, sla, contactPerson,
				company, technicalAreas, description, script, channels);
		
		String result="new position";
		
		return  result;
	}


}
