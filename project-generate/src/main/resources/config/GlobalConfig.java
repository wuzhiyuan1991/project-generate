package {mainpackage}.disconf;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.baidu.disconf.client.common.annotations.DisconfFile;
import com.baidu.disconf.client.common.annotations.DisconfFileItem;

@Configuration
@Service
@Scope("singleton")
@DisconfFile(filename = "global.properties")
public class GlobalConfig {

	private String registryAddress;
	/**
	 * 负载均衡配置 负载均衡策略，可选值：random,roundrobin,leastactive，分别表示：随机，轮循，最少活跃调用
	 */
	private String serviceLoadbalance;

	@DisconfFileItem(name = "dubbo.registry.address")
	public String getRegistryAddress() {
		return registryAddress;
	}

	@DisconfFileItem(name = "dubbo.service.loadbalance")
	public String getServiceLoadbalance() {
		return serviceLoadbalance;
	}
	
	private String driverClassName;

	private String url;

	private String username;

	private String password;

	private long timeout;

	private int pool;

	@DisconfFileItem(name = "datasource.order.driverClassName")
	public String getDriverClassName() {
		return driverClassName;
	}

	@DisconfFileItem(name = "datasource.order.url")
	public String getUrl() {
		return url;
	}

	@DisconfFileItem(name = "datasource.order.username")
	public String getUsername() {
		return username;
	}

	@DisconfFileItem(name = "datasource.order.password")
	public String getPassword() {
		return password;
	}

	@DisconfFileItem(name = "datasource.order.timeout")
	public long getTimeout() {
		return timeout;
	}

	@DisconfFileItem(name = "datasource.order.pool")
	public int getPool() {
		return pool;
	}
	

}
