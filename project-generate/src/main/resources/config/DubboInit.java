package com.ueb.wms.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource({"classpath:dubbo-provider.xml","classpath:dubbo-consumer.xml"})
public class DubboInit {

//	@Autowired
//	AppConfig appConfig;
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

//	@Bean
//	public RegistryConfig registryConfig() {
//		RegistryConfig registryConfig = new RegistryConfig();
//		registryConfig.setAddress(appConfig.getDubboRegistryAddress());
//		return registryConfig;
//	}

	/*
	 * @Bean public AnnotationBean annotationBean() { AnnotationBean
	 * annotationBean = new AnnotationBean(); //LOGGER.info("---> package "
	 * +globalConfig.getAnnotationPackage());
	 * annotationBean.setPackage("com.ueb.esb.mq.consumer.impl"); return
	 * annotationBean; }
	 */

	/*
	 * @Bean public ProtocolConfig dubboProtocolConfig() {
	 * com.alibaba.dubbo.config.ProtocolConfig pconfig = new
	 * com.alibaba.dubbo.config.ProtocolConfig(); pconfig.setName("dubbo");
	 * pconfig.setPort(globalConfig.getProtocolDubboPort()); return pconfig; }
	 */

	/*
	 * @Bean public ProtocolConfig restProtocolConfig() {
	 * com.alibaba.dubbo.config.ProtocolConfig pconfig = new
	 * com.alibaba.dubbo.config.ProtocolConfig(); pconfig.setName("rest1");
	 * pconfig.setPort(globalConfig.getProtocolRestPort());
	 * pconfig.setServer(globalConfig.getProtocolRestServer()); return pconfig;
	 * }
	 */

//	@Bean
//	public com.alibaba.dubbo.config.ProviderConfig loadbalance() {
//		com.alibaba.dubbo.config.ProviderConfig pconfig = new com.alibaba.dubbo.config.ProviderConfig();
//		pconfig.setLoadbalance(appConfig.getDubboLoadbalance());
//		return pconfig;
//	}

}
