/*
 * @(#)MySqlConfig.java 1.0 1 04/11/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.orm.jpa.JpaBaseConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

/**
 * A classe {@link DataBaseConfig} contem as configuracoes
 * do banco de dados <b>MySql</b>.
 *
 * @author Marcelo
 * @version 1.0 04/11/2017
 */
@Configuration
@EnableTransactionManagement
@EntityScan(basePackages = {"br.gov.sp.fatec.mapskills.*"})
@EnableJpaRepositories(basePackages = "br.gov.sp.fatec.mapskills.*")
public class DataBaseConfig extends JpaBaseConfiguration {

	protected DataBaseConfig(final DataSource dataSource, final JpaProperties properties,
			final ObjectProvider<JtaTransactionManager> jtaTransactionManager,
			final ObjectProvider<TransactionManagerCustomizers> transactionManagerCustomizers) {
		super(dataSource, properties, jtaTransactionManager, transactionManagerCustomizers);
	}

	@Override
	protected AbstractJpaVendorAdapter createJpaVendorAdapter() {
		return new EclipseLinkJpaVendorAdapter();
	}

	@Override
	protected Map<String, Object> getVendorProperties() {
		final Map<String, Object> vendorProperties = new HashMap<>();
		vendorProperties.put("eclipselink.cache.shared.default", "false");
        vendorProperties.put("eclipselink.cache-usage", "CheckCacheThenDatabase");
		vendorProperties.put("eclipselink.jdbc.cache-statements", "false");
		vendorProperties.put("eclipselink.weaving", "static");
		vendorProperties.put("eclipselink.query-results-cache", "true");
		return vendorProperties;
	}
}