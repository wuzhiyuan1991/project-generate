package project.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import project.service.impl.ProjectGeneratorServiceImpl;

public class FileUtils {

	final static Logger logger = LoggerFactory.getLogger(ProjectGeneratorServiceImpl.class);

	/**
	 * 压缩指定的单个或多个文件，如果是目录，则遍历目录下所有文件进行压缩
	 * 
	 * @param zipFileName
	 *            ZIP文件名包含全路径
	 * @param files
	 *            文件列表
	 */
	public static boolean zip(String zipFileName, File... files) {
		logger.info("压缩: " + zipFileName);
		ZipOutputStream out = null;
		// BufferedOutputStream bo = null;
		try {
			out = new ZipOutputStream(new FileOutputStream(zipFileName));
			for (int i = 0; i < files.length; i++) {
				if (null != files[i]) {
					zip(out, files[i], files[i].getName());
				}
			}
			logger.info("压缩完成");
			return true;
		} catch (Exception e) {
			logger.error("generation zip file error: ", e);
		} finally {
			if (null != out)
				try {
					out.close();
				} catch (IOException e) {
				} // 输出流关闭
		}
		return false;
	}

	/**
	 * 执行压缩
	 * 
	 * @param out
	 *            ZIP输入流
	 * @param f
	 *            被压缩的文件
	 * @param base
	 *            被压缩的文件名
	 */
	private static void zip(ZipOutputStream out, File f, String base) { // 方法重载
		try {
			if (f.isDirectory()) {// 压缩目录
				try {
					File[] fl = f.listFiles();
					if (fl.length == 0) {
						out.putNextEntry(new ZipEntry(base + "/")); // 创建zip实体
						logger.info(base + "/");
					}
					for (int i = 0; i < fl.length; i++) {
						zip(out, fl[i], base + "/" + fl[i].getName()); // 递归遍历子文件夹
					}

				} catch (IOException e) {
					logger.error(e.getMessage());
					e.printStackTrace();
				}
			} else { // 压缩单个文件
				logger.info(base);
				out.putNextEntry(new ZipEntry(base)); // 创建zip实体
				FileInputStream in = new FileInputStream(f);
				BufferedInputStream bi = new BufferedInputStream(in);
				int b;
				while ((b = bi.read()) != -1) {
					out.write(b); // 将字节流写入当前zip目录
				}
				out.closeEntry(); // 关闭zip实体
				in.close(); // 输入流关闭
			}

		} catch (IOException e) {
			logger.error("zip file error: ", e);
		}
	}
	
	 //删除文件和目录
	 public static void clearFiles(String workspaceRootPath){
	      File file = new File(workspaceRootPath);
	      if(file.exists()){
	           deleteFile(file);
	      }
	 }
	 private static void deleteFile(File file){
	      if(file.isDirectory()){
	           File[] files = file.listFiles();
	           for(int i=0; i<files.length; i++){
	                deleteFile(files[i]);
	           }
	      }
	      file.delete();
	 }
}
