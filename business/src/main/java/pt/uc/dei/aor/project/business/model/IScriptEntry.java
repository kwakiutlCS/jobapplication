package pt.uc.dei.aor.project.business.model;

import java.util.Collection;

import pt.uc.dei.aor.project.business.exception.IllegalScaleException;

public interface IScriptEntry {
	
	public String getText();
	
	public void setText(String text);
	
	public String getQuestionType();
	
	public String getMin();
	
	public String getMax();
	
	public void setMin(String min) throws IllegalScaleException;
	
	public void setMax(String max) throws IllegalScaleException;
	
	public Collection<String> getAnswers();
}
