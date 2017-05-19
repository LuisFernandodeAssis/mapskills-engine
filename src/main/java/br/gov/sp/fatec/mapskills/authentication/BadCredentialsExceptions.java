/*
 * @(#)BadCredentialsExceptions.java 1.0 27/01/2017
 *
 * Copyright (c) 2017, Fatec Jessen Vidal. All rights reserved. Fatec Jessen Vidal
 * proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.authentication;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.gov.sp.fatec.mapskills.application.MapSkillsException;

/**
 * A classe <code>BadCredentialsExceptions</code> � chamada quando ocorre uma falha
 * ao tentar realizar login na aplica��o, retornando um status http 401.
 * 
 * @author Marcelo In�cio
 *
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class BadCredentialsExceptions extends MapSkillsException {

	private static final long serialVersionUID = 1L;
	
	public BadCredentialsExceptions(final String msg) {
		super(msg);
	}

}
