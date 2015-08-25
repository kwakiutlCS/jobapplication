package pt.uc.dei.aor.project.business.model;

import java.util.Collection;
import java.util.List;

import pt.uc.dei.aor.project.business.util.QuestionType;

public interface IScript {

	public long getId();

	public List<IScriptEntry> getEntries();

	public void addQuestion(String questionText, QuestionType questionType, Collection<IAnswerChoice> answerChoices);

	public void deleteQuestion(IScriptEntry entry);

	public void addQuestion(String questionText, QuestionType questionType, int min, int max);

	public void addQuestion(String questionText, QuestionType questionType);

	public void moveQuestion(int fromIndex, int toIndex);

	public String getTitle();

	void setTitle(String title);

	public void addAnswerToEntry(IScriptEntry entry, IAnswerChoice answerChoice);

	public void removeAnswerFromEntry(IScriptEntry entry, IAnswerChoice answerChoice);
}
