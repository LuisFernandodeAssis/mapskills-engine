/*
 * @(#)AbstractSpringContextConfiguration.java 1.0 28/12/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
/**
 * 
 * A interface {@link AbstractSpringContextConfiguration} contem todas configuracoes
 * necessaria para aplicacao, utilizadas nos testes e em producao.
 *
 * @author Marcelo
 * @version 1.0 28/12/2016
 */
@PropertySource({"classpath:application.properties",
				 "classpath:authentication.properties"})
@ComponentScan(basePackages = "br.gov.sp.fatec.mapskills.*")
@EnableJpaRepositories(basePackages = "br.gov.sp.fatec.mapskills.*")
@Import({SerializersConfig.class, SecurityConfig.class})
public abstract class AbstractSpringContextConfiguration {
}