package pt.uc.dei.aor.project.presentation.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
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
import pt.uc.dei.aor.project.business.util.QuestionType;

@Named
@SessionScoped
public class ScriptBean implements Serializable {
	
	private static final long serialVersionUID = -6537089658441990072L;

	@Inject
	private IScriptBusinessService scriptEjb;
	
		
	private IScript editableScript;
	private String questionText;
	private QuestionType questionType;
	private int minOption = 1;
	private int maxOption = 5;
	private String answerOption;
	private SortedSet<String> answers;
	private String scriptTitle;
	private boolean editScriptTitle = false;
	private String newTitle;
	private String option;
	
	
	public IScript getEditableScript() {
		return editableScript;
	}
	
	public List<IScript> getScripts() {
		return scriptEjb.getScripts();
	}
			
	public List<IScriptEntry> getEntries() {
		for (IScriptEntry se : editableScript.getEntries()) {
			if (se.getQuestionType() == QuestionType.MULTIPLE_CHOICE) {
				System.out.println(se.getAnswers());
			}
		}
		return editableScript.getEntries();
	}
	
	public void addQuestion() {
		if (questionType.equals(QuestionType.SCALE)) {
			try {
				editableScript = scriptEjb.addQuestion(editableScript, questionText, questionType, minOption, maxOption);
				questionText = null;
			} catch (IllegalScaleException e) {
				addMessage(FacesMessage.SEVERITY_ERROR, "Illegal scale values");
			} catch (IllegalQuestionTypeException e) {
				addMessage(FacesMessage.SEVERITY_ERROR, "Duplicated question");
			}
		}
		else if (questionType.equals(QuestionType.MULTIPLE_CHOICE)) {
			try {
				editableScript = scriptEjb.addQuestion(editableScript, questionText, questionType, answers);
				questionText = null;
				answers = null;
			} catch (IllegalQuestionTypeException e) {
				addMessage(FacesMessage.SEVERITY_ERROR, "Duplicated question");
			} catch (IllegalAnswerOptionsException e) {
				addMessage(FacesMessage.SEVERITY_ERROR, "Illegal answer options");
			} catch (Exception e) {
				System.out.println("FINALEXCEPTION: "+e.getMessage());
			}
		}
		else {
			try {
				editableScript = scriptEjb.addQuestion(editableScript, questionText, questionType);
				questionText = null;
			} catch (IllegalQuestionTypeException e) {
				addMessage(FacesMessage.SEVERITY_ERROR, "Duplicated question");
			}
		}
	}
	
	public void deleteQuestion(IScriptEntry entry) {
		scriptEjb.delete(editableScript, entry);
	}
	
	public void updateScript() {
		editableScript = scriptEjb.update(editableScript);
	}
	
	public List<QuestionType> getQuestionTypeList() {
		return scriptEjb.getQuestionTypeList();
	}

	public String addScript() {
		editableScript = scriptEjb.createNewScript(scriptTitle);
		scriptTitle = null;
		return "editscript.xhtml?faces-redirect=true";
	}
	
	public String edit(IScript script) {
		editableScript = script;
		return "editscript.xhtml?faces-redirect=true";
	}
	
	public String view(IScript script) {
		editableScript = script;
		return "script.xhtml?faces-redirect=true";
	}
	
	public void delete(IScript script) {
		scriptEjb.deleteScript(script);
	}
	
	public void addAnswer() {
		if (answers == null) answers = new TreeSet<>();
		answers.add(answerOption.trim());
		answerOption = null;
	}
	
	public void deleteAnswer(String answer) {
		answers.remove(answer);
	}
	
	public void onRowReorder(ReorderEvent event) {
		int fromIndex = event.getFromIndex();
		int toIndex = event.getToIndex();
		editableScript = scriptEjb.moveQuestion(editableScript, fromIndex, toIndex);
	}
	
	public List<Integer> getScaleValues(IScriptEntry entry) {
		List<Integer> values = new ArrayList<>();
		for (int i = Integer.parseInt(entry.getMin()); i <= Integer.parseInt(entry.getMax()); i++)
			values.add(i);
		return values;
	}
	
	public void prepareEditTitle() {
		setEditScriptTitle(true);
		setNewTitle(editableScript.getTitle());
	}
	
	public void editTitle() {
		editableScript.setTitle(newTitle);
		scriptEjb.update(editableScript);
		setEditScriptTitle(false);
	}
	
	
	public void addAnswerToEntry(IScriptEntry entry) {
		//try {
			editableScript = scriptEjb.addAnswerToEntry(editableScript, entry, option);
		//} catch (IllegalAnswerOptionsException e) {
		//	addMessage(FacesMessage.SEVERITY_ERROR, "Repeated message");
		//}
	}
	
	public void deleteAnswerFromEntry(IScriptEntry entry, String answer) {
		//try {
			editableScript = scriptEjb.removeAnswerFromEntry(editableScript, entry, answer);
		//} catch (IllegalAnswerOptionsException e) {
		//	addMessage(FacesMessage.SEVERITY_ERROR, "At least 2 responses needed");
		//}
	}
	
	// getters and setters
	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	public QuestionType getQuestionType() {
		return questionType;
	}

	public void setQuestionType(QuestionType questionType) {
		this.questionType = questionType;
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

	public Set<String> getAnswers() {
		return answers;
	}

	public void setAnswers(List<String> answers) {
		this.answers.addAll(answers);
	}

	public String getScriptTitle() {
		return scriptTitle;
	}

	public void setScriptTitle(String scriptTitle) {
		this.scriptTitle = scriptTitle;
	}

	public boolean isEditScriptTitle() {
		return editScriptTitle;
	}

	public void setEditScriptTitle(boolean editScriptTitle) {
		this.editScriptTitle = editScriptTitle;
	}

	public String getNewTitle() {
		return newTitle;
	}

	public void setNewTitle(String newTitle) {
		this.newTitle = newTitle;
	}
	
	private void addMessage(Severity type, String text) {
		FacesMessage msg = new FacesMessage(type, text, text);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}
}
