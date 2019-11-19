package utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtils {
	
	
	public static void main(String[] args) {
		batchReplaceFileName("E:\\eclipse-workspace\\wms-bussiness\\src\\kyle\\wms\\es\\price"
				, "WmspricePrice"
				, "Wmsprice");
		System.out.println("==========done===========");
	}

	/**
	 * 批量替换文件名
	 * @param filePath 需要替换文件名的文件夹
	 * @param oldName 需要替换的旧的名称
	 * @param newName 新的名称
	 */
	public static void batchReplaceFileName(String filePath, String oldName, String newName) {
		File path = new File(filePath);
		if(!path.exists()) return;
		for(File file : path.listFiles()) {
			if(file.isFile()) {
				String oldPath = file.getPath();
				if(oldPath.indexOf(oldName) > -1) {
					file.renameTo(new File(oldPath.replaceAll(oldName, newName)));
				}
			}else {
				batchReplaceFileName(file.getPath(), oldName, newName);
			}
		}
	}
	
	public static List<String> getMatch(String regex, String sourceStr) {
		Pattern patten = Pattern.compile(regex);
        Matcher matcher = patten.matcher(sourceStr);
        List<String> matchStrs = new ArrayList<>();
        while (matcher.find()) { 
           matchStrs.add(matcher.group());
        }
        return matchStrs;
	}
}
