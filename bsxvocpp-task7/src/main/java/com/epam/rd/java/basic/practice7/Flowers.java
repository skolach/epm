package com.epam.rd.java.basic.practice7;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "flower"
})
@XmlRootElement(name = "flowers")
public class Flowers {

    protected List<Flower> flowers;

    /**
     * Gets the value of the flower property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the flower property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFlower().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Flower }
     * 
     * 
     */
    public List<Flower> getFlowers() {
        if (flowers == null) {
            flowers = new ArrayList<Flower>();
        }
        return this.flowers;
    }

}
