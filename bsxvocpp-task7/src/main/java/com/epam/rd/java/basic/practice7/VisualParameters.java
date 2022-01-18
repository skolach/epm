package com.epam.rd.java.basic.practice7;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "stemColour",
    "leafColour",
    "aveLenFlower"
})
public class VisualParameters {

    @XmlElement(required = true)
    protected String stemColour;
    @XmlElement(required = true)
    protected String leafColour;
    protected AveLenFlower aveLenFlower;

    /**
     * Gets the value of the stemColour property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStemColour() {
        return stemColour;
    }

    /**
     * Sets the value of the stemColour property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStemColour(String value) {
        this.stemColour = value;
    }

    /**
     * Gets the value of the leafColour property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLeafColour() {
        return leafColour;
    }

    /**
     * Sets the value of the leafColour property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLeafColour(String value) {
        this.leafColour = value;
    }

    /**
     * Gets the value of the aveLenFlower property.
     * 
     * @return
     *     possible object is
     *     {@link AveLenFlower }
     *     
     */
    public AveLenFlower getAveLenFlower() {
        return aveLenFlower;
    }

    /**
     * Sets the value of the aveLenFlower property.
     * 
     * @param value
     *     allowed object is
     *     {@link AveLenFlower }
     *     
     */
    public void setAveLenFlower(AveLenFlower value) {
        this.aveLenFlower = value;
    }

}