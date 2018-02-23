package {mainpackage}.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import com.alibaba.dubbo.config.RegistryConfig;
import {mainpackage}.disconf.GlobalConfig;

@Configuration
@ImportResource("classpath:dubbo.xml")
public class DubboConfigInit {

	@Autowired
	GlobalConfig globalConfig;
	/*
	 * protected static final Logger LOGGER = LoggerFactory
	 * .getLogger(DubboConfigInit.class);
	 */

	/*
	 * @Bean public ApplicationConfig applicationConfig() { ApplicationConfig
	 * applicationConfig = new ApplicationConfig(); LOGGER.info(
	 * "---> objecta {}", dubboConfig);
	 * applicationConfig.setName("mq-consumer"); return applicationConfig; }
	 */

	@Bean
	public RegistryConfig registryConfig() {
		RegistryConfig registryConfig = new RegistryConfig();
		registryConfig.setAddress(globalConfig.getRegistryAddress());
		return registryConfig; 
	}

	/*
	 * @Bean public AnnotationBean annotationBean() { AnnotationBean
	 * annotationBean = new AnnotationBean(); //LOGGER.info("---> package "
	 * +dubboConfig.getAnnotationPackage());
	 * annotationBean.setPackage("com.ueb.esb.mq.consumer.impl"); return
	 * annotationBean; }
	 */

	/*
	 * @Bean public ProtocolConfig dubboProtocolConfig() {
	 * com.alibaba.dubbo.config.ProtocolConfig pconfig = new
	 * com.alibaba.dubbo.config.ProtocolConfig(); pconfig.setName("dubbo");
	 * pconfig.setPort(dubboConfig.getProtocolDubboPort()); return pconfig; }
	 */

	/*
	 * @Bean public ProtocolConfig restProtocolConfig() {
	 * com.alibaba.dubbo.config.ProtocolConfig pconfig = new
	 * com.alibaba.dubbo.config.ProtocolConfig(); pconfig.setName("rest1");
	 * pconfig.setPort(dubboConfig.getProtocolRestPort());
	 * pconfig.setServer(dubboConfig.getProtocolRestServer()); return pconfig; }
	 */

	@Bean
	public com.alibaba.dubbo.config.ProviderConfig loadbalance() {
		com.alibaba.dubbo.config.ProviderConfig pconfig = new com.alibaba.dubbo.config.ProviderConfig();
		pconfig.setLoadbalance(globalConfig.getServiceLoadbalance());

		return pconfig;
	}

}
