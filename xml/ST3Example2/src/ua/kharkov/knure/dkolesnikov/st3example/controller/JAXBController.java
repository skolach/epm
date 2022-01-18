package ua.kharkov.knure.dkolesnikov.st3example.controller;

import java.io.File;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

import ua.kharkov.knure.dkolesnikov.st3example.constants.Constants;
import ua.kharkov.knure.dkolesnikov.st3example.entity.Test;

/**
 * Controller for JAXB.
 * 
 * @author D.Kolesnikov
 * 
 */
public class JAXBController {

	/**
	 * XML --> Java (with validation against XSD). Throws no exception if XML is
	 * well-formed, but NOT valid (just prints validation warning message).
	 * 
	 * @param xmlFileName
	 *            Input XML file name (not null, required).
	 * @param xsdFileName
	 *            External XSD for validation. If equals to "", validation will
	 *            be against XSD indicated in XML document. If equals to null,
	 *            there is no validation during Test object loading.
	 * @return Test object, container with info from the input XML document.
	 */
	public static Test loadTest(final String xmlFileName,
			final String xsdFileName) throws JAXBException, SAXException {
		JAXBContext jc = JAXBContext.newInstance(Constants.JAXB__PACKAGE);
		Unmarshaller unmarshaller = jc.createUnmarshaller();

		// obtain schema
		SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		
		if (xsdFileName != null) { // <-- setup validation on
			Schema schema = null;			
			if ("".equals(xsdFileName)) {
				// setup validation against XSD pointed in XML
				schema = sf.newSchema();
			} else {
				// setup validation against external XSD
				schema = sf.newSchema(new File(xsdFileName));
			}
			
			unmarshaller.setSchema(schema); // <-- set XML schema for validation

			// set up handler
			unmarshaller.setEventHandler(new ValidationEventHandler() {
				// this method will be invoked if XML is NOT valid
				@Override
				public boolean handleEvent(ValidationEvent event) {
					// at this point we have NOT valid XML document
					// just print message
					System.err.println("====================================");
					System.err.println(xmlFileName + " is NOT valid against "
							+ xsdFileName + ":\n" + event.getMessage());
					System.err.println("====================================");

					// if we place 'return false;' unmarshal method throws
					// exception
					// 'return true;' does not imply any exceptions
					return true;
				}
			});
		}

		// do unmarshal
		Test test = (Test) unmarshaller.unmarshal(new File(xmlFileName));
		return test; // <-- filled container
	}

	/**
	 * Java --> XML (with validation against XSD). Throws exception if XML is
	 * NOT valid.
	 * 
	 * @param xmlFileName
	 *            Output XML file name (not null, required).
	 * @param xsdFileName
	 *            XSD for validation. If equals to null, there is NO
	 *            validation.
	 * @throws JAXBException
	 *             If JAXB object is not valid against XSD document.
	 */
	public static void saveTest(Test test, final String xmlFileName,
			final String xsdFileName) throws JAXBException, SAXException {
		JAXBContext jc = JAXBContext.newInstance(Constants.JAXB__PACKAGE);
		Marshaller marshaller = jc.createMarshaller();

		// obtain schema
		SchemaFactory sf = SchemaFactory
				.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

		// setup validation against XSD
		if (xsdFileName != null) {
			Schema schema = sf.newSchema(new File(xsdFileName));

			marshaller.setSchema(schema);
			marshaller.setEventHandler(new ValidationEventHandler() {
				@Override
				public boolean handleEvent(ValidationEvent event) {
					// at this point we have NOT valid XML document
					// just print message
					System.err.println("====================================");
					System.err.println(xmlFileName + " is NOT valid against "
							+ xsdFileName + ":\n" + event.getMessage());
					System.err.println("====================================");

					// if we place 'return false;' marshal method throws
					// exception
					// 'return true;' does not imply any exceptions
					return false;
				}
			});
		}
		
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, Constants.SCHEMA_LOCATION__URI);
		marshaller.marshal(test, new File(xmlFileName));
	}
	
	public static void main(String[] args) throws JAXBException, SAXException {
		// load Test object from NOT valid XML (success, just prints validation
		// warning)
		Test test = loadTest(Constants.INVALID_XML_FILE, Constants.XSD_FILE);

		// we have Test object at this point
		System.out.println("====================================");
		System.out.println("Here is the test: \n" + test);
		System.out.println("====================================");

		// try to save Test object to XML file (failed, exception)
		try {
			saveTest(test, Constants.INVALID_XML_FILE + ".jaxb.xml", Constants.XSD_FILE);
		} catch (Exception ex) {
			System.err.println("====================================");
			System.err.println("Object tree not valid against XSD.");
			System.err.println(ex.getClass().getName());
			System.err.println("====================================");
		}

		// save Test object to XML without validation (success)
		saveTest(test, Constants.INVALID_XML_FILE + ".jaxb.xml", null);

		// now make Test object valid against XSD schema
		test.getQuestions().get(0).getAnswers().remove(4);

		// save Test object to XML (success)
		saveTest(test, Constants.VALID_XML_FILE + ".jaxb.xml", "Test.xsd");
	}
}