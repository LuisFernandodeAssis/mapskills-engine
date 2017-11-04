/*
 * @(#)UserNotFoundException.java 1.0 10/11/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.domain.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.gov.sp.fatec.mapskills.domain.MapSkillsException;
/**
 * 
 * A classe {@link UserNotFoundException} e lançada quando falha
 * ao tentar uma autenticacao por um usuario.
 *
 * @author Marcelo
 * @version 1.0 10/11/2016
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UserNotFoundException extends MapSkillsException {

	private static final long serialVersionUID = 1L;
	
	public UserNotFoundException(final String username) {
		super(username + " não possui permissão");
	}	
}