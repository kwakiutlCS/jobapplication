package pt.uc.dei.aor.project.presentation.bean;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import pt.uc.dei.aor.project.business.exception.IllegalAnswerOptionsException;
import pt.uc.dei.aor.project.business.model.IScriptEntry;
import pt.uc.dei.aor.project.business.service.IScriptEntryBusinessService;

@Named
@RequestScoped
public class ScriptEntryBean implements Serializable {
	
	private static final long serialVersionUID = -6537089658441990072L;
	private String option;

	@Inject
	private IScriptEntryBusinessService ejb;
	
	public void updateQuestion(IScriptEntry entry) {
		ejb.save(entry);
	}
	
	public void addAnswer(IScriptEntry entry) {
		try {
			ejb.addAnswer(entry, option);
			option = null;
		} catch (IllegalAnswerOptionsException e) {
			addMessage(FacesMessage.SEVERITY_ERROR, "Repeated message");
		}
	}
	
	public void deleteOption(IScriptEntry entry, String answer) {
		try {
			ejb.removeAnswer(entry, answer);
		} catch (IllegalAnswerOptionsException e) {
			addMessage(FacesMessage.SEVERITY_ERROR, "At least 2 responses needed");
		}
	}
	

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}
	
	
	private void addMessage(Severity type, String text) {
		FacesMessage msg = new FacesMessage(type, text, text);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
}
