package org.java.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * converter for kuwo music format
 * @author luoyy
 *
 */
public class FileFormatConvert {

	/**
	 * 将目标文件夹的指定格式文件转换为新的格式，并将转换格式后的文件拷贝到新的文件夹
	 * 
	 * 该方法已经废弃，请使用<code>{@link org.java.utils.FileFormatConvert
	 * #invoke(java.lang.String,java.lang.String,
	 * java.lang.String,java.lang.String)}</code>
	 * @param originPath 原目录
	 * @param newPath 新目录
	 * @param originFormat 原格式
	 * @param newFormat 新格式
	 * @throws IOException 
	 */
	@Deprecated
	public static void convert(String originPath, String newPath, String originFormat, String newFormat) throws IOException {
		File originPathFile = new File(originPath);
		if(!originPathFile.exists() && originPathFile.listFiles().length == 0)
			return;
		
		File newPathFile = new File(newPath);
		if(!newPathFile.exists())
			newPathFile.mkdirs();
		
		for(File file : originPathFile.listFiles()) {
			String fileName = file.getName();
			if(fileName.indexOf(originFormat) == -1)
				continue;
			String newfilePath = newPath + "/" + fileName.substring(0, fileName.indexOf("."))+newFormat;
			if(new File(newfilePath).exists()) continue;
			BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(newfilePath));
			byte[] bytes = new byte[4096];
			int len = 0;
			while((len = bufferedInputStream.read(bytes)) != -1) {
				bufferedOutputStream.write(bytes, 0, len);
			}
			bufferedOutputStream.close();
			bufferedInputStream.close();
		}
	}
	
	/**
	 * 替换covert方法
	 * @param originPath
	 * @param newPath
	 * @param originFormat
	 * @param newFormat
	 * @throws IOException
	 */
	public static void invoke(String originPath, String newPath, String originFormat, String newFormat) throws IOException {
		File originPathFile = new File(originPath);
		if(!originPathFile.exists() && originPathFile.listFiles().length == 0)
			return;
		
		File newPathFile = new File(newPath);
		if(!newPathFile.exists())
			newPathFile.mkdirs();
		
		for(File file : originPathFile.listFiles()) {
			String fileName = file.getName();
			if(fileName.indexOf(originFormat) == -1)
				continue;
			String newfilePath = newPath + "/" + fileName.substring(0, fileName.indexOf("."))+newFormat;
			File newFile = new File(newfilePath);
			if(new File(newfilePath).exists()) continue;
			file.renameTo(newFile);
			break;
		}
	}
	
	
	public static void main(String[] args) {
		try {
			invoke("E:\\KwDownload\\Temp", "E:\\Mymusic", ".wma", ".mp3");
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("=================done===================");
	}
}
