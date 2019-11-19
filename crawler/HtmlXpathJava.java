package test;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.w3c.dom.Document;

public class HtmlXpathJava {
	
	public static void main(String[] args) {
		String sampleHtml = "<div2><table><td id='1234 fo3o3 5678'>Hello</td>";
		String sampleXpath = "//div2//td[contains(@id, 'fo3o3')]/text()";
		System.out.println(getValueByXpath(sampleXpath, sampleHtml));
	}
	
	/**
	 * Extract value by xPath from HTML.
	 */
	private static String getValueByXpath(String xPath, String html) {
		TagNode tagNode = new HtmlCleaner().clean(html);
		String value = null;
		try {
			Document doc = new DomSerializer(new CleanerProperties()).createDOM(tagNode);
			XPath xpath = XPathFactory.newInstance().newXPath();
			value = (String) xpath.evaluate(xPath, doc);
		} catch (Exception e) {
			System.out.println("Extract value error : " + e.getMessage());
			e.printStackTrace();
		}
		return value;
	}


}
