package utils;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="AmazonEnvelope")
@XmlType(propOrder={
	    "Header",
	    "MessageType",
	    "Message"
	})
public class AmazonEnvelope {
	
	@XmlAttribute(name="xsi:noNamespaceSchemaLocation")
	private String xsiNoNamespaceSchemaLocation;
	
	@XmlAttribute(name="xmlns:xsi")
	private String xmlnsXsi;
	
	@XmlAttribute(name="testXmlAttribute")
	private String testXmlAttribute;
	
	@XmlElement(name = "Header")
	private String Header;
	
	@XmlElement(name = "MessageType")
	private String MessageType;
	
	@XmlElement(name = "Message")
	private List<AmazonMessage> Message;

	public String getHeader() {
		return Header;
	}

	public void setHeader(String header) {
		Header = header;
	}

	public String getMessageType() {
		return MessageType;
	}

	public void setMessageType(String messageType) {
		MessageType = messageType;
	}

	public List<AmazonMessage> getMessage() {
		return Message;
	}

	public void setMessage(List<AmazonMessage> message) {
		Message = message;
	}

	public String getXmlnsXsi() {
		return xmlnsXsi;
	}

	public void setXmlnsXsi(String xmlnsXsi) {
		this.xmlnsXsi = xmlnsXsi;
	}

	public String getXsiNoNamespaceSchemaLocation() {
		return xsiNoNamespaceSchemaLocation;
	}

	public void setXsiNoNamespaceSchemaLocation(String xsiNoNamespaceSchemaLocation) {
		this.xsiNoNamespaceSchemaLocation = xsiNoNamespaceSchemaLocation;
	}
	
	
	
	public String getTestXmlAttribute() {
		return testXmlAttribute;
	}

	public void setTestXmlAttribute(String testXmlAttribute) {
		this.testXmlAttribute = testXmlAttribute;
	}

	@Override
	public String toString() {
		return "testXmlAttribute:"+testXmlAttribute+";xsiNoNamespaceSchemaLocation:"+xsiNoNamespaceSchemaLocation;
	}
}
