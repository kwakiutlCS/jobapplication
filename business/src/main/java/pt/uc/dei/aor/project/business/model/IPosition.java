package pt.uc.dei.aor.project.business.model;

import java.util.Date;
import java.util.List;

import pt.uc.dei.aor.project.business.util.Localization;
import pt.uc.dei.aor.project.business.util.PositionState;
import pt.uc.dei.aor.project.business.util.TechnicalArea;

public interface IPosition  {
	
	public long getId();
	
	public String getOpeningDateFormatted();
	public Date getOpeningDate();
	
	public long getCode();
	
	public String getTitle();
	public void setTitle(String title);
	
	public String getLocalizationsFormatted();
	public List<Localization> getLocalizations();
	public void setLocalizations(List<Localization> localizations);
	
	public PositionState getState();
	public void setState(PositionState state);
	
	public String getVacancies();
	public void setVacancies(int vacancies);
	
	public String getClosingDateFormatted();
	public Date getClosingDate();
	public void setClosingDate(Date closingDate);
	
	public String getSla();
	public void setSLA(int Sla);
	
	public String getCompany();
	public void setCompany(String company);
	
	public String getContactPerson();
	public void setContactPerson(String contactPerson);
	
	public String getTechnicalAreasFormatted();
	public List<TechnicalArea> getTechnicalAreas();
	public void setTechnicalAreas(List<TechnicalArea> technicalAreas);
	
	public String getDescription();
	public void setDescription(String description);
	
	String getPublicationChannels();
	public List<IPublicationChannel> getIPublicationChannels();
	public void setIPublicationChannels(List<IPublicationChannel> ipublicationChannels);

	public IScript getScript();

}
