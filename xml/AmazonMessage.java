package utils;

import javax.xml.bind.annotation.XmlAttribute;

public class AmazonMessage {
	
	private String id;

	private String title;
	
	private String name;
	
	private String testXmlAttribute;

	public String getTestXmlAttribute() {
		return testXmlAttribute;
	}

	@XmlAttribute(name="testXmlAttribute")
	public void setTestXmlAttribute(String testXmlAttribute) {
		this.testXmlAttribute = testXmlAttribute;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
