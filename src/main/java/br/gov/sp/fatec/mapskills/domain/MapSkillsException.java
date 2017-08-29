/*
 * @(#)MapSkillsException.java 1.0 10/11/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.domain;
/**
 * 
 * A classe {@link MapSkillsException} representa a excecao raiz
 * de todas as excecoes que podem acontecer dentro da aplicacao.
 *
 * @author Marcelo
 * @version 1.0 24/08/2017
 */
public abstract class MapSkillsException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public MapSkillsException() {
		super();
	}
	
	public MapSkillsException(final String message, final Throwable arg1) {
		super(message, arg1);
	}
	
	public MapSkillsException(final String message) {
		super(message);
	}

}
