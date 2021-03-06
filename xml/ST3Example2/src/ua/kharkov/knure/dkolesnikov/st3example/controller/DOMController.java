package ua.kharkov.knure.dkolesnikov.st3example.controller;

import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import ua.kharkov.knure.dkolesnikov.st3example.constants.Constants;
import ua.kharkov.knure.dkolesnikov.st3example.constants.XML;
import ua.kharkov.knure.dkolesnikov.st3example.entity.Answer;
import ua.kharkov.knure.dkolesnikov.st3example.entity.Question;
import ua.kharkov.knure.dkolesnikov.st3example.entity.Test;
import ua.kharkov.knure.dkolesnikov.st3example.util.Transformer;

/**
 * Controller for DOM parser.
 * 
 * @author D.Kolesnikov
 * 
 */
public class DOMController {

	private String xmlFileName;

	private Test test; // <-- container

	public DOMController(String xmlFileName) {
		this.xmlFileName = xmlFileName;
	}

	public Test getTest() {
		return test;
	}

	/**
	 * Parses XML document.
	 * 
	 * @param validate
	 *            If true validate XML document against its XML schema.
	 */
	public void parse(boolean validate) throws ParserConfigurationException,
			SAXException, IOException {

		// obtain DOM parser 

		// get document builder factory
		// this way you obtain DOM analyzer based on internal implementation 
		// of the XERCES library bundled with jdk
		//
		// if you place xercesImpl.jar to application classpath the following invocation:
		// 		DocumentBuilderFactory.newInstance()
		// returns factory based on the external XERCES library.
		// (see xercesImpl.jar/META-INF/services/javax.xml.parsers.DocumentBuilderFactory)
		//
		// if there is no xercesImpl.jar in classpath then
		// internal implementation of XERCES will be used automatically
		// i.e. in this case you may use the following code:
		// 		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance(
				Constants.CLASS__DOCUMENT_BUILDER_FACTORY_INTERNAL,
				this.getClass().getClassLoader());

		// set properties for Factory
		dbf.setNamespaceAware(true); // <-- XML document has namespace
		if (validate) { // <-- make parser validating
			dbf.setFeature(Constants.FEATURE__TURN_VALIDATION_ON, true);
			dbf.setFeature(Constants.FEATURE__TURN_SCHEMA_VALIDATION_ON, true);
		}

		DocumentBuilder db = dbf.newDocumentBuilder();

		db.setErrorHandler(new DefaultHandler() {
			@Override
			public void error(SAXParseException e) throws SAXException {
				throw e; // <-- throw exception if XML document is NOT valid
			}
		});

		Document document = db.parse(xmlFileName); // <-- parse XML document
		Element root = document.getDocumentElement(); // <-- get root element

		// create container
		test = new Test();

		NodeList questionNodes = root
				.getElementsByTagName(XML.QUESTION.value());

		for (int j = 0; j < questionNodes.getLength(); j++) {
			Question question = getQuestion(questionNodes.item(j));
			test.getQuestions().add(question); // <-- add question to container
		}
	}

	/**
	 * Extracts question object from the question XML node.
	 * 
	 * @param qNode
	 *            Question node.
	 * @return Question object.
	 */
	private Question getQuestion(Node qNode) {
		Question question = new Question();
		Element qElement = (Element) qNode;

		// process question text
		Node qtNode = qElement.getElementsByTagName(XML.QUESTION_TEXT.value())
				.item(0);
		// set question text
		question.setQuestionText(qtNode.getTextContent());

		// process answers
		NodeList aNodeList = qElement.getElementsByTagName(XML.ANSWER.value());
		for (int j = 0; j < aNodeList.getLength(); j++) {
			Answer answer = getAnswer(aNodeList.item(j));
			question.getAnswers().add(answer); // <-- add answer
		}

		return question;
	}

	/**
	 * Extracts answer object from the answer XML node.
	 * 
	 * @param aNode
	 *            Answer node.
	 * @return Answer object.
	 */
	private Answer getAnswer(Node aNode) {
		Answer answer = new Answer();
		Element aElement = (Element) aNode;

		// process correct
		String correct = aElement.getAttribute(XML.CORRECT.value());
		answer.setCorrect(Boolean.valueOf(correct)); // <-- set correct

		// process content
		String content = aElement.getTextContent();
		answer.setContent(content); // <-- set content

		return answer;
	}

	/**
	 * Saves Test object in XML file (through DOM).
	 * 
	 * @param test
	 *            Test object to be saved.
	 * @param xmlFileName
	 *            Output xml file name.
	 */
	public static void saveXML(Test test, String xmlFileName)
			throws ParserConfigurationException, TransformerException {
	
		// obtain DOM parser
		
		// get document builder factory
		// this way you obtain DOM analyzer based on internal implementaion 
		// of the XERCES library bundled with jdk
		//
		// if you place xercesImpl.jar to application classpath the following invocation:
		// 		DocumentBuilderFactory.newInstance()
		// returns factory based on the external XERCES library.
		// (see xercesImpl.jar/META-INF/services/javax.xml.parsers.DocumentBuilderFactory)
		//
		// if there is no xercesImpl.jar in classpath then
		// internal implementation of XERCES will be used automatically
		// i.e. in this case you may use the following code:
		// 		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance(
				Constants.CLASS__DOCUMENT_BUILDER_FACTORY_INTERNAL,
				DOMController.class.getClassLoader());

		// set properties for Factory
		dbf.setNamespaceAware(true); // <-- XML document has namespace

		DocumentBuilder db = dbf.newDocumentBuilder();
		Document document = db.newDocument();

		// this is the root element
		Element tElement = document.createElementNS(
				Constants.MY_NS__URI, Constants.MY_NS__PREFIX + ":" + XML.TEST.value());

		// set schema location
		tElement.setAttributeNS(
				XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI,
				Constants.SCHEMA_LOCATION__ATTR_FQN,
				Constants.SCHEMA_LOCATION__URI);
		
		document.appendChild(tElement);

		// add questions elements
		for (Question question : test.getQuestions()) {
			// add question
			Element qElement = document.createElement(XML.QUESTION.value());
			tElement.appendChild(qElement);

			// add question text
			Element qtElement = 
					document.createElement(XML.QUESTION_TEXT.value());
			qtElement.setTextContent(question.getQuestionText());
			qElement.appendChild(qtElement);

			// add answers
			for (Answer answer : question.getAnswers()) {
				Element aElement = document.createElement(XML.ANSWER.value());
				aElement.setTextContent(answer.getContent());
				
				// set attribute
				if (answer.isCorrect())
					aElement.setAttribute(XML.CORRECT.value(), "true");
				qElement.appendChild(aElement);
			}
		}

		Transformer.saveToXML(document, xmlFileName); // DOM -> XML		
	}

	public static void main(String[] args) throws ParserConfigurationException,
			SAXException, IOException, TransformerException {

		// try to parse NOT valid XML document with validation on (failed)
		DOMController domContr = new DOMController(Constants.VALID_XML_FILE);
		try {
			domContr.parse(true); // <-- parse with validation (failed)
		} catch (SAXException ex) {
			System.err.println("====================================");
			System.err.println("XML not valid");
			System.err.println("Test object --> " + domContr.getTest());
			System.err.println("====================================");
		}

		// try to parse NOT valid XML document with validation off (success)
		domContr.parse(false); // <-- parse with validation off (success)

		// we have Test object at this point:
		System.out.println("====================================");
		System.out.print("Here is the test: \n" + domContr.getTest());
		System.out.println("====================================");

		// save test in XML file
		Test test = domContr.getTest();
		DOMController.saveXML(test, Constants.VALID_XML_FILE + ".dom.xml");
	}
}
