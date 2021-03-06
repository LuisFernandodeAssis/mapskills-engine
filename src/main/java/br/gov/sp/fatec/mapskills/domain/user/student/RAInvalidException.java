/*
 * @(#)RAInvalidException.java 1.0 10/11/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved. Fatec Jessen Vidal
 * proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.domain.user.student;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.gov.sp.fatec.mapskills.application.MapSkillsException;
/**
 * A classe <code>RAInvalidException</code> eh lancada caso 
 * encontre uma mal forma��o no RA informado.
 * 
 * @author Marcelo In�cio
 *
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RAInvalidException extends MapSkillsException {

	private static final long serialVersionUID = 1L;	
	private final String ra;
	
	public RAInvalidException(final String ra) {
		super(String.format("O RA %s se encontra inv�lido!", ra));
		this.ra = ra;
	}
	
	public String ra() {
		return ra;
	}

}
