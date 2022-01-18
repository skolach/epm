package com.epam.rd.java.basic.practice7;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.stream.StreamSource;

public class STAXController {
    private String xmlFileName;
    private Flowers flowers;
    private String currentElement;
    private Flower flower;
    private VisualParameters visualParameters;
    private GrowingTips growingTips;
    private AveLenFlower aveLenFlower;
    private Tempreture temperature;
    private Lighting lighting;
    private Watering watering;

    public STAXController(String xmlFileName) {
        this.xmlFileName = xmlFileName;
    }

    public void parse() throws XMLStreamException {

        XMLInputFactory factory = XMLInputFactory.newInstance();
        factory.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, true);

        XMLEventReader reader = 
            factory.createXMLEventReader(new StreamSource(xmlFileName));

        while(reader.hasNext()){
            XMLEvent event = reader.nextEvent();

            if (event.isCharacters() && event.asCharacters().isWhiteSpace()){
                continue;
            }

            if (event.isStartElement()){
                StartElement startElement = event.asStartElement();
                currentElement = startElement.getName().getLocalPart();

                if (currentElement.equals("flowers")) {
                    flowers = new Flowers();
                    continue;
                }

                if (currentElement.equals("flower")) {
                    flower = new Flower();
                    continue;
                }

                if (currentElement.equals("visualParameters")) {
                    visualParameters = new VisualParameters();
                    continue;
                }

                if (currentElement.equals("growingTips")) {
                    growingTips = new GrowingTips();
                    continue;
                }

                if (currentElement.equals("aveLenFlower")){
                    aveLenFlower = new AveLenFlower();
                    Attribute attribute = startElement.getAttributeByName(
                        new QName("measure"));
                    if (attribute != null) {
                        aveLenFlower.setMeasure(attribute.getValue());
                    }
                    continue;
                }

                if(currentElement.equals("tempreture")) {
                    temperature = new Tempreture();
                    Attribute attribute = startElement.getAttributeByName(
                        new QName("measure"));
                    if (attribute != null) {
                        temperature.setMeasure(attribute.getValue());
                    }
                    continue;
                }

                if (currentElement.equals("lighting")) {
                    lighting = new Lighting();
                    Attribute attribute = startElement.getAttributeByName(
                        new QName("lightRequiring"));
                    if (attribute != null){
                        lighting.setLightRequiring(attribute.getValue());
                    }
                    continue;
                }

                if (currentElement.equals("watering")) {
                    watering = new Watering();
                    Attribute attribute = startElement.getAttributeByName(
                        new QName("measure"));
                    if (attribute != null) {
                        watering.setMeasure(attribute.getValue());
                    }
                    continue;
                }
            }

            if (event.isCharacters()){
                Characters characters = event.asCharacters();
                String currentText = characters.getData();

                switch (currentElement) {
                    case "name":
                        flower.setName(currentText);
                        break;
                    case "soil":
                        flower.setSoil(currentText);
                        break;
                    case "origin":
                        flower.setOrigin(currentText);
                        break;
                    case "stemColour":
                        visualParameters.setStemColour(currentText);
                        break;
                    case "leafColour":
                        visualParameters.setLeafColour(currentText);
                        break;
                    case "aveLenFlower":
                        aveLenFlower.setValue(Integer.parseInt(currentText));
                        break;
                    case "tempreture":
                        temperature.setValue(Integer.parseInt(currentText));
                        break;
                    case "watering":
                        watering.setValue(Integer.parseInt(currentText));
                        break;
                    case "multiplying":
                        flower.setMultiplying(currentText);
                        break;
                    default:
                        break;
                }
                continue;
            }

            if (event.isEndElement()){
                EndElement endElement = event.asEndElement();
				String localName = endElement.getName().getLocalPart();

                if (localName.equals("flower")) {
                    flowers.getFlowers().add(flower);
                    continue;
                }

                if (localName.equals("visualParameters")) {
                    flower.setVisualParameters(visualParameters);
                    continue;
                }

                if (localName.equals("growingTips")) {
                    flower.setGrowingTips(growingTips);
                    continue;
                }

                if (localName.equals("aveLenFlower")){
                    visualParameters.setAveLenFlower(aveLenFlower);
                    continue;
                }

                if(localName.equals("tempreture")) {
                    growingTips.setTempreture(temperature);
                    continue;
                }

                if (localName.equals("lighting")) {
                    growingTips.setLighting(lighting);
                    continue;
                }

                if (localName.equals("watering")) {
                    growingTips.setWatering(watering);
                }
            }
        }
    }

    public Flowers getFlowers() {
        return flowers;
    }
}