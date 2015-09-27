package pt.uc.dei.aor.project.persistence.proxy;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import pt.uc.dei.aor.project.business.model.IPosition;
import pt.uc.dei.aor.project.business.model.IPublicationChannel;
import pt.uc.dei.aor.project.business.model.IScript;
import pt.uc.dei.aor.project.business.model.IUser;
import pt.uc.dei.aor.project.business.util.Localization;
import pt.uc.dei.aor.project.business.util.PositionState;
import pt.uc.dei.aor.project.business.util.TechnicalArea;
import pt.uc.dei.aor.project.persistence.entity.PositionEntity;
import pt.uc.dei.aor.project.persistence.entity.PublicationChannelEntity;
import pt.uc.dei.aor.project.persistence.entity.ScriptEntity;
import pt.uc.dei.aor.project.persistence.entity.UserEntity;
import pt.uc.dei.aor.project.persistence.service.GenericPersistenceService;


public class PositionProxy implements IPosition, IProxyToEntity<PositionEntity> {

	private PositionEntity entity;

	public PositionProxy(PositionEntity entity) {
		this.entity = entity != null ? entity : new PositionEntity();
	}

	public PositionProxy(long code, Date openingDate, String title,
			Collection<Localization> localizations, PositionState state,
			int vacancies, Date closingDate, int sla, IUser contactPerson,
			String company, Collection<TechnicalArea> technicalAreas,
			String description, List<IScript> scripts,
			Collection<IPublicationChannel> channels){

		Set<PublicationChannelEntity> publicationChannelEntities = new TreeSet<>();
		if (channels != null) {
			for(IPublicationChannel pc: channels){
				publicationChannelEntities.add(GenericPersistenceService.getEntity(pc));
			}
		}

		List<ScriptEntity> scriptEntities = new ArrayList<>();
		if (scripts != null) {
			for (IScript s : scripts) {
				System.out.println(s);
				scriptEntities.add(GenericPersistenceService.getEntity(s));
			}
		}
		
		UserEntity manager = GenericPersistenceService.getEntity(contactPerson);

		entity = new PositionEntity(code,title, localizations,
				state,  vacancies, openingDate,
				closingDate,  sla,  manager, company,
				technicalAreas,  description, publicationChannelEntities, scriptEntities);
	}

	@Override
	public PositionEntity getEntity() {
		return entity;
	}

	@Override
	public String getOpeningDateFormatted() {
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/YYYY"); 
		
		return formatter.format(getOpeningDate());
	}
	
	@Override
	public Date getOpeningDate() {
		return entity.getOpeningDate();
	}

	@Override
	public long getCode() {
		return entity.getCode();
	}
	

	@Override
	public String getTitle() {
		return entity.getTitle();
	}

	@Override
	public void setTitle(String title) {
		entity.setTitle(title);
	}

	@Override
	public String getLocalizationsFormatted() {
		StringBuilder build = new StringBuilder("");
		for(Localization local : getLocalizations()){
			if(build.length()>0)
				build.append(", ");
			build.append(local.getLocalizationLabel());
		}
		return build.toString();
	}

	@Override
	public void setLocalizationsFormatted(List<Localization> localizations) {
		entity.setLocalizations(localizations);
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
	public String getClosingDateFormatted() {
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/YYYY"); 
		
		return formatter.format(getClosingDate());
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
	public int getSla() {
		return entity.getSla();
	}

	@Override
	public void setSla(int sla) {
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
	public IUser getContactPerson() {
		return new UserProxy(entity.getContactPerson());
	}

	@Override
	public void setContactPerson(IUser contactPerson) {
		entity.setContactPerson(GenericPersistenceService.getEntity(contactPerson));
	}

	@Override
	public List<TechnicalArea> getTechnicalAreas() {
		Set<TechnicalArea> set = new HashSet<>(entity.getTechnicalAreas()); 
		return new ArrayList<>(set);
	}
	
	@Override
	public String getTechnicalAreasFormatted() {
		StringBuilder build = new StringBuilder("");
		for ( TechnicalArea area : getTechnicalAreas()) {
			if (build.length() > 0)
				build.append(", ");
			build.append(area.getTechnicalAreaLabel());
		}
		
		return build.toString();
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
	public String getPublicationChannels() {
		
		StringBuilder build = new StringBuilder("");
		
		Set<PublicationChannelEntity> channels = entity.getPublications();
		
		for ( PublicationChannelEntity pce : channels) {
			if (build.length() > 0)
				build.append(", ");
			build.append(pce.getChannel());
		}
		
		return build.toString();
	}
	
	@Override
	public List<IPublicationChannel> getIPublicationChannels() {
		
		List<IPublicationChannel> ichannels  = new ArrayList<IPublicationChannel>();
		Set<PublicationChannelEntity> channels = entity.getPublications();
		for(PublicationChannelEntity pce : channels)
			ichannels.add( new PublicationChannelProxy (pce) );
		
		return ichannels ;
	}

	@Override
	public void setIPublicationChannels(
			List<IPublicationChannel> ipublicationChannels) {

		Set<PublicationChannelEntity> channels = new TreeSet<PublicationChannelEntity>(); 
		for(IPublicationChannel ipc : ipublicationChannels)
			channels.add(GenericPersistenceService.getEntity(ipc));
	}

	@Override
	public List<IScript> getScripts() {
		List<ScriptEntity> scripts = entity.getScripts();
		List<IScript> proxies = new ArrayList<>();
		
		if (scripts == null) return null;
		for (ScriptEntity se : scripts) {
			proxies.add(new ScriptProxy(se));
		}
		
		return proxies;
	}

	@Override
	public long getId() {
		return entity.getId();
	}

	@Override
	public String toString() {
		return getCode()+" -> "+getTitle();
	}

	@Override
	public String getStateFormatted() {
		
		return entity.getState().getPositionStateLabel();
	}
}
