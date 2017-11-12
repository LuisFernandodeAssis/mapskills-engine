/*
 * @(#)DatabaseTestConfig.java 1.0 1 24/08/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.jpa.JpaDialect;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaDialect;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * A classe {@link DatabaseTestConfig}contém a definição de <i>beans</i> do
 * Spring referentes a configuração de base de dados para testes de integração.
 *
 * @author Roberto Perillo
 * @version 1.0 23/09/2015
 */
@Profile("test")
@Configuration
@PropertySource("classpath:/br/gov/sp/fatec/mapskills/config/datasource.properties")
public class DatabaseTestConfig {
	
	@Bean
    @Qualifier("hikariProperties")
    public Properties hikariProperties(@Value("${datasource.url}") final String url,
    		@Value("${datasource.username}") final String username,
            @Value("${datasource.password}") final String password) {
        final Properties properties = new Properties();
        properties.setProperty("url", url);
        properties.setProperty("user", username);
        properties.setProperty("password", password);
        return properties;
    }

    @Bean
    public HikariConfig hikariConfig(@Value("${datasource.class}") final String dataSource,
    		@Qualifier("hikariProperties") final Properties hikariProperties) {
        final HikariConfig config = new HikariConfig();
        config.setDataSourceClassName(dataSource);
        config.setMaximumPoolSize(10);
        config.setIdleTimeout(30000);
        config.setConnectionTestQuery("select 1 from information_schema.system_users");
        config.setDataSourceProperties(hikariProperties);
        return config;
    }

    @Bean
    public DataSource dataSource(final HikariConfig config) {
        return new HikariDataSource(config);
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        final EclipseLinkJpaVendorAdapter adapter = new EclipseLinkJpaVendorAdapter();
        adapter.setShowSql(true);
        adapter.setDatabase(Database.HSQL);
        adapter.setDatabasePlatform("org.eclipse.persistence.platform.database.HSQLPlatform");
        return adapter;
    }

    @Bean
    public JpaDialect dialect() {
        return new EclipseLinkJpaDialect();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(final DataSource dataSource,
    		final JpaVendorAdapter jpaVendorAdapter, final JpaDialect dialect) {
        
    	final LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
        emfb.setJpaVendorAdapter(jpaVendorAdapter);
        emfb.setDataSource(dataSource);
        emfb.setJpaDialect(dialect);
        
        emfb.setPersistenceUnitName("MAPSKILLSPersistenceUnit");
        emfb.setPersistenceXmlLocation("classpath*:/br/gov/sp/fatec/mapskills/config/persistence.xml");
        return emfb;
    }

    @Bean
    public JpaTransactionManager transactionManager(final EntityManagerFactory factory, final DataSource dataSource,
    		final JpaDialect dialect) {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(factory);
        transactionManager.setDataSource(dataSource);
        transactionManager.setJpaDialect(dialect);
        return transactionManager;
    }
}