package pt.uc.dei.aor.project.business.model;

import java.util.Collection;

public interface IScriptEntry {
	
	public String getText();
	
	public String getQuestionType();
	
	public String getMin();
	
	public String getMax();
	
	public Collection<String> getAnswers();
}
