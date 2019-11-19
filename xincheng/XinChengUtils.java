package org.java;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class XinChengUtils {

	public static void main(String[] args) throws Exception {
		// 添加服务
//		 ServiceConfig("/Express/src/kyle/leis/es/businessrule/salespersonrule/sv/SalespersonruleService.java");

		// 添加实体服务
		addEntityService("E:/xincheng/template/common","E:\\eclipse-workspace\\wms-bussiness\\src\\kyle\\wms\\fs\\dictionary\\whslocation","Whslocation","kyle.wms.fs.dictionary.whslocation","TdiWhslocation");

		System.out.println("==============done==================");
	}
	

	/**
	 * 路径替换
	 * 
	 * @param path
	 * @return
	 */
	public static String[] replacePath(String path) {
		String strPath = path.replaceAll("/", ".");
		strPath = strPath.substring(strPath.indexOf("kyle"), strPath.indexOf(".java"));
		return new String[] { strPath.substring(strPath.lastIndexOf(".") + 1), strPath };
	}

	/**
	 * 添加服务
	 * 
	 * @param servicePath
	 * @throws Exception
	 */
	public static void ServiceConfig(String servicePath) throws Exception {
		String xmlPath = "E:/xincheng/express/WebContent/WEB-INF/ServiceConfig.xml";
		String tomcatXmlPath = "E:/eclipse-workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp1/wtpwebapps/Express/WEB-INF/ServiceConfig.xml";
		Document doc = new SAXReader().read(new File(xmlPath));
		Element rootElem = doc.getRootElement();
		String[] valueArr = replacePath(servicePath);
		Element stuElem = rootElem.addElement("Service");
		stuElem.addAttribute("Name", valueArr[0]);
		stuElem.addAttribute("Class", valueArr[1]);
		
		
		for(Iterator item = rootElem.elementIterator(); item.hasNext();) {
			Element service = (Element)item.next();
			String serviceName = service.attributeValue("Name");
			if( serviceName!= null && serviceName.equals(valueArr[0])) {
				System.out.println("添加失败：服务已存在！");
				return;
			}
		}

		FileOutputStream out = new FileOutputStream(xmlPath);
		FileOutputStream out2 = new FileOutputStream(tomcatXmlPath);
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		XMLWriter writer = new XMLWriter(out, format);
		XMLWriter writer2 = new XMLWriter(out2, format);
		writer.write(doc);
		writer2.write(doc);
		writer.close();
		writer2.close();
		System.out.println("服务添加成功！");
	}

	/**
	 * 生成实体服务
	 * 
	 * @param servicePath
	 * @throws Exception
	 */
	public static void addEntityService(String templatePath, String servicePath, String serviceName, String packageName, String entityName) throws Exception {
		File originFile = new File(templatePath);
		for (File file : originFile.listFiles()) {
			if (file.isDirectory()) {
				String newDirPath = servicePath + "/" + file.getName();
				new File(newDirPath).mkdirs();
				addEntityService(file.getPath(), newDirPath, serviceName, packageName, entityName);
			} else {
				String newFileName = servicePath + "/" + file.getName().replaceAll("#serviceName#", serviceName);
				copyFile(file.getPath(), newFileName, serviceName, packageName, entityName);
			}
		}
	}

	/**
	 * 文件复制
	 * 
	 * @param originFilePath
	 * @param newFilePath
	 */
	public static void copyFile(String originFilePath, String newFilePath, String serviceName, String packageName, String entityName) throws Exception {
		final String fileCharset = "GBK";
		File newFile = new File(newFilePath);
		if (!newFile.getParentFile().exists())
			newFile.getParentFile().mkdirs();

		InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(originFilePath), fileCharset);
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(newFile), fileCharset);
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
		String line;
		while ((line = bufferedReader.readLine()) != null) {
			line = line.replaceAll("#serviceName#", serviceName)
					.replaceAll("#packageName#", packageName)
					.replaceAll("#entityName#", entityName);
			bufferedWriter.write(line);
			bufferedWriter.newLine();
		}

		bufferedWriter.flush();
		bufferedWriter.close();
		bufferedWriter.close();
		outputStreamWriter.close();
		inputStreamReader.close();
	}

}
