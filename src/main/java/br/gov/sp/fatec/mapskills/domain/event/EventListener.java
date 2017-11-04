/*
 * @(#)EventListener.java 1.0 1 28/10/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.domain.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

/**
 * A classe {@link EventListener} indica que uma
 * classe faz parte de um evento de dominio.
 *
 * @author Marcelo
 * @version 1.0 28/10/2017
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Component()
public @interface EventListener {
	
	/**
	 * The value may indicate a suggestion for a logical component name,
	 * to be turned into a Spring bean in case of an autodetected component.
	 * @return the suggested component name, if any
	 */
	String value() default "";
}