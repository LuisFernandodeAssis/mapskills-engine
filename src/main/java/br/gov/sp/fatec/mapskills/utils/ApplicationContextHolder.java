/*
 * @(#)ApplicationContextHolder.java 1.0 01/11/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
/**
 * 
 * A classe {@link ApplicationContextHolder} utilizada para recuperar objetos
 * que estao instanciados no cluster de objetos do spring.
 * Solucao para resolver problemas de dependencia.
 *
 * @author Marcelo
 * @version 1.0 01/11/2016
 */
@Component
public class ApplicationContextHolder implements ApplicationContextAware {

	private static ApplicationContext context;
	
	@Override
	public void setApplicationContext(final ApplicationContext ctx) {
		synchronized (ApplicationContextHolder.class) {
	        context = ctx;
	    }
	}

	public static <T> T getBean(final String name, final Class<T> clazz) {
		return context.getBean(name, clazz);
	}
}