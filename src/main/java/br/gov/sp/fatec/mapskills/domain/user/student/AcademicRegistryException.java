/*
 * @(#)AcademicRegistryException.java 1.0 10/11/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.domain.user.student;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.gov.sp.fatec.mapskills.domain.MapSkillsException;

/**
 * A classe <code>RAInvalidException</code> eh lancada caso 
 * encontre uma mal formação no RA informado.
 * 
 * @author Marcelo Inácio
 * @version 10/11/2016
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AcademicRegistryException extends MapSkillsException {

	private static final long serialVersionUID = 1L;
	
	public AcademicRegistryException(final String ra) {
		super(String.format("O RA %s se encontra inválido!", ra));
	}
	
	public AcademicRegistryException(final String ra, final Throwable cause) {
		super(String.format("O RA %s se encontra inválido!", ra), cause);
	}	
}