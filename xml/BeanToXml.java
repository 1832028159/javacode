package utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

public class BeanToXml {
    
    public static String beanToXml(Object obj,String xmlPath) throws Exception{
        JAXBContext context = JAXBContext.newInstance(obj.getClass());
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
        StringWriter writer = new StringWriter();
        writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?> \n");
        marshaller.marshal(obj,writer);
        BufferedWriter bfw = new BufferedWriter(new FileWriter(new File(xmlPath)));
        bfw.write(writer.toString());
        bfw.close();
        return writer.toString();
    }
    
    public static <T> T xmlToBean(InputStream inputStream,Class<T> targetClass) {
    	return JAXB.unmarshal(inputStream,targetClass);
    }
    
    public static void main(String[] args) throws Exception {
            AmazonEnvelope objAmazonEnvelope = new AmazonEnvelope();
            objAmazonEnvelope.setXmlnsXsi("http://www.w3.org/2001/XMLSchema-instance");
            objAmazonEnvelope.setXsiNoNamespaceSchemaLocation("amzn-envelope.xsd");
            objAmazonEnvelope.setHeader("objAmazonHeader");
            objAmazonEnvelope.setMessageType("OrderAcknowledgement");
            objAmazonEnvelope.setTestXmlAttribute("testXmlAttribute001");
            
            List<AmazonMessage> amazonMessageList = new ArrayList<>();
            AmazonMessage objAmazonMessage = new AmazonMessage();
            objAmazonMessage.setId(UUID.randomUUID().toString());
            objAmazonMessage.setName("AmazonMessageName");
            objAmazonMessage.setTitle("Title0001");
            amazonMessageList.add(objAmazonMessage);
            objAmazonMessage.setTestXmlAttribute("subsetTestXmlAttribute");
            objAmazonEnvelope.setMessage(amazonMessageList);
            
            //beanToXml
            BeanToXml.beanToXml(objAmazonEnvelope, "F:\\bak\\bak10/test004.xml");
            
            File file = new File("F:\\bak\\bak10/test004.xml");
            AmazonEnvelope amazonEnvelope = xmlToBean(new FileInputStream(file), AmazonEnvelope.class);
            System.out.println(amazonEnvelope);
            
            System.out.println("=========done=======");
    }
}