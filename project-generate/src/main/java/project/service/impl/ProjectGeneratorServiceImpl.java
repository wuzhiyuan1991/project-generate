package project.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.google.common.io.Files;

import project.service.IProjectGeneratorService;
import project.utils.FileUtils;

/**
 * 项目生产
 * 
 * @author fengcl
 *
 */
@Service
public class ProjectGeneratorServiceImpl implements IProjectGeneratorService {

	final String PROJECT_ROOT = "projects";

	Logger logger = LoggerFactory.getLogger(ProjectGeneratorServiceImpl.class);

	/**
	 * 
	 * @param groupid
	 *            com.ueb.listing
	 * @param artifactid
	 *            ueb-listing
	 * @param pkg
	 *            com.ueb.listing
	 * @param version
	 *            0.0.1-SNAPSHOT
	 * @param port
	 *            8101
	 * @return
	 */
	public byte[] run(String groupid, String artifactid, String pkg, String port, String dubboport, String restport,
			String serviceid) {
		File directory = new File(PROJECT_ROOT + File.separator + artifactid);
		if (directory.exists()) {
			FileUtils.clearFiles(directory.getPath());
		}
		directory.mkdirs();
		String rootPath = directory.getPath();
		saveModuleMavenConfig(rootPath, groupid, artifactid, "-parent", "");
		this.createModule(rootPath, groupid, artifactid, pkg, "api", "", "");
		this.createModule(rootPath, groupid, artifactid, pkg, "disconf", "GlobalConfig.java", "GlobalConfig.java");
		this.createModule(rootPath, groupid, artifactid, pkg, "dao", "MysqlDataSource.java", "MysqlDataSource.java");
		String[] artifacts = artifactid.split("-");
		String mainClass = "";
		for (String ar : artifacts) {
			mainClass += ar.substring(0, 1).toUpperCase() + ar.substring(1);
		}
		String moduleconfigpath = this.createModule(rootPath, groupid, artifactid, pkg, "service", "App.java",
				mainClass + "App.java");
		this.createProjectConfig(moduleconfigpath, "disconf.properties", pkg, artifactid.replace("-", ""), dubboport,
				restport, serviceid, port);
		this.createProjectConfig(moduleconfigpath, "disconf.xml", pkg, artifactid.replace("-", ""), dubboport, restport,
				serviceid, port);
		this.createProjectConfig(moduleconfigpath, "dubbo.properties", pkg, artifactid.replace("-", ""), dubboport,
				restport, serviceid, port);
//		this.createProjectConfig(moduleconfigpath, "dubbo.xml", pkg, artifactid.replace("-", ""), dubboport, restport,
//				serviceid, port);
		this.createProjectConfig(moduleconfigpath, "dubbo-consumer.xml", pkg, artifactid.replace("-", ""), dubboport, restport,
				serviceid, port);
		this.createProjectConfig(moduleconfigpath, "dubbo-provider.xml", pkg, artifactid.replace("-", ""), dubboport, restport,
				serviceid, port);
		this.createProjectConfig(moduleconfigpath, "log4j.properties", pkg, artifactid.replace("-", ""), dubboport,
				restport, serviceid, port);
		this.createProjectConfig(moduleconfigpath, "log4j2.xml", pkg, artifactid.replace("-", ""), dubboport,
				restport, serviceid, port);
		this.createProjectConfig(moduleconfigpath, "main-application.properties", pkg, artifactid.replace("-", ""),
				dubboport, restport, serviceid, port);
		String path = directory.getPath() + File.separator + artifactid + ".zip";
		if (FileUtils.zip(path, directory.listFiles())) {
			try {
				return Files.toByteArray(new File(path));
			} catch (IOException e) {
				logger.error("get error : ", e);
			}
		}
		return null;
	}

	private String createModule(String rootpath, String groupid, String artifactid, String mainpkg, String moduleSuffix,
			String className, String newClassName) {
		String mSuffix = "-" + moduleSuffix;
		File file = new File(rootpath + File.separator + artifactid + mSuffix);
		file.mkdir();
		String mclasspath = this.createModulePath(file.getPath(), mainpkg + "." + moduleSuffix);
		String mainclassname = newClassName;
		if (mainclassname.contains(".")) {
			mainclassname = mainclassname.substring(0, mainclassname.lastIndexOf('.'));
		}
		saveModuleMavenConfig(file.getPath(), groupid, artifactid, mSuffix, mainpkg + "." + mainclassname);
		createModuleClass(mclasspath, mainpkg, className, mainclassname);
		return file.getPath();
	}

