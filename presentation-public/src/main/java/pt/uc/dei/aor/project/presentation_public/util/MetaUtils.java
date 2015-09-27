package pt.uc.dei.aor.project.presentation_public.util;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import pt.uc.dei.aor.project.business.model.ICandidate;
import pt.uc.dei.aor.project.business.model.IWorker;

public class MetaUtils {
	
	public static ICandidate getUser() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		return (ICandidate) request.getSession().getAttribute("user");	
	}
	
	public static void setMsg(String text, Severity severity) {
		FacesMessage msg = new FacesMessage(severity,
				text, text);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	public static void setMsg(String text, Severity severity, String component) {
		FacesMessage msg = new FacesMessage(severity,
				text, text);
		FacesContext.getCurrentInstance().addMessage(component, msg);
	}
	
	public static HttpSession getSession() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		return request.getSession();
	}
}
