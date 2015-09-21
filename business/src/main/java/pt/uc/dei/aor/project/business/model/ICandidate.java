package pt.uc.dei.aor.project.business.model;

import java.util.List;

public interface ICandidate {


	public String getFullName();

	public String getLogin();

	public long getId();

	public String getName();
	
	public void setName(String name);

	public String getSurname();
	
	public void setSurname(String surname);
	
	public String getEmail();
	
	public void setEmail(String email);
	
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

	public void addQualification(IQualification qualification);

	public void removeQualification(IQualification qualification);


}
