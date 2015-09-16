package pt.uc.dei.aor.project.business.model;

import java.util.List;

public interface ICandidate {

	
	public String getAddress();
	
	public void setAddress(String adress);
	
	public String getCity();
	
	public void setCity(String city);
	
	public String getCountry();
	
	public void setCountry(String country);
	
	public String getPhone();
	
	public void setPhone(String phone);
	
	public String getMobilePhone();
	
	public void setMobilePhone(String mobilephone);
	
	public List<IQualification> getQualifications();
	
	public void setQualifications(List<IQualification> qualifications);
	
	public String getCv();
	
	public void setCv(String cv);
	
	public List<IApplication> getApplications();
	
	public void setApplications(List<IApplication> applications);
	
	
}
