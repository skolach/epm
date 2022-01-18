package com.epam.rd.java.basic.practice7;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "tempreture",
    "lighting",
    "watering"
})
public class GrowingTips {

    @XmlElement(required = true)
    protected Tempreture tempreture;
    @XmlElement(required = true)
    protected Lighting lighting;
    protected Watering watering;

    /**
     * Gets the value of the tempreture property.
     * 
     * @return
     *     possible object is
     *     {@link Tempreture }
     *     
     */
    public Tempreture getTempreture() {
        return tempreture;
    }

    /**
     * Sets the value of the tempreture property.
     * 
     * @param value
     *     allowed object is
     *     {@link Tempreture }
     *     
     */
    public void setTempreture(Tempreture value) {
        this.tempreture = value;
    }

    /**
     * Gets the value of the lighting property.
     * 
     * @return
     *     possible object is
     *     {@link Lighting }
     *     
     */
    public Lighting getLighting() {
        return lighting;
    }

    /**
     * Sets the value of the lighting property.
     * 
     * @param value
     *     allowed object is
     *     {@link Lighting }
     *     
     */
    public void setLighting(Lighting value) {
        this.lighting = value;
    }

    /**
     * Gets the value of the watering property.
     * 
     * @return
     *     possible object is
     *     {@link Watering }
     *     
     */
    public Watering getWatering() {
        return watering;
    }

    /**
     * Sets the value of the watering property.
     * 
     * @param value
     *     allowed object is
     *     {@link Watering }
     *     
     */
    public void setWatering(Watering value) {
        this.watering = value;
    }

}