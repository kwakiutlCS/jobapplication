package pt.uc.dei.aor.project.business.model;

import java.util.List;

public interface IScript {

	public long getId();

	public List<IScriptEntry> getEntries();

	public List<String> getQuestionTypeList();

	public void addQuestion(String questionText, String questionType);
}
