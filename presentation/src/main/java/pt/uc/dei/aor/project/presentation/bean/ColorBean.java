package pt.uc.dei.aor.project.presentation.bean;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import pt.uc.dei.aor.project.business.model.IColor;
import pt.uc.dei.aor.project.business.service.IColorBusinessService;

@Named
@ApplicationScoped
public class ColorBean implements Serializable {
	
	private static final long serialVersionUID = -1535787061220132256L;
	
    private IColor admin;
    private IColor manager;
    private IColor interview;
    
    @Inject
    private IColorBusinessService colorService;
    
   
    
    public void saveAdmin() {
    	admin = colorService.save(admin);
    }

    public void saveManager() {
    	manager = colorService.save(manager);
    }

    public void saveInterview() {
    	interview = colorService.save(interview);
    }


    
	public IColor getAdmin() {
		if (admin == null) setAdmin(colorService.findColorByTitle("admin"));
		return admin;
	}

	public void setAdmin(IColor admin) {
		this.admin = admin;
	}

	public IColor getInterview() {
		if (interview == null) setInterview(colorService.findColorByTitle("interview"));
		return interview;
	}

	public void setInterview(IColor interview) {
		this.interview = interview;
	}

	public IColor getManager() {
		if (manager == null) setManager(colorService.findColorByTitle("manager"));
    	return manager;
	}

	public void setManager(IColor manager) {
		this.manager = manager;
	}
   
}
