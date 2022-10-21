package com.dxc.imda.cam.common.config;

import java.util.HashMap;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionManager;

@Configuration
@EnableJpaRepositories(basePackages = { "com.dxc.imda.cam.tls.dao", "com.dxc.imda.cam.tls.entity" },
	entityManagerFactoryRef = "tlsEntityManager",
	transactionManagerRef = "tlsTransactionManager")
public class AppTlsJdbcConfig {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
    private Environment env;	
	    
	@Bean(name = "tlsDataSource")
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource.tls")
    public DataSource tlsDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getProperty("spring.datasource.tls.driverClassName"));
    	dataSource.setUrl(env.getProperty("spring.datasource.tls.url"));
    	logger.info("url: " + env.getProperty("spring.datasource.tls.url"));
    	dataSource.setUsername(env.getProperty("spring.datasource.tls.username"));
    	dataSource.setPassword(env.getProperty("spring.datasource.tls.password"));
    	return dataSource;
    }

	@Bean(name = "tlsJdbcTemplate")
	@Primary
	public JdbcTemplate tlsJdbcTemplate(@Qualifier("tlsDataSource") DataSource tlsDS) {
		return new JdbcTemplate(tlsDS);
	}
	
	@Bean
	public TransactionManager transactionManager() {
		return new DataSourceTransactionManager(tlsDataSource());
    }
	
	
	@Bean(name = "tlsEntityManager")
    @Primary
    public LocalContainerEntityManagerFactoryBean tlsEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(tlsDataSource());
        em.setPackagesToScan(new String[] { "com.dxc.imda.cam.tls.entity" });

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
        em.setJpaPropertyMap(properties);
        return em;
    }
	
	@Bean
	@Primary    
    public PlatformTransactionManager tlsTransactionManager() { 
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(tlsEntityManager().getObject());
        return transactionManager;
    }
    
}
