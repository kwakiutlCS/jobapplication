package pt.uc.dei.aor.project.presentation.bean;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import pt.uc.dei.aor.project.business.model.IScript;
import pt.uc.dei.aor.project.business.model.IScriptEntry;
import pt.uc.dei.aor.project.business.service.IScriptBusinessService;

@Named
@SessionScoped
public class ScriptBean implements Serializable {
	
	private static final long serialVersionUID = -6537089658441990072L;

	@Inject
	private IScriptBusinessService scriptEjb;
	
	private IScript editableScript;
	private String questionText;
	private String questionType;
	
	
	public List<IScript> getScripts() {
		return scriptEjb.getScripts();
	}
	
	public List<IScriptEntry> getEntries() {
		return editableScript.getEntries();
	}
	
	public void addQuestion() {
		editableScript = scriptEjb.addQuestion(editableScript, questionText, questionType);
	}
	
	public List<String> getQuestionTypeList() {
		return scriptEjb.getQuestionTypeList();
	}

	public String addScript() {
		editableScript = scriptEjb.createNewScript();
		return "editscript.xhtml?faces-redirect=true";
	}
	
	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}
}
