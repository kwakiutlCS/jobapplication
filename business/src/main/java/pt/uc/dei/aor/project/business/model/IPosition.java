package pt.uc.dei.aor.project.business.model;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import pt.uc.dei.aor.project.business.util.Localization;
import pt.uc.dei.aor.project.business.util.PositionState;
import pt.uc.dei.aor.project.business.util.TechnicalArea;

public interface IPosition  {
	
	public Date getOpeningDate();
	
	public String getCode();
	
	public String getTitle();
	public void setTitle(String title);
	
	public List<Localization> getLocalizations();
	public void setLocalizations(List<Localization> localizations);
	
	public PositionState getState();
	public void setState(PositionState state);
	
	public int getVacancies();
	public void setVacancies(int vacancies);
	
	public Date getClosingDate();
	public void setClosingDate(Date closingDate);
	
	public String getSla();
	public void setSLA(String Sla);
	
	public String getCompany();
	public void setCompany(String company);
	
	public String getContactPerson();
	public void setContactPerson(String contactPerson);
	
	public Collection<TechnicalArea> getTechnicalAreas();
	public void setTechnicalAreas(Collection<TechnicalArea> technicalAreas);
	
	public String getDescription();
	public void setDescription(String description);
	
	public List<IPublicationChannel> getIPublicationChannels();
	public void setIPublicationChannels(List<IPublicationChannel> ipublicationChannels);
	
	
	

}
