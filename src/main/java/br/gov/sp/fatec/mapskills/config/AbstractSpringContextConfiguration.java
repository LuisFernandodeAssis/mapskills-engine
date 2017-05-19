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
@Import({SerializersConfig.class, SecurityConfig.class})
@ComponentScan(basePackages = {"br.gov.sp.fatec.mapskills.domain.user",
		"br.gov.sp.fatec.mapskills.domain.institution",
		"br.gov.sp.fatec.mapskills.domain.skill",
		"br.gov.sp.fatec.mapskills.domain.theme",
		"br.gov.sp.fatec.mapskills.domain.scene",
		"br.gov.sp.fatec.mapskills.domain.answerevent",
		"br.gov.sp.fatec.mapskills.utils",
		"br.gov.sp.fatec.mapskills.restapi.serializer",
		"br.gov.sp.fatec.mapskills.authentication",
		"br.gov.sp.fatec.mapskills.authentication.jwt",
		"br.gov.sp.fatec.mapskills.restapi.wrapper.report"})
@EnableJpaRepositories(basePackages = {"br.gov.sp.fatec.mapskills.domain.user",
		"br.gov.sp.fatec.mapskills.domain.institution",
		"br.gov.sp.fatec.mapskills.domain.skill",
		"br.gov.sp.fatec.mapskills.domain.scene",
		"br.gov.sp.fatec.mapskills.domain.answerevent",
		"br.gov.sp.fatec.mapskills.domain.theme",
		"br.gov.sp.fatec.mapskills.restapi.wrapper.report"})
public abstract class AbstractSpringContextConfiguration {
}
