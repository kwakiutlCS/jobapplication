package pt.uc.dei.aor.project.business.util;

public enum QuestionType {
	SHORT_ANSWER("Short answer"),
	LONG_ANSWER("Long answer"),
	TRUE_FALSE("True or False"),
	MULTIPLE_CHOICE("Multiple choice"),
	SCALE("Scale value"),
	DATE("Date"),
	TIME("Time");
	
	
	private String label;
	
	private QuestionType(String name) {
		this.setLabel(name);
	}
	
	public String toString() {
		return label;
	}
	
	public static QuestionType toEnum(String name) {
		for (QuestionType qt : QuestionType.values()) {
			if (qt.label.equals(name)) return qt;
		}
		return null;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}
