package pt.uc.dei.aor.project.presentation.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import pt.uc.dei.aor.project.business.exception.IllegalAnswerOptionsException;
import pt.uc.dei.aor.project.business.exception.IllegalQuestionTypeException;
import pt.uc.dei.aor.project.business.exception.IllegalScaleException;
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
	private int minOption = 1;
	private int maxOption = 5;
	private String answerOption;
	private List<String> answers;
	private boolean pendingAlteration = false;
	
	
	public List<IScript> getScripts() {
		return scriptEjb.getScripts();
	}
	
	public List<IScriptEntry> getEntries() {
		return editableScript.getEntries();
	}
	
	public void addQuestion() throws IllegalQuestionTypeException, IllegalScaleException, IllegalAnswerOptionsException {
		if (questionType.equals("Escala")) {
			scriptEjb.addQuestion(editableScript, questionText, questionType, minOption, maxOption);
		}
		else if (questionType.equals("Escolha múltipla")) {
			scriptEjb.addQuestion(editableScript, questionText, questionType, answers);
		}
		else 
			scriptEjb.addQuestion(editableScript, questionText, questionType);
		pendingAlteration = true;
	}
	
	public void deleteQuestion(IScriptEntry entry) {
		editableScript.deleteQuestion(entry);
		pendingAlteration = true;
	}
	
	public void updateScript() {
		editableScript = scriptEjb.update(editableScript);
		pendingAlteration=false;
	}
	
	public List<String> getQuestionTypeList() {
		return scriptEjb.getQuestionTypeList();
	}

	public String addScript() {
		editableScript = scriptEjb.createNewScript();
		pendingAlteration = false;
		return "editscript.xhtml?faces-redirect=true";
	}
	
	public String edit(IScript script) {
		editableScript = script;
		pendingAlteration = false;
		return "editscript.xhtml?faces-redirect=true";
	}
	
	public void addAnswer() {
		System.out.println(answerOption);
		if (answers == null) answers = new ArrayList<>();
		answers.add(answerOption);
	}
	
	// getters and setters
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

	public boolean isPendingAlteration() {
		return pendingAlteration;
	}

	public void setPendingAlteration(boolean pendingAlteration) {
		this.pendingAlteration = pendingAlteration;
	}

	public int getMinOption() {
		return minOption;
	}

	public void setMinOption(int minOption) {
		this.minOption = minOption;
	}

	public int getMaxOption() {
		return maxOption;
	}

	public void setMaxOption(int maxOption) {
		this.maxOption = maxOption;
	}

	public String getAnswerOption() {
		return answerOption;
	}

	public void setAnswerOption(String answerOption) {
		this.answerOption = answerOption;
	}

	public List<String> getAnswers() {
		return answers;
	}

	public void setAnswers(List<String> answers) {
		this.answers = answers;
	}
}
