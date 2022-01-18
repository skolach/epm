package com.epam.rd.java.basic.practice7;

import java.io.File;
import java.io.IOException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class DOMController {

    private String xmlFileName;
    private Flowers flowers;

    public DOMController(String xmlFileName) {
        this.xmlFileName = xmlFileName;
    }

    public void parse(boolean validate) throws ParserConfigurationException,
            SAXException, IOException{

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance(
            "com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl",
            this.getClass().getClassLoader()
        );

        dbf.setNamespaceAware(true);

        if (validate) {
            dbf.setFeature("http://xml.org/sax/features/validation", true);
            dbf.setFeature("http://apache.org/xml/features/validation/schema", true);
        }

        DocumentBuilder db = dbf.newDocumentBuilder();
        db.setErrorHandler(new DefaultHandler() {
            @Override
            public void error(SAXParseException e) throws SAXException {
                throw e;
            }
        });

        Document document = db.parse(xmlFileName);
        Element root = document.getDocumentElement();

        flowers = new Flowers();

        NodeList flowerNodes = root.getElementsByTagName("flower");

        for (int i = 0; i < flowerNodes.getLength(); i++) {
            Flower flower = getFlower(flowerNodes.item(i));
            flowers.getFlowers().add(flower);
        }



    }

    private Flower getFlower(Node item) {
        Flower flower = new Flower();
        Element fElement = (Element) item;

        flower.setName(
            fElement.getElementsByTagName("name").item(0).getTextContent()
        );

        flower.setSoil(
            fElement.getElementsByTagName("soil").item(0).getTextContent()
        );

        flower.setOrigin(
            fElement.getElementsByTagName("origin").item(0).getTextContent()
        );

        flower.setMultiplying(
            fElement.getElementsByTagName("multiplying").item(0).getTextContent()
        );

        flower.setVisualParameters(
            getVisualParameters(fElement.getElementsByTagName("visualParameters").item(0))
        );

        flower.setGrowingTips(
            getGrowingTips(fElement.getElementsByTagName("growingTips").item(0))
        );

        return flower;
    }

    private GrowingTips getGrowingTips(Node item) {
        GrowingTips gt = new GrowingTips();
        Element gtElement = (Element)item;

        Tempreture temperature = new Tempreture();
        temperature.setValue(
            Integer.parseInt(
                gtElement.getElementsByTagName("tempreture").item(0).getTextContent()));
        Element temperatureElement = 
            (Element)gtElement.getElementsByTagName("tempreture").item(0);
        temperature.setMeasure(temperatureElement.getAttribute("measure"));
        gt.setTempreture(temperature);

        Lighting light = new Lighting();
        Element lightElement = (Element)gtElement.getElementsByTagName("lighting").item(0);
        light.setLightRequiring(lightElement.getAttribute("lightRequiring"));
        gt.setLighting(light);

        Watering watering = new Watering();
        watering.setValue(
            Integer.parseInt(
                gtElement.getElementsByTagName("watering").item(0).getTextContent()));
        Element wateringElement = 
            (Element)gtElement.getElementsByTagName("watering").item(0);
        watering.setMeasure(wateringElement.getAttribute("measure"));
        gt.setWatering(watering);

        return gt;
    }

    private VisualParameters getVisualParameters(Node item) {
        VisualParameters visualParameters = new VisualParameters();
        Element vpElement = (Element)item;
        // <visualParameters>
        //      <stemColour>green</stemColour>
        //      <leafColour>green</leafColour>
        //      <aveLenFlower measure = "cm">10</aveLenFlower>
        // </visualParameters>
        visualParameters.setStemColour(
            vpElement.getElementsByTagName("stemColour").item(0).getTextContent()
        );
        visualParameters.setLeafColour(
            vpElement.getElementsByTagName("leafColour").item(0).getTextContent()
        );
        AveLenFlower aveLenFlower = new AveLenFlower();
        aveLenFlower.setValue(
            Integer.parseInt(vpElement.getElementsByTagName("aveLenFlower").item(0).getTextContent())
        );
        Element alfElement = (Element)vpElement.getElementsByTagName("aveLenFlower").item(0);
        aveLenFlower.setMeasure(
            alfElement.getAttribute("measure")
        );
        visualParameters.setAveLenFlower(aveLenFlower);

        return visualParameters;
    }

    public Flowers getFlowers() {
        return this.flowers;
    }

    public static void saveXML(Flowers flowers, String fileName) throws ParserConfigurationException, TransformerException {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance(
            "com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl",
            DOMController.class.getClassLoader());

        dbf.setNamespaceAware(true);
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.newDocument();

        Element rootElement = document.createElement("flowers");

        rootElement.setAttribute("xmlns:xsi", 
            "http://www.w3.org/2001/XMLSchema-instance");

        document.appendChild(rootElement);

        for (Flower flower : flowers.getFlowers()) {
            Element flowerElement = document.createElement("flower");
            rootElement.appendChild(flowerElement);

            Element nameElement = document.createElement("name");
            nameElement.setTextContent(flower.getName());
            flowerElement.appendChild(nameElement);

            Element soilElement = document.createElement("soil");
            soilElement.setTextContent(flower.getSoil());
            flowerElement.appendChild(soilElement);

            Element originElement = document.createElement("origin");
            originElement.setTextContent(flower.getOrigin());
            flowerElement.appendChild(originElement);

                Element visualParametersElement = document.createElement("visualParameters");

                Element stemColourElement = document.createElement("stemColour");
                stemColourElement.setTextContent(flower.getVisualParameters().getStemColour());

                Element leafColourElement = document.createElement("leafColour");
                leafColourElement.setTextContent(flower.getVisualParameters().getLeafColour());

                Element aveLenFlowerElement = document.createElement("aveLenFlower");
                aveLenFlowerElement.setTextContent(flower.getVisualParameters().getAveLenFlower().getValue().toString());
                aveLenFlowerElement.setAttribute("measure", flower.getVisualParameters().getAveLenFlower().getMeasure());


                visualParametersElement.appendChild(stemColourElement);
                visualParametersElement.appendChild(leafColourElement);
                visualParametersElement.appendChild(aveLenFlowerElement);

            flowerElement.appendChild(visualParametersElement);

                Element growingTipsElement = document.createElement("growingTips");

                Element temperatureElement = document.createElement("tempreture");
                temperatureElement.setTextContent(flower.getGrowingTips().getTempreture().getValue().toString());
                temperatureElement.setAttribute("measure", flower.getGrowingTips().getTempreture().getMeasure());

                Element lightingElement = document.createElement("lighting");
                lightingElement.setAttribute("lightRequiring", flower.getGrowingTips().getLighting().getLightRequiring());

                Element wateringElement = document.createElement("watering");
                wateringElement.setTextContent(flower.getGrowingTips().getWatering().getValue().toString());
                wateringElement.setAttribute("measure", flower.getGrowingTips().getWatering().getMeasure());

                growingTipsElement.appendChild(temperatureElement);
                growingTipsElement.appendChild(lightingElement);
                growingTipsElement.appendChild(wateringElement);

            flowerElement.appendChild(growingTipsElement);

            Element multiplyingElement = document.createElement("multiplying");
            multiplyingElement.setTextContent(flower.getMultiplying());
            flowerElement.appendChild(multiplyingElement);
        }

        saveDOMToXML(document, fileName);

    }

    private static void saveDOMToXML(Document document, String fileName) throws TransformerException {

        StreamResult result = new StreamResult(new File(fileName));
		
		TransformerFactory tf = TransformerFactory.newInstance();
		javax.xml.transform.Transformer t = tf.newTransformer();
		t.setOutputProperty("indent", "yes");			
		
		t.transform(new DOMSource(document), result);
    }

}