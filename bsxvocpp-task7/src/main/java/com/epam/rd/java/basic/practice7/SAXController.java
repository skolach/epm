package com.epam.rd.java.basic.practice7;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXController extends DefaultHandler {

    private String xmlFileName;

    public SAXController(String xmlFileName) {
        this.xmlFileName = xmlFileName;
    }

    public void parse(boolean validate) throws ParserConfigurationException, SAXException, IOException {

        SAXParserFactory factory = SAXParserFactory.newInstance(
            "com.sun.org.apache.xerces.internal.jaxp.SAXParserFactoryImpl",
            this.getClass().getClassLoader());

        factory.setNamespaceAware(true);
        if(validate) {
            factory.setFeature("http://xml.org/sax/features/validation", true);
            factory.setFeature("http://apache.org/xml/features/validation/schema", true);
        }

        SAXParser parser = factory.newSAXParser();
        parser.parse(xmlFileName, this);

    }

    @Override
	public void error(org.xml.sax.SAXParseException e) throws SAXException {
		throw e;
	}

    private Flowers flowers;
    private String currentElement;
    private Flower flower;
    private VisualParameters visualParameters;
    private GrowingTips growingTips;
    private AveLenFlower aveLenFlower;
    private Tempreture temperature;
    private Lighting lighting;
    private Watering watering;

    public Flowers getFlowers() {
        return flowers;
    }

    @Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {

        currentElement = localName;

        if (currentElement == "flowers") {
            flowers = new Flowers();
            return;
        }

        if (currentElement == "flower") {
            flower = new Flower();
            flowers.getFlowers().add(flower);
            return;
        }

        if (currentElement == "visualParameters") {
            visualParameters = new VisualParameters();
            flower.setVisualParameters(visualParameters);
            return;
        }

        if (currentElement == "growingTips") {
            growingTips = new GrowingTips();
            flower.setGrowingTips(growingTips);
            return;
        }

        if (currentElement == "aveLenFlower") {
            aveLenFlower = new AveLenFlower();
            if (attributes.getLength() > 0) {
                aveLenFlower.setMeasure(attributes.getValue(uri, "measure"));
            }
            flower.getVisualParameters().setAveLenFlower(aveLenFlower);
        }

        if (currentElement == "tempreture") {
            temperature = new Tempreture();
            if (attributes.getLength() > 0) {
                temperature.setMeasure(attributes.getValue(uri, "measure"));
            }
            flower.getGrowingTips().setTempreture(temperature);
        }

        if (currentElement == "lighting") {
            lighting = new Lighting();
            if(attributes.getLength() > 0) {
                lighting.setLightRequiring(attributes.getValue("lightRequiring"));
            }
            flower.getGrowingTips().setLighting(lighting);
        }

        if (currentElement == "watering") {
            watering = new Watering();
            if (attributes.getLength() > 0) {
                watering.setMeasure(attributes.getValue(uri, "measure"));
            }
            flower.getGrowingTips().setWatering(watering);
        }

    }

    @Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
        
        String elementText = new String(ch, start, length);

        if (elementText.isEmpty()){
            return;
        }

        if (currentElement == "name") {
            flower.setName(elementText);
            return;
        }

        if (currentElement == "soil") {
            flower.setSoil(elementText);
            return;
        }

        if (currentElement == "origin") {
            flower.setOrigin(elementText);
            return;
        }

        if (currentElement == "multiplying") {
            flower.setMultiplying(elementText);
            return;
        }

        if (currentElement == "stemColour") {
            visualParameters.setStemColour(elementText);
            return;
        }

        if (currentElement == "leafColour") {
            visualParameters.setLeafColour(elementText);
            return;
        }

        if (currentElement == "aveLenFlower") {
            aveLenFlower.setValue(Integer.parseInt(elementText));
            return;
        }

        if (currentElement == "tempreture") {
            temperature.setValue(Integer.parseInt(elementText));
            return;
        }

        if (currentElement == "watering") {
            watering.setValue(Integer.parseInt(elementText));
            return;
        }

    }

    @Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {

        if (currentElement == "flower") {
            flowers.getFlowers().add(flower);
            return;
        }

        if (currentElement == "visualParameters") {
            flower.setVisualParameters(visualParameters);
            return;
        }

        if (currentElement == "growingTips") {
            flower.setGrowingTips(growingTips);
            return;
        }

    }

}