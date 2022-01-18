package ua.kharkov.knure.dkolesnikov.st3example.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import ua.kharkov.knure.dkolesnikov.st3example.constants.Constants;
import ua.kharkov.knure.dkolesnikov.st3example.constants.XML;
import ua.kharkov.knure.dkolesnikov.st3example.controller.SimpleErrorHandler;
import ua.kharkov.knure.dkolesnikov.st3example.entity.Answer;
import ua.kharkov.knure.dkolesnikov.st3example.entity.Question;
import ua.kharkov.knure.dkolesnikov.st3example.entity.Test;

/**
 * Controller for JDOM parser. WARNING!!! To use functionality from this class
 * place jdom.jar and xercesImpl.jar to classpath of this project.
 * 
 * @author D.Kolesnikov
 * 
 */
public class JAXPDOMController {

	private String xmlFileName;

	private Test test; // <-- container

	public JAXPDOMController(String xmlFileName) {
		this.xmlFileName = xmlFileName;
	}

	public Test getTest() {
		return test;
	}

	/**
	 * Parses XML document with JDOM (based on XERCES SAX parser).
	 * 
	 * @param validate
	 *            If true validate XML document against its XML schema.
	 * @throws JDOMException
	 */
	public void parse(boolean validate) throws ParserConfigurationException, SAXException, IOException {

		// get builder factory (and set validation if it is necessary)
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		
		if (validate) {
			// here we set underlying parser validating
			// create schema for against validation
			SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = schemaFactory.newSchema(new StreamSource(Constants.XSD_FILE));

			// against validation
			Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File(xmlFileName)));
		}

		// Create builder
		DocumentBuilder builder = dbf.newDocumentBuilder();
		builder.setErrorHandler(new SimpleErrorHandler());
		
		// parse
		Document document = builder.parse(xmlFileName);

		// process with JDOM
		Element root = document.getDocumentElement();
		test = new Test();

		NodeList qNodes = root.getElementsByTagName(XML.QUESTION.value());
		for (int i = 0; i < qNodes.getLength(); i++) {
			// Create Question object for data filling
			Question question = new Question();

			Element qNode = (Element) qNodes.item(i);
			NodeList qtNodes = qNode.getElementsByTagName(XML.QUESTION_TEXT.value());
			Element qtNode = (Element) qtNodes.item(0);
			String qtContent = qtNode.getTextContent();
			question.setQuestionText(qtContent);

			NodeList qAnswers = qNode.getElementsByTagName(XML.ANSWER.value());
			for (int j = 0; j < qAnswers.getLength(); j++) {
				Answer answer = new Answer();
				Element qAnswer = (Element) qAnswers.item(j);
				answer.setContent(qAnswer.getTextContent());
				String correct = qAnswer.getAttribute(XML.CORRECT.value());
				answer.setCorrect(new Boolean(correct));
				question.getAnswers().add(answer);
			}
			// at this point we have filled Question object
			test.getQuestions().add(question);
		}
		// at this point we have filled Test object
	}

	/**
	 * Saves Test object in XML file.
	 * 
	 * @param test
	 *            Test object to be saved.
	 * @param xmlFileName
	 *            Output xml file name.
	 * @throws SAXException
	 * @throws TransformerException
	 */
	public static void saveXML(Test test, String xmlFileName) throws ParserConfigurationException,
			FileNotFoundException, IOException, SAXException, TransformerException {
		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		domFactory.setNamespaceAware(true);
		Schema schema = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(new File(Constants.XSD_FILE));
		domFactory.setSchema(schema);
		DocumentBuilder domBuilder = domFactory.newDocumentBuilder();

		Document doc = domBuilder.newDocument();
		Element root = doc.createElementNS (Constants.MY_NS__URI, Constants.MY_NS__PREFIX + ":" + XML.TEST.value());

		// set schema location
		root.setAttributeNS(
				XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI,
				Constants.SCHEMA_LOCATION__ATTR_FQN,
				Constants.SCHEMA_LOCATION__URI);
		
		doc.appendChild(root);
		for (Question question : test.getQuestions()) {
			Element qElement = doc.createElement(XML.QUESTION.value());

			Element qtElement = doc.createElement(XML.QUESTION_TEXT.value());
			qtElement.setTextContent(question.getQuestionText());
			qElement.appendChild(qtElement);

			for (Answer answer : question.getAnswers()) {
				Element aElement = doc.createElement(XML.ANSWER.value());

				aElement.setTextContent(answer.getContent());
				if (answer.isCorrect())
					aElement.setAttribute(XML.CORRECT.value(), String.valueOf(answer.isCorrect()));

				qElement.appendChild(aElement);
			}
			root.appendChild(qElement);
		}

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		DOMSource source = new DOMSource(doc);

		// write data
		transformer.transform(source, new StreamResult(new File(xmlFileName)));
	}

	public static void main(String[] args)
			throws ParserConfigurationException, SAXException, IOException, TransformerException {

		// try to parse NOT valid XML document (failed)
		JAXPDOMController jdomContr = new JAXPDOMController(Constants.INVALID_XML_FILE);
		try {
			jdomContr.parse(true); // <-- parse with validation (failed)
		} catch (Exception ex) {
			System.err.println("====================================");
			System.err.println("XML not valid\n" + ex.getMessage());
			System.err.println("Test object --> " + jdomContr.getTest());
			System.err.println("====================================");
		}

		// try to parse NOT valid XML document with validation off (success)
		jdomContr.parse(false); // <-- parse with validation off (success)

		// we have Test object at this point:
		System.out.println("====================================");
		System.out.print("Here is the test: \n" + jdomContr.getTest());
		System.out.println("====================================");

		// now try to parse the valid XML document with validation on (success)
		jdomContr = new JAXPDOMController(Constants.VALID_XML_FILE);
		jdomContr.parse(true); // <-- parse with validation on (success)

		// we have Test object at this point:
		System.out.println("====================================");
		System.out.print("Here is the test: \n" + jdomContr.getTest());
		System.out.println("====================================");

		// save test in XML file
		Test test = jdomContr.getTest();
		JAXPDOMController.saveXML(test, Constants.VALID_XML_FILE + ".jaxpdom.xml");
	}
}