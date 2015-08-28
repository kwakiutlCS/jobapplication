package pt.uc.dei.aor.project.business.model;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import pt.uc.dei.aor.project.business.util.Localization;
import pt.uc.dei.aor.project.business.util.PositionState;
import pt.uc.dei.aor.project.business.util.TechnicalArea;

public interface IPosition  {
	
	Date getOpeningDate();
	
	String getCode();
	
	String getTitle();
	void setTitle(String title);
	
	List<Localization> getLocalizations();
	void setLocalizations(List<Localization> localizations);
	
	PositionState getState();
	void setState(PositionState state);
	
	int getVacancies();
	void setVacancies(int vacancies);
	
	Date getClosingDate();
	void setClosingDate(Date closingDate);
	
	String getSla();
	void setSLA(String Sla);
	
	String getCompany();
	void setCompany(String company);
	
	String getContactPerson();
	void setContactPerson(String contactPerson);
	
	Collection<TechnicalArea> getTechnicalAreas();
	void setTechnicalAreas(Collection<TechnicalArea> technicalAreas);
	
	String getDescription();
	void setDescription(String description);
	
	List<IPublicationChannel> getIPublicationChannels();
	void setIPublicationChannels(List<IPublicationChannel> ipublicationChannels);
	
	
	

}
