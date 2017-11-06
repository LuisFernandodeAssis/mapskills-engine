/*
 * @(#)DataBaseHsqldbConfig.java 1.0 09/11/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
/**
 * 
 * A classe {@link DataBaseHsqldbConfig} representa as configuracoes
 * do banco de dados HSQLDB, que é um banco de dados em memoria,
 * utilizado para a realizacao de testes.
 *
 * @author Marcelo
 * @version 1.0 09/11/2016
 */
@Configuration
public class DataBaseHsqldbConfig {
	
	@Bean
	public DataSource dataSource() {
		final DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.hsqldb.jdbcDriver");
		dataSource.setUrl("jdbc:hsqldb:mem:mapskills");
		dataSource.setUsername("sa");
		dataSource.setPassword("sa");
		return dataSource;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource());
		entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
		entityManagerFactoryBean.setPackagesToScan("br.gov.sp.fatec.mapskills");
		entityManagerFactoryBean.setJpaProperties(hibProperties());
		return entityManagerFactoryBean;
	}

	@Bean
	public JpaTransactionManager transactionManager() {
		final JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
		return transactionManager;
	}
	
	private Properties hibProperties() {
		final Properties properties = new Properties();
		properties.put("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
		properties.put("hibernate.show_sql", "true");
		properties.put("hibernate.connection.shutdown", "true");
		properties.put("hibernate.hbm2ddl.auto", "update");
		return properties;
	}
}