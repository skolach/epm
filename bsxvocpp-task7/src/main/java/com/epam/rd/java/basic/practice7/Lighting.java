package com.epam.rd.java.basic.practice7;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
public class Lighting {

    @XmlAttribute(name = "lightRequiring", required = true)
    protected String lightRequiring;

    /**
     * Gets the value of the lightRequiring property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLightRequiring() {
        return lightRequiring;
    }

    /**
     * Sets the value of the lightRequiring property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLightRequiring(String value) {
        this.lightRequiring = value;
    }

}