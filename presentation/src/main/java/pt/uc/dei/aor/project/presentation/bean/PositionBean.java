package pt.uc.dei.aor.project.presentation.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.uc.dei.aor.project.business.model.IPosition;
import pt.uc.dei.aor.project.business.model.IPublicationChannel;
import pt.uc.dei.aor.project.business.model.IScript;
import pt.uc.dei.aor.project.business.model.IWorker;
import pt.uc.dei.aor.project.business.service.IPositionBusinessService;
import pt.uc.dei.aor.project.business.util.Localization;
import pt.uc.dei.aor.project.business.util.PositionState;
import pt.uc.dei.aor.project.business.util.TechnicalArea;
import pt.uc.dei.aor.project.presentation.util.MetaUtils;

@Named
@ViewScoped
public class PositionBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory.getLogger(LoginBean.class);
	
	@Inject
	private IPositionBusinessService positionService;
		
	private String title;
	private int vacancies = 1; 
	private Date closingDate;
	private int sla = 1;
	private IWorker contactPerson;
	private String company;
	private String description;
	private PositionState state;
	private List<IScript> scripts;
	private IScript script;
	private List<IPublicationChannel> channels;
	private List<Localization> selectedLocalizations;
	private List<TechnicalArea> selectedTechnicalAreas;
	private List<IPublicationChannel> selectedChannels;
	private Date today = new Date();
	
	private long posId;
	private IPosition position;
	
	public void onload() {
		position = positionService.findPositionById(posId);
	}
	
	public PositionBean() {
	}
	
	public void removeScript(IScript script) {
		scripts.remove(script);
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
	
	public void updatePosition() {
		position = positionService.updatePosition(position);
		MetaUtils.setMsg("Position updated", FacesMessage.SEVERITY_INFO);
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


	public int getSla() {
		return sla;
	}


	public void setSla(int sla) {
		this.sla = sla;
	}


	public IWorker getContactPerson() {
		return contactPerson;
	}


	public void setContactPerson(IWorker contactPerson) {
		this.contactPerson = contactPerson;
	}


	public String getCompany() {
		return company;
	}


	public List<IScript> getScripts() {
		return scripts;
	}

	public void setScript(List<IScript> scripts) {
		this.scripts = scripts;
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
	}


	public PositionState getPositionState() {
		return state;
	}


	public void setPositionState(PositionState state) {
		this.state = state;
	}


	public void setTechnicalAreas(List<TechnicalArea> technicalAreas) {
	}
	
	public List<Localization> getSelectedLocalizations() {
		
		return selectedLocalizations;
	}
		
	public void setSelectedLocalizations(List<Localization> selectedLocalizations) {
		this.selectedLocalizations = selectedLocalizations;
	}

	public List<TechnicalArea> getSelectedTechnicalAreas() {
		
		return selectedTechnicalAreas;
	}
	
	public void setSelectedTechnicalAreas(List<TechnicalArea> selectedTechnicalAreas) {
		this.selectedTechnicalAreas = selectedTechnicalAreas;
	}

	public List<IPublicationChannel> getSelectedChannels() {
		return selectedChannels;
	}

	public void setSelectedChannels(List<IPublicationChannel> selectedChannels) {
		this.selectedChannels = selectedChannels;
	}

	public void createPosition() {
		if (scripts == null || scripts.isEmpty()) {
			MetaUtils.setMsg("Please select at least a script", FacesMessage.SEVERITY_ERROR);
		}
		positionService.createNewPosition(title, selectedLocalizations, state, vacancies, closingDate, sla, contactPerson,
				company, selectedTechnicalAreas, description, scripts, selectedChannels);
		title = null;
		selectedLocalizations = null;
		state = null;
		vacancies = 1;
		closingDate = null;
		sla = 1;
		contactPerson = null;
		company = null;
		selectedTechnicalAreas = null;
		description = null;
		script = null;
		selectedChannels = null;
		
		MetaUtils.setMsg("Position created", FacesMessage.SEVERITY_INFO);
	}

	public IScript getScript() {
		return script;
	}

	public void setScript(IScript script) {
		this.script = script;
	}

	public void addScript() {
		if (script == null) return;
		if (scripts == null) scripts = new ArrayList<>();
		
		scripts.remove(script);
		scripts.add(script);
	}

	public Date getToday() {
		return today;
	}

	public void setToday(Date today) {
		this.today = today;
	}

	public long getPosId() {
		return posId;
	}

	public void setPosId(long posId) {
		this.posId = posId;
	}

	public IPosition getPosition() {
		return position;
	}
	
	public void setPosition(IPosition position) {
		this.position = position;
	}
}
