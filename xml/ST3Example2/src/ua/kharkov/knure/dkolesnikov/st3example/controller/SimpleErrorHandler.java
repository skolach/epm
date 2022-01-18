package ua.kharkov.knure.dkolesnikov.st3example.controller;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class SimpleErrorHandler implements ErrorHandler {
	public void warning(SAXParseException e) throws SAXException {
		System.err.println("====================================");
		System.out.println(e.getMessage());
		System.err.println("====================================");
	}

	public void error(SAXParseException e) throws SAXException {
		System.err.println("====================================");
		System.out.println(e.getMessage());
		System.err.println("====================================");
	}

	public void fatalError(SAXParseException e) throws SAXException {
		System.err.println("====================================");
		System.out.println(e.getMessage());
		System.err.println("====================================");
	}
}