package ua.kharkov.knure.dkolesnikov.st3example.entity;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "questions" })
@XmlRootElement(name = "Test")
public class Test {

	@XmlElement(name = "Question", required = true)
	protected List<Question> questions;

	public List<Question> getQuestions() {
		if (questions == null)
			questions = new ArrayList<Question>();
		return questions;
	}
	
	// ////////////////////////////////////////////////////////
	// it is not good idea to add your own code to this class
	// because it has been created with the help of JAXB
	// if you need to add some functionality to this class
	// create an OTHER WRAPPER class associated with this class
	// and place all the necessary functionality to it
	
	@Override
	public String toString() {
		if (questions == null || questions.size() == 0)
			return "Test contains no questions";
		StringBuilder result = new StringBuilder();
		for (Question question : questions)
			result.append(question).append('\n');
		return result.toString();
	}
}
