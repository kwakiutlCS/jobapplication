package pt.uc.dei.aor.project.presentation.bean;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;

import pt.uc.dei.aor.project.business.service.IImageBusinessService;
import pt.uc.dei.aor.project.presentation.util.MetaUtils;

@Named
@ViewScoped
public class ConfigurationBean implements Serializable {
	
	private static final long serialVersionUID = -1535787061220132256L;
	
	@Inject
	private IImageBusinessService imageService;
	
    private String page = "admin";
    private Part img;
    
	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}
	
	
	public void upload(AjaxBehaviorEvent event) {
		System.out.println(img.getContentType());
		if (!img.getContentType().equals("application/png")) {
			MetaUtils.setMsg("Please upload an image file", FacesMessage.SEVERITY_ERROR);
			return; 
		}
		
		try {
			imageService.upload(img);
		} catch (IOException e) {
			MetaUtils.setMsg("Error uploading file", FacesMessage.SEVERITY_ERROR);
		}
		
		img = null;
	}
   
}
