package org.java;

import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import de.hunsicker.jalopy.Jalopy;

public class GenerateColumns {
	
	public static void main(String[] args) throws Exception {
		String configPath = GenerateColumns.class.getResource("/").toString().replace("file:/", "");
		SAXReader saxReader = new SAXReader();
		Document document = saxReader.read(new File(configPath+"config.xml"));
		List<Node> nodes = document.selectNodes("//HibCool/BatchFile");
		for(Node node : nodes) {
			String status = node.valueOf("@Status");
			if(status != null && status.equals("InActive")) continue;
			generate(Template.TEMPLATEPATH.getContent()+node.getStringValue());
		}
		System.out.println("===========done===========");
	}

	/**
	 * 生成colum,condition,query
	 * @param xmlPath
	 * @throws Exception
	 */
	public static void generate(String xmlPath) throws Exception{
		SAXReader saxReader = new SAXReader();
		Document document = saxReader.read(new File(xmlPath));
		String packageName = document.selectSingleNode("//Batch/Java").valueOf("@Package");
		List<Node> nodes = document.selectNodes("//Batch/PageQuery");
		for(Node node : nodes) {
			String columnTemplate = Template.COLUMNS.getContent();
			String name = toUpperCaseFirst(node.valueOf("@Name"));
			String columnName = name+"Columns";
			String select = node.valueOf("@Select");
			String from = node.valueOf("@From");
			String where = node.valueOf("@Where");
			String orderby = node.valueOf("@Orderby");
			String groupby = node.valueOf("@Groupby");
			String[] fieldArr = select.split(",");
			StringBuilder strFieldParam = new StringBuilder();
			StringBuilder strSetFun = new StringBuilder();
			StringBuilder strGetSet = new StringBuilder();
			int fieldSize = fieldArr.length;
			String columnSize = String.valueOf(fieldSize);
			for(int i = 0; i < fieldSize; i++) {
				String field = fieldArr[i];
				Matcher matcher = Pattern.compile("\\.\\w").matcher(field);
				matcher.find();
				String upper = matcher.group(0);
				String realField = field.replace(upper, upper.toUpperCase()).replace(".", "");
				
				strFieldParam.append("String "+realField);
				if(i != fieldSize-1) strFieldParam.append(",");

				String setGetName = toUpperCaseFirst(realField.toLowerCase());
				strSetFun.append("set"+setGetName+"("+realField+");");
			
				strGetSet.append("public void set"+setGetName+"(String "+realField+") {this.setField("+i+", "+realField+");} ")
				.append(" public String get"+setGetName+"() {return this.getField("+i+");}");
			}
			
			//生成columns
			String columns = columnTemplate.replace("#PackageName#", packageName)
					.replaceAll("#ColumnName#", columnName)
					.replaceAll("#ColumnSize#", columnSize)
					.replaceAll("#FieldParam#", strFieldParam.toString())
					.replaceAll("#SetFun#", strSetFun.toString())
					.replaceAll("#GetSet#", strGetSet.toString());
			writeStringToFile(packageName.replace(".", "/")+"/"+columnName+".java",columns);
	        
	        String conditionTemplate = Template.CONDITION.getContent();
	        String conditonName = name+"Condition";
			List<Node> conditions = node.selectNodes("./Condition");
			int conditionSize = conditions.size();
			StringBuilder conditionGetSet = new StringBuilder();
			StringBuilder strConditionWords = new StringBuilder();
			StringBuilder strConditionCount = new StringBuilder();
			for(int i = 0; i < conditionSize; i++) {
				Node conditionNode = conditions.get(i);
				String condition = conditionNode.getStringValue();
				String conditonField = condition.split("=")[1].replaceAll("('\\$|\\$'|\\s)", "");
				String setGetName = toUpperCaseFirst(conditonField.toLowerCase());
				conditionGetSet.append("public void set"+setGetName+"(String "+conditonField+") {this.setField("+i+", "+conditonField+");} ")
				.append(" public String get"+setGetName+"() {return this.getField("+i+");}");
				
				strConditionWords.append("\""+condition.replaceAll("\\$\\w*\\$", "~~")+"\"");
				strConditionCount.append("1");
				if(i != conditionSize-1) {
					strConditionWords.append(",");
					strConditionCount.append(",");
				}
			}
			
			//生成condition
			String condition = conditionTemplate.replace("#PackageName#", packageName)
					.replaceAll("#ConditionName#", conditonName)
					.replaceAll("#ConditionSize#", String.valueOf(conditionSize+1))
					.replaceAll("#GetSet#", conditionGetSet.toString());
			writeStringToFile(packageName.replace(".", "/")+"/"+conditonName+".java",condition);
			
			//生成query
			String queryTemplate = Template.QUERY.getContent();
			String query = queryTemplate.replace("#PackageName#", packageName)
					.replaceAll("#QueryName#", name)
					.replaceAll("#Select#", select)
					.replaceAll("#From#", from)
					.replaceAll("#Where#", where)
					.replaceAll("#Orderby#", orderby)
					.replaceAll("#Groupby#", groupby)
					.replaceAll("#ConditionWords#", strConditionWords.toString())
					.replaceAll("#ConditionCount#", strConditionCount.toString())
					.replaceAll("#GetSet#", conditionGetSet.toString());
			writeStringToFile(packageName.replace(".", "/")+"/"+name+"Query.java",query);
		}
	}
	
	/**
	 * 首字母转大写
	 * @param str
	 * @return
	 */
	public static String toUpperCaseFirst(String str){
	    return (new StringBuilder()).append(Character.toUpperCase(str.charAt(0))).append(str.substring(1).toLowerCase()).toString();
	}
	
	/**
	 * 将字符串写入文件
	 * @param filePath
	 * @param str
	 * @throws Exception
	 */
	public static void writeStringToFile(String filePath, String str) throws Exception{
		StringBuffer output = new StringBuffer();
        Jalopy jalopy = new Jalopy();
        jalopy.setEncoding("UTF-8");
        jalopy.setInput(str, "Generate.java");
        jalopy.setOutput(output);
        jalopy.format();
        String path = Template.GENERATEPATH.getContent();
        String columnPath = path+filePath;
        File packagePath = new File(columnPath).getParentFile();
        if(!packagePath.exists()) packagePath.mkdirs();
        FileWriter fileWriter = new FileWriter(columnPath);
        fileWriter.write(output.toString());
        fileWriter.close();
	}
}
