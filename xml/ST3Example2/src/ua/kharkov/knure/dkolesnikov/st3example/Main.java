package ua.kharkov.knure.dkolesnikov.st3example;

import ua.kharkov.knure.dkolesnikov.st3example.controller.DOMController;
import ua.kharkov.knure.dkolesnikov.st3example.controller.JAXBController;
import ua.kharkov.knure.dkolesnikov.st3example.controller.SAXController;
import ua.kharkov.knure.dkolesnikov.st3example.controller.STAXController;
import ua.kharkov.knure.dkolesnikov.st3example.entity.Test;
import ua.kharkov.knure.dkolesnikov.st3example.util.Sorter;
import ua.kharkov.knure.dkolesnikov.st3example.util.Transformer;

/**
 * Entry point for st3 example.
 * 
 * @author D.Kolesnikov
 * 
 */
public class Main {
	public static void usage() {
		System.out
				.println("Usage: java -jar st3example.jar xmlFileName xsdFileName [xslFileName]");
	}

	public static void main(String[] args) throws Exception {
		if (args.length < 2 || args.length > 3) {
			usage();
			return;
		}

		String xmlFileName = args[0];
		String xsdFileName = args[1];
		String xslFileName = null;
		if (args.length == 3)
			xslFileName = args[2];

		// //////////////////////////////////////////////////////
		// DOM
		// //////////////////////////////////////////////////////

		// get
		DOMController domController = new DOMController(xmlFileName);
		domController.parse(true);
		Test test = domController.getTest();

		// sort
		Sorter.sortQuestionsByQuestionText(test);

		// save
		String outputXmlFile = xmlFileName + ".dom.xml";
		DOMController.saveXML(test, outputXmlFile);
		if (xslFileName != null)
			Transformer.saveToHTML(outputXmlFile, xslFileName, outputXmlFile
					+ ".html");

		// //////////////////////////////////////////////////////
		// SAX
		// //////////////////////////////////////////////////////

		// get
		SAXController saxController = new SAXController(xmlFileName);
		saxController.parse(true);
		test = saxController.getTest();

		// sort
		Sorter.sortQuestionsByAnswersNumber(test);

		// save
		outputXmlFile = xmlFileName + ".sax.xml";

		// other way:
		// JAXBController.saveTest(test, outputXmlFile, xsdFileName);
		DOMController.saveXML(test, outputXmlFile);

		if (xslFileName != null)
			Transformer.saveToHTML(outputXmlFile, xslFileName, outputXmlFile
					+ ".html");

		// //////////////////////////////////////////////////////
		// StAX
		// //////////////////////////////////////////////////////

		// get
		STAXController staxController = new STAXController(xmlFileName);
		staxController.parse();
		test = staxController.getTest();

		// sort
		Sorter.sortAnswersByContent(test);

		// save
		outputXmlFile = xmlFileName + ".stax.xml";

		// other way:
		// DOMController.saveXML(test, outputXmlFile);
		JAXBController.saveTest(test, outputXmlFile, xsdFileName);

		if (xslFileName != null)
			Transformer.saveToHTML(outputXmlFile, xslFileName, outputXmlFile
					+ ".html");
	}

}

/**
 * Demo class to run project WO command line.
 * 
 * @author D.Kolesnikov
 * 
 */
class Demo {
	public static void main(String[] args) throws Exception {
		Main.main(new String[] { "Test.xml", "Test.xsd", "Test.xsl" });
	}
}