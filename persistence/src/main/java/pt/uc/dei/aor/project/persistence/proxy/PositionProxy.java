package pt.uc.dei.aor.project.persistence.proxy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import pt.uc.dei.aor.project.business.model.IPosition;
import pt.uc.dei.aor.project.business.model.IPublicationChannel;
import pt.uc.dei.aor.project.business.model.IScript;
import pt.uc.dei.aor.project.business.util.Localization;
import pt.uc.dei.aor.project.business.util.PositionState;
import pt.uc.dei.aor.project.business.util.TechnicalArea;
import pt.uc.dei.aor.project.persistence.entity.PositionEntity;
import pt.uc.dei.aor.project.persistence.entity.PublicationChannelEntity;
import pt.uc.dei.aor.project.persistence.entity.ScriptEntity;


public class PositionProxy implements IPosition, IProxyToEntity<PositionEntity> {

	private PositionEntity entity;

	public PositionProxy(PositionEntity entity) {
		this.entity = entity != null ? entity : new PositionEntity();
	}

	@SuppressWarnings("unchecked")
	public PositionProxy(String code, Date openingDate, String title,
			Collection<Localization> localizations, PositionState state,
			int vacancies, Date closingDate, String sla, String contactPerson,
			String company, Collection<TechnicalArea> technicalAreas,
			String description, IScript script,
			Collection<IPublicationChannel> channels){


		Set<PublicationChannelEntity> publicationChannelEntities = new TreeSet<>();
		if (channels != null) {
			for(IPublicationChannel pc: channels){
				publicationChannelEntities.add(((IProxyToEntity<PublicationChannelEntity>) pc).getEntity());
			}
		}


		ScriptEntity scriptEntity = null;
		if (script != null) {
			scriptEntity = ((IProxyToEntity<ScriptEntity>) script).getEntity();
		}

		entity = new PositionEntity(title, localizations,
				state,  vacancies, openingDate,
				closingDate,  sla,  contactPerson, company,
				technicalAreas,  description, publicationChannelEntities, scriptEntity);
	}

	@Override
	public PositionEntity getEntity() {
		return entity;
	}

	@Override
	public Date getOpeningDate() {
		
		System.out.println("position Porxy: getting values");
		
		return entity.getOpeningDate();
	}

	@Override
	public String getCode() {
		
		System.out.println("position Porxy: getting values");
		
		return entity.getCode();
	}

	@Override
	public String getTitle() {
		
		System.out.println("position Porxy: getting values");
		
		return entity.getTitle();
	}

	@Override
	public void setTitle(String title) {
		
		entity.setTitle(title);
	}

	@Override
	public List<Localization> getLocalizations() {
		
		return entity.getLocalizations();
	}

	@Override
	public void setLocalizations(List<Localization> localizations) {
		
		entity.setLocalizations(localizations);
	}

	@Override
	public PositionState getState() {
		
		return entity.getState();
	}

	@Override
	public void setState(PositionState state) {
		
		entity.setState(state);
	}

	@Override
	public int getVacancies() {
		
		return entity.getVacancies();
	}

	@Override
	public void setVacancies(int vacancies) {
		
		entity.setVacancies(vacancies);
	}

	@Override
	public Date getClosingDate() {
		
		return entity.getClosingDate();
	}

	@Override
	public void setClosingDate(Date closingDate) {
		
		entity.setClosingDate(closingDate);
	}

	@Override
	public String getSla() {
		
		return entity.getSla();
	}

	@Override
	public void setSLA(String sla) {
		
		entity.setSla(sla);
	}

	@Override
	public String getCompany() {
		
		return entity.getCompany();
	}

	@Override
	public void setCompany(String company) {
		
		entity.setCompany(company);
	}

	@Override
	public String getContactPerson() {
		
		return entity.getContactPerson();
	}

	@Override
	public void setContactPerson(String contactPerson) {
		
		entity.setContactPerson(contactPerson);
	}

	@Override
	public List<TechnicalArea> getTechnicalAreas() {
		
		return entity.getTechnicalAreas();
	}

	@Override
	public void setTechnicalAreas(List<TechnicalArea> technicalAreas) {
		
		entity.setTechnicalAreas(technicalAreas);
	}

	@Override
	public String getDescription() {
		
		return entity.getDescription();
	}

	@Override
	public void setDescription(String description) {
		
		entity.setDescription(description);	
	}

	@Override
	public List<IPublicationChannel> getIPublicationChannels() {
		
		List<IPublicationChannel> ichannels  = new ArrayList<IPublicationChannel>();
		Set<PublicationChannelEntity> channels = entity.getPublications();
		for(PublicationChannelEntity pce : channels)
			ichannels.add( new PublicationChannelProxy (pce) );
		
		return ichannels;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setIPublicationChannels(
			List<IPublicationChannel> ipublicationChannels) {

		Set<PublicationChannelEntity> channels = new TreeSet<PublicationChannelEntity>(); 
		for(IPublicationChannel ipc : ipublicationChannels)
			channels.add(((IProxyToEntity<PublicationChannelEntity>) ipc).getEntity());
	}




}
