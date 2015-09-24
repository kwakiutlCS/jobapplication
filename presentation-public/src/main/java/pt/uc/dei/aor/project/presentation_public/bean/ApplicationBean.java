package pt.uc.dei.aor.project.presentation_public.bean;

import java.io.Serializable;
import java.util.Date;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import pt.uc.dei.aor.project.business.model.ICandidate;
import pt.uc.dei.aor.project.business.model.IPosition;
import pt.uc.dei.aor.project.presentation_public.util.MetaUtils;

@ViewScoped
@Named
public class ApplicationBean implements Serializable {


	private static final long serialVersionUID = 1L;

	private String cv;
	private String coverletter;
	private String sourceInfo;
	private Date date;
	private boolean analyzed;
	private boolean refused;
	private ICandidate candidate;
	private IPosition position;


	public ApplicationBean() {
	}

	public int verifyLogin(){

		int logged = 0;

		if(MetaUtils.getUser()!=null){
			logged = 1;	
		}
		return logged;
	}

	
	
	
	public String applyNow(){

		String result="/authorized/apply.xhtml?faces-redirect=true";

		if(MetaUtils.getUser()==null){
			result = "/firstregister.xhtml?faces-redirect=true";	
		}
		return result;
	}

	public void swapDialog(){

		RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.execute("PF('positionDetailDlg').hide()"); 

		if(MetaUtils.getUser()==null){
		System.out.println("Swaping dialogs");	
			requestContext.execute("PF('identifyDlg').show()");
		}
		else 
			requestContext.execute("PF('applyDlg').show()");
	}

	public String getCv() {
		return cv;
	}

	public void setCv(String cv) {
		this.cv = cv;
	}


	public String getCoverletter() {
		return coverletter;
	}


	public void setCoverletter(String coverletter) {
		this.coverletter = coverletter;
	}


	public String getSourceInfo() {
		return sourceInfo;
	}


	public void setSourceInfo(String sourceInfo) {
		this.sourceInfo = sourceInfo;
	}


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	public boolean isAnalyzed() {
		return analyzed;
	}


	public void setAnalyzed(boolean analyzed) {
		this.analyzed = analyzed;
	}


	public boolean isRefused() {
		return refused;
	}


	public void setRefused(boolean refused) {
		this.refused = refused;
	}


	public IPosition getPosition() {
		return position;
	}


	public void setPosition(IPosition position) {
		this.position = position;
	}


	public ICandidate getCandidate() {
		return candidate;
	}


	public void setCandidate(ICandidate candidate) {
		this.candidate = candidate;
	}





}
