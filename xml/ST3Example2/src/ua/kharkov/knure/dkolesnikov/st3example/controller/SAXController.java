package ua.kharkov.knure.dkolesnikov.st3example.controller;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import ua.kharkov.knure.dkolesnikov.st3example.constants.Constants;
import ua.kharkov.knure.dkolesnikov.st3example.constants.XML;
import ua.kharkov.knure.dkolesnikov.st3example.entity.Answer;
import ua.kharkov.knure.dkolesnikov.st3example.entity.Question;
import ua.kharkov.knure.dkolesnikov.st3example.entity.Test;

/**
 * Controller for SAX parser.
 * 
 * @author D.Kolesnikov
 * 
 */
public class SAXController extends DefaultHandler {
	private String xmlFileName;

	public SAXController(String xmlFileName) {
		this.xmlFileName = xmlFileName;
	}

	/**
	 * Parses XML document.
	 * 
	 * @param validate
	 *            If true validate XML document against its XML schema. With
	 *            this parameter it is possible make parser validating.
	 */
	public void parse(boolean validate) throws ParserConfigurationException,
			SAXException, IOException {
		
		// get sax parser factory
		// this way you obtain SAX parser factory based on internal implementation 
		// of the XERCES library bundled with jdk
		//
		// if you place xercesImpl.jar to application classpath the following invocation:
		// 		SAXParserFactory.newInstance()
		// returns factory based on the external XERCES library
		// (see xercesImpl.jar/META-INF/services/javax.xml.parsers.SAXParserFactory)
		//
		// If there is no xercesImpl.jar in classpath then
		// internal implementation of XERCES will be used automatically
		// i.e. in this case you may use the following code:
		// 		SAXParserFactory factory = SAXParserFactory.newInstance();
		
		SAXParserFactory factory = SAXParserFactory.newInstance(
				Constants.CLASS__SAX_PARSER_FACTORY_INTERNAL,
				this.getClass().getClassLoader());
		
		factory.setNamespaceAware(true);
		if (validate) {
			factory.setFeature(Constants.FEATURE__TURN_VALIDATION_ON, true);
			factory.setFeature(Constants.FEATURE__TURN_SCHEMA_VALIDATION_ON, true);
		}

		SAXParser parser = factory.newSAXParser();
		parser.parse(xmlFileName, this);
	}

	// ///////////////////////////////////////////////////////////
	// ERROR HANDLER IMPLEMENTATION
	// ///////////////////////////////////////////////////////////

	@Override
	public void error(org.xml.sax.SAXParseException e) throws SAXException {
		throw e; // <-- if XML document not valid just throw exception
	};

	// ///////////////////////////////////////////////////////////
	// CONTENT HANDLER IMPLEMENTATION
	// ///////////////////////////////////////////////////////////

	private String currentElement; // <-- current element name holder

	private Test test; // <-- main container
	private Question question;
	private Answer answer;

	public Test getTest() {
		return test;
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {

		currentElement = localName;

		// WARNING!!! 
		// here and below we use '==' operation to compare two INTERNED STRINGS
		if (currentElement == XML.TEST.value()) {
			test = new Test();
			return;
		}

		if (currentElement == XML.QUESTION.value()) {
			question = new Question();
			return;
		}

		if (currentElement == XML.ANSWER.value()) {
			answer = new Answer();
			if (attributes.getLength() > 0) {
				answer.setCorrect(Boolean.parseBoolean(attributes.getValue(uri,
						XML.CORRECT.value())));
			}
		}
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {

		String elementText = new String(ch, start, length).trim();

		if (elementText.isEmpty()) // <-- return if content is empty
			return;

		if (currentElement == XML.QUESTION_TEXT.value()) {
			question.setQuestionText(elementText);
			return;
		}

		if (currentElement == XML.ANSWER.value()) {
			answer.setContent(elementText);
			return;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {

		if (localName == XML.QUESTION.value()) {
			// just add question to container
			test.getQuestions().add(question);
			return;
		}

		if (localName == XML.ANSWER.value()) {
			// just add answer to container
			question.getAnswers().add(answer);
			return;
		}
	}

	public static void main(String[] args) throws ParserConfigurationException,
			SAXException, IOException {

		// try to parse valid XML file (success)
		SAXController saxContr = new SAXController(Constants.VALID_XML_FILE);
		saxContr.parse(true); // <-- do parse with validation on (success)
		Test test = saxContr.getTest(); // <-- obtain container

		// we have Test object at this point:
		System.out.println("====================================");
		System.out.print("Here is the test: \n" + test);
		System.out.println("====================================");

		// now try to parse NOT valid XML (failed)
		saxContr = new SAXController(Constants.INVALID_XML_FILE);
		try {
			saxContr.parse(true); // <-- do parse with validation on (failed)
		} catch (Exception ex) {
			System.err.println("====================================");
			System.err.println("Validation is failed:\n" + ex.getMessage());
			System.err
					.println("Try to print test object:" + saxContr.getTest());
			System.err.println("====================================");
		}

		// and now try to parse NOT valid XML with validation off (success)
		saxContr.parse(false); // <-- do parse with validation off (success)

		// we have Test object at this point:
		System.out.println("====================================");
		System.out.print("Here is the test: \n" + saxContr.getTest());
		System.out.println("====================================");
	}
}