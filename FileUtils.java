package utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtils {
	
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		copy("F:/bak/bak9/v1/AddProduct.ftl", "F:/bak/bak9/v1/AddProduct1.ftl");
		System.out.println("耗时："+(System.currentTimeMillis() - start));
	}

	/**
	 * 文件拷贝
	 * @param sourceFile
	 * @param targetFile
	 */
	public static void copy(String sourceFile, String targetFile) {
		try {
			write(new FileInputStream(sourceFile), targetFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 将输入流写入文件
	 * @param inputStream
	 * @param targetFile
	 */
	public static void writeToFile(InputStream inputStream, String targetFile) {
		write(inputStream, targetFile);
	}
	
	public static void write(InputStream inputStream, String targetFile) {
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bis = new BufferedInputStream(inputStream);
	        bos =  new BufferedOutputStream(new FileOutputStream(targetFile));
	        byte[] buff = new byte[1024 * 1024 * 2];
            int bytesRead;
            while ((bytesRead = bis.read(buff)) != -1) {
                bos.write(buff, 0, bytesRead);
            }
		}catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				bos.close();
				bis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
