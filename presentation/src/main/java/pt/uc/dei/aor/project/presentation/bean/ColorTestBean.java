package pt.uc.dei.aor.project.presentation.bean;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import pt.uc.dei.aor.project.business.model.IColor;
import pt.uc.dei.aor.project.business.service.IColorBusinessService;

@Named
@SessionScoped
public class ColorTestBean implements Serializable {
	
	private static final long serialVersionUID = -1535787061220132256L;
	
	@Inject
	private IColorBusinessService colorService;
	   
	@Inject
	private ColorBean colorBean;
	
	 
    private IColor color;
    private int page = 0;
    private boolean testing = false;
	
    
    
    public IColor getColor(int page) {
    	if (testing) return color;
    	else if (page == 1) {
    		return colorBean.getAdmin();
    	}
    	else if (page == 2) {
    		return colorBean.getManager();
    	}
    	else {
    		return colorBean.getInterview();
    	}
    }
    
    public IColor getColor() {
    	if (color == null) color = colorService.color();
    	return color;
    }
    
    public void save(int page) {
    	this.page = page;
    	testing = true;
    }
    
    
	public void setColor(IColor color) {
		this.color = color;
		System.out.println(color.getHeader());
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public boolean isTesting() {
		return testing;
	}

	public void setTesting(boolean testing) {
		this.testing = testing;
	}
    
    
}
