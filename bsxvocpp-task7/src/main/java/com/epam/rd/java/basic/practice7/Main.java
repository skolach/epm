package com.epam.rd.java.basic.practice7;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

public final class Main {

    public static void main(final String[] args) 
            throws ParserConfigurationException, SAXException, IOException, TransformerException, XMLStreamException {
        if (args.length != 2){
            System.out.println("Missmatch in parameters");
            return;
        }

        String xmlFileName = args[0];
        String xsdFileName = args[1];

        //DOM

        DOMController domController = new DOMController(xmlFileName);
        domController.parse(true);
        Flowers flowers = domController.getFlowers();

        Sorter.sortFlowersByName(flowers);

        DOMController.saveXML(flowers, "output.dom.xml");

        //SAX

        SAXController saxController = new SAXController(xmlFileName);
        saxController.parse(true);

        flowers = saxController.getFlowers();

        Sorter.sortFlowersByAveLen(flowers);

        DOMController.saveXML(flowers, "output.sax.xml");

        //StAX

        STAXController staxController = new STAXController(xmlFileName);
        staxController.parse();
        flowers = staxController.getFlowers();

        Sorter.sortFlowersByWatering(flowers);

        DOMController.saveXML(flowers, "output.stax.xml");

    }
}