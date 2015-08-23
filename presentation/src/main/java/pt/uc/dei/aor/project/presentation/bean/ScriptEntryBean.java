package pt.uc.dei.aor.project.presentation.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.ReorderEvent;

import pt.uc.dei.aor.project.business.exception.IllegalAnswerOptionsException;
import pt.uc.dei.aor.project.business.exception.IllegalQuestionTypeException;
import pt.uc.dei.aor.project.business.exception.IllegalScaleException;
import pt.uc.dei.aor.project.business.model.IScript;
import pt.uc.dei.aor.project.business.model.IScriptEntry;
import pt.uc.dei.aor.project.business.service.IScriptBusinessService;
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
		} catch (IllegalAnswerOptionsException e) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Repeated answer", "Repeated answer");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	public void deleteOption(IScriptEntry entry, String answer) {
		ejb.removeAnswer(entry, answer);
	}
	

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}
	
}
