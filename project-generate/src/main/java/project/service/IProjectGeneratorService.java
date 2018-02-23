package project.service;

public interface IProjectGeneratorService {
	/**
	 * 
	 * @param groupid  maven组id
	 * @param artifactid maven 项目id
	 * @param pkg   主包空间  com.ueb.order
	 * @param port  程序启动端口
	 * @param dubboport dubbo端口
	 * @param restport  duebborest端口
	 * @param prjectid 项目id
	 * @return
	 */
	public byte[] run(String groupid, String artifactid, String pkg,String port, String dubboport,
			String restport, String prjectid);
}
