package pt.uc.dei.aor.project.business.util;

public enum QuestionType {
	SHORT_ANSWER("Resposta curta"),
	LONG_ANSWER("Resposta longa"),
	TRUE_FALSE("Verdadeiro ou falso"),
	MULTIPLE_CHOICE("Escolha múltipla"),
	SCALE("Escala"),
	DATE("Data"),
	TIME("Hora");
	
	
	private String name;
	
	private QuestionType(String name) {
		this.name = name;
	}
	
	public String toString() {
		return name;
	}
	
	public static QuestionType toEnum(String name) {
		for (QuestionType qt : QuestionType.values()) {
			if (qt.name.equals(name)) return qt;
		}
		return null;
	}
}
