/*
 * @(#)UnauthorizedAuthenticationException.java 1.0 1 03/11/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.authentication;

import org.springframework.security.core.AuthenticationException;

/**
 * A classe {@link UnauthorizedAuthenticationException}
 *
 * @author Roberto Perillo
 * @author Marcelo Inacio
 * @version 1.0 06/03/2017
 */
public class UnauthorizedAuthenticationException extends AuthenticationException {

	private static final long serialVersionUID = 1L;

	public UnauthorizedAuthenticationException(final String msg) {
		super(msg);
	}
}