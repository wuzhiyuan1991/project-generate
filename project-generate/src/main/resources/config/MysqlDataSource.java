//package {mainpackage}.dao.config;
//
//import java.util.Properties;
//
//import javax.sql.DataSource;
//
//import org.apache.ibatis.plugin.Interceptor;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.mybatis.spring.annotation.MapperScan;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import com.github.pagehelper.PageHelper;
//import {mainpackage}.disconf.GlobalConfig;
//import com.zaxxer.hikari.HikariConfig;
//import com.zaxxer.hikari.HikariDataSource;
//
//@Component
//@Configuration
//@EnableTransactionManagement
//@MapperScan(basePackages = "{mainpackage}.dao.mapper.**", sqlSessionFactoryRef = "mySqlSessionFactory", sqlSessionTemplateRef = "mySqlSessionTemplate")
//public class MysqlDataSource {
//
//	protected static final Logger LOGGER = LoggerFactory.getLogger(MysqlDataSource.class);
//
//
//	/**
//	 * DataSource
//	 */
//	@Bean(destroyMethod = "close", name = "mydb")
//	protected HikariDataSource buildDataSource() {
//		HikariConfig cfg = new HikariConfig();
//		cfg.setUsername(globalConfig.getUsername());
//		cfg.setPassword(globalConfig.getPassword());
//		cfg.setJdbcUrl(globalConfig.getUrl());
//		cfg.setDriverClassName(globalConfig.getDriverClassName());
//		cfg.setIdleTimeout(globalConfig.getTimeout());
//		cfg.setMaximumPoolSize(globalConfig.getPool());
//		// cfg.getConnectionTestQuery("");
//		// cfg.setDataSourceJNDI(jndiDataSource);
//		return new HikariDataSource(cfg);
//	}
//
//	/**
//	 * TransactionManager
//	 */
//	@Bean(name = "mytx")
//	protected PlatformTransactionManager createTransactionManager(@Qualifier("mydb") DataSource dataSource) {
//		return new DataSourceTransactionManager(dataSource); // new
//	}
//
//	@Bean(name = "mySqlSessionFactory")
//	public SqlSessionFactory sqlSessionFactory(@Qualifier("mydb") DataSource dataSource) throws Exception {
//		final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
//		sessionFactory.setDataSource(dataSource);
//		// 分页插件
//		PageHelper pageHelper = new PageHelper();
//		Properties props = new Properties();
//		props.setProperty("dialect", "mysql");
//		props.setProperty("offsetAsPageNum", "false");
//		props.setProperty("rowBoundsWithCount", "false");
//		props.setProperty("pageSizeZero", "false");
//		
//		props.setProperty("reasonable", "false");
//		props.setProperty("supportMethodsArguments", "false");
//		props.setProperty("returnPageInfo", "none");
//		//props.setProperty("params", "count=countSql");
//		pageHelper.setProperties(props);
//		// 添加插件
//		sessionFactory.setPlugins(new Interceptor[] { pageHelper });
//
//		// sessionFactory.setTypeAliasesPackage("xxx.mybatis");
//		return sessionFactory.getObject();
//	}
//
//	@Bean(name = "mySqlSessionTemplate")
//	public SqlSessionTemplate amonSqlSessionTemplate(
//			@Qualifier("mySqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
//		return new SqlSessionTemplate(sqlSessionFactory);
//	}
//}
