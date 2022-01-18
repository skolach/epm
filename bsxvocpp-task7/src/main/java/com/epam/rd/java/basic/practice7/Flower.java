package com.epam.rd.java.basic.practice7;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "name",
    "soil",
    "origin",
    "visualParameters",
    "growingTips",
    "multiplying"
})
public class Flower {

    @XmlElement(required = true)
    protected String name;
    @XmlElement(required = true)
    protected String soil;
    @XmlElement(required = true)
    protected String origin;
    @XmlElement(required = true)
    protected VisualParameters visualParameters;
    @XmlElement(required = true)
    protected GrowingTips growingTips;
    @XmlElement(required = true)
    protected String multiplying;

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the soil property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSoil() {
        return soil;
    }

    /**
     * Sets the value of the soil property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSoil(String value) {
        this.soil = value;
    }

    /**
     * Gets the value of the origin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrigin() {
        return origin;
    }

    /**
     * Sets the value of the origin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrigin(String value) {
        this.origin = value;
    }

    /**
     * Gets the value of the visualParameters property.
     * 
     * @return
     *     possible object is
     *     {@link VisualParameters }
     *     
     */
    public VisualParameters getVisualParameters() {
        return visualParameters;
    }

    /**
     * Sets the value of the visualParameters property.
     * 
     * @param value
     *     allowed object is
     *     {@link VisualParameters }
     *     
     */
    public void setVisualParameters(VisualParameters value) {
        this.visualParameters = value;
    }

    /**
     * Gets the value of the growingTips property.
     * 
     * @return
     *     possible object is
     *     {@link GrowingTips }
     *     
     */
    public GrowingTips getGrowingTips() {
        return growingTips;
    }

    /**
     * Sets the value of the growingTips property.
     * 
     * @param value
     *     allowed object is
     *     {@link GrowingTips }
     *     
     */
    public void setGrowingTips(GrowingTips value) {
        this.growingTips = value;
    }

    /**
     * Gets the value of the multiplying property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMultiplying() {
        return multiplying;
    }

    /**
     * Sets the value of the multiplying property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMultiplying(String value) {
        this.multiplying = value;
    }

}