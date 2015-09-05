package pt.uc.dei.aor.project.business.model;

public interface IAnswer {

	String getQuestion();

	IInterview getInterview();

	void setAnswer(String answer);

}
