package ua.kharkov.knure.dkolesnikov.st3example.controller;

import java.io.IOException;

import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.stream.StreamSource;

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
public class STAXController extends DefaultHandler {
	private String xmlFileName;

	private String currentElement; // <-- current element name holder

	private Test test; // <-- main container
	private Question question;
	private Answer answer;

	public Test getTest() {
		return test;
	}

	public STAXController(String xmlFileName) {
		this.xmlFileName = xmlFileName;
	}

	/**
	 * Parses XML document with StAX API. There is no validation during the
	 * parsing.
	 */
	public void parse() throws ParserConfigurationException, SAXException,
			IOException, XMLStreamException {

		XMLInputFactory factory = XMLInputFactory.newInstance();
		
		factory.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, true);

		XMLEventReader reader = factory.createXMLEventReader(
				new StreamSource(xmlFileName));

		while (reader.hasNext()) {
			XMLEvent event = reader.nextEvent();

			// skip any empty content
			if (event.isCharacters() && event.asCharacters().isWhiteSpace())
				continue;

			// handler for start tags
			if (event.isStartElement()) {
				StartElement startElement = event.asStartElement();
				currentElement = startElement.getName().getLocalPart();

				// WARNING!!! 
				// here and below we use '==' operation to compare two INTERNED STRINGS
				if (currentElement == XML.TEST.value()) {
					test = new Test();
					continue;
				}

				if (currentElement == XML.QUESTION.value()) {
					question = new Question();
					continue;
				}

				if (currentElement == XML.ANSWER.value()) {
					answer = new Answer();
					Attribute attribute = startElement.getAttributeByName(
							new QName(XML.CORRECT.value()));
					if (attribute != null)
						answer.setCorrect(Boolean.parseBoolean(attribute.getValue()));
				}
			}

			// handler for contents
			if (event.isCharacters()) {
				Characters characters = event.asCharacters();

				if (currentElement == XML.QUESTION_TEXT.value()) {
					question.setQuestionText(characters.getData());
					continue;
				}

				if (currentElement == XML.ANSWER.value()) {
					answer.setContent(characters.getData());
					continue;
				}
			}

			// handler for end tags
			if (event.isEndElement()) {
				EndElement endElement = event.asEndElement();
				String localName = endElement.getName().getLocalPart();

				if (localName == XML.QUESTION.value()) {
					test.getQuestions().add(question);
					continue;
				}

				if (localName == XML.ANSWER.value()) {
					question.getAnswers().add(answer);
				}
			}
		}
		reader.close();
	}

	public static void main(String[] args) throws ParserConfigurationException,
			SAXException, IOException, XMLStreamException {

		// try to parse (valid) XML file (success)
		STAXController staxContr = new STAXController(Constants.VALID_XML_FILE);
		staxContr.parse(); // <-- do parse (success)

		Test test = staxContr.getTest(); // <-- obtain container

		// we have Test object at this point:
		System.out.println("====================================");
		System.out.print("Here is the test: \n" + test);
		System.out.println("====================================");
	}
}