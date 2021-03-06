
package org.openclinica.ws.beans;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for eventCrfInformationList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="eventCrfInformationList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="eventCrf" type="{http://openclinica.org/ws/beans}eventCrfType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "eventCrfInformationList", propOrder = {
    "eventCrf"
})
public class EventCrfInformationList {

    protected List<EventCrfType> eventCrf;

    /**
     * Gets the value of the eventCrf property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the eventCrf property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEventCrf().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EventCrfType }
     * 
     * 
     */
    public List<EventCrfType> getEventCrf() {
        if (eventCrf == null) {
            eventCrf = new ArrayList<EventCrfType>();
        }
        return this.eventCrf;
    }

}
