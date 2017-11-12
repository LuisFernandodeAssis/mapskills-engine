/*
 * @(#)MapSkillsApplication.java 1.0 1 06/11/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * A classe {@link MapSkillsApplication} e responsavel
 * pela inicializa a aplicacao spring boot.
 *
 * @author Marcelo
 * @version 1.0 06/11/2017
 */
@SpringBootApplication
public class MapSkillsApplication {
	public static void main(final String[] args) {
		SpringApplication.run(MapSkillsApplication.class, args);
	}
}