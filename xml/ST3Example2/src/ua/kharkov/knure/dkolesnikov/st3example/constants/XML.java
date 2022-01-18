package ua.kharkov.knure.dkolesnikov.st3example.constants;

/**
 * Holds elements and attributes names from the XML schema.
 * @author D.Kolesnikov
 *
 */
public enum XML {
	// these are tags
	TEST("Test"), QUESTION("Question"), QUESTION_TEXT("QuestionText"), ANSWER("Answer"),

	// these are attributes
	CORRECT("correct");

	private String value;

	public String value() {
		return value;
	}

	XML(String value) {
		this.value = value.intern();
	}
}