	/**
	 * 
	 * @param modulePath
	 * @param pkg
	 * @return 返回模块主包路径
	 */
	private String createModulePath(String modulePath, String pkg) {
		String javapaths = modulePath + File.separator + "src" + File.separator + "main" + File.separator + "java";
		for (String p : pkg.split("\\.")) {
			javapaths += File.separator + p;
		}
		new File(javapaths).mkdirs();
		new File(modulePath + File.separator + "src" + File.separator + "main" + File.separator + "resources").mkdirs();
		new File(modulePath + File.separator + "src" + File.separator + "test" + File.separator + "java").mkdirs();
		new File(modulePath + File.separator + "src" + File.separator + "test" + File.separator + "resources").mkdirs();
		return javapaths;
	}

	private void saveModuleMavenConfig(String roopath, String groupid, String artifactid, String moduleSuffix,
			String mainclass) {
		BufferedWriter bw = null;
		try {
			List<String> list = Files.readLines(
					new File(this.getClass().getResource("/config/pom" + moduleSuffix + ".xml").getFile().toString()),
					Charset.forName("UTF-8"));
			File newFile = new File(roopath + File.separator + "pom.xml");
			if (newFile.exists())
				newFile.delete();
			newFile.createNewFile();
			bw = Files.newWriter(newFile, Charset.forName("UTF-8"));
			String artifactname = artifactid.replace("-", "");
			for (String line : list) {
				bw.append(line.replace("{groupid}", groupid).replace("{artifactid}", artifactid)
						.replace("{artifactname}", artifactname).replace("{mainclass}", mainclass));
				bw.newLine();
			}
			bw.flush();
			bw.close();
		} catch (IOException e) {
			logger.error("generation " + moduleSuffix + " maven config module error: ", e);
		} finally {
			if (bw != null) {
				try {
					bw.flush();
					bw.close();
				} catch (Exception e) {
				}
			}
		}
	}

	private void createProjectConfig(String serviceModulePath, String configfileName, String pkg, String artifactname,
			String dubboport, String restport, String serviceid, String port) {
		File file = new File(
				serviceModulePath + File.separator + "src" + File.separator + "main" + File.separator + "resources");
		BufferedWriter bw = null;
		try {
			List<String> list = Files.readLines(
					new File(this.getClass().getResource("/config/" + configfileName).getFile().toString()),
					Charset.forName("UTF-8"));
			String newName = configfileName;
			if (newName.contains("-")) {
				newName = newName.substring(newName.indexOf('-') + 1);
			}
			
			// 20180130修改，dubbo配置文件修改
			if(configfileName.equals("dubbo-consumer.xml") || configfileName.equals("dubbo-provider.xml")){
				newName = configfileName;
			}
			
			File newFile = new File(file.getPath() + File.separator + newName);
			if (newFile.exists())
				newFile.delete();
			newFile.createNewFile();
			bw = Files.newWriter(newFile, Charset.forName("UTF-8"));
			for (String line : list) {
				bw.append(line.replace("{package}", pkg).replace("{artifactname}", artifactname)
						.replace("{dubboport}", dubboport).replace("{restport}", restport)
						.replace("{serviceid}", serviceid).replace("{port}", port));
				bw.newLine();
			}
			bw.flush();
			bw.close();
		} catch (IOException e) {
			logger.error("generation " + configfileName + " module  config  error: ", e);
		} finally {
			if (bw != null) {
				try {
					bw.flush();
					bw.close();
				} catch (Exception e) {
				}
			}
		}
	}

	private void createModuleClass(String modulemainpath, String pkg, String className, String newClassName) {
		if (StringUtils.isEmpty(className))
			return;
		File file = new File(modulemainpath);
		if (modulemainpath.lastIndexOf("service") > 0) {
			file = file.getParentFile();
		} else if (modulemainpath.lastIndexOf("dao") > 0) {
			file = new File(modulemainpath + File.separator + "config");
		}
		BufferedWriter bw = null;
		try {
			file.mkdirs();
			List<String> list = Files.readLines(
					new File(this.getClass().getResource("/config/" + className).getFile().toString()),
					Charset.forName("UTF-8"));
			File newFile = new File(file.getPath() + File.separator + newClassName + ".java");
			if (newFile.exists())
				newFile.delete();
			newFile.createNewFile();
			bw = Files.newWriter(newFile, Charset.forName("UTF-8"));
			for (String line : list) {
				bw.append(line.replace("{mainpackage}", pkg).replace("{mainclass}", newClassName));
				bw.newLine();
			}
			bw.flush();
			bw.close();
		} catch (IOException e) {
			logger.error("generation " + className + " module  class  error: ", e);
		} finally {
			if (bw != null) {
				try {
					bw.flush();
					bw.close();
				} catch (Exception e) {
				}
			}
		}

	}

}
