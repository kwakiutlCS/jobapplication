package pt.uc.dei.aor.project.business.model;

import java.util.Collection;
import java.util.List;

public interface IScript {

	public long getId();

	public List<IScriptEntry> getEntries();

	public void addQuestion(String questionText, String questionType);

	public void deleteQuestion(IScriptEntry entry);

	public void addQuestion(String questionText, String questionType, int min, int max);

	public void addQuestion(String questionText, String questionType, Collection<String> options);

	public void moveQuestion(int fromIndex, int toIndex);

	public String getTitle();

	void setTitle(String title);
}
