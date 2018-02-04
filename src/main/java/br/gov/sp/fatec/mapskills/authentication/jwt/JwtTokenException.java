/*
 * @(#)JwtTokenException.java 1.0 27/01/2017
 *
 * Copyright (c) 2017, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.authentication.jwt;

import org.springframework.security.core.AuthenticationException;

/**
 * A classe <code>JwtTokenException</code> representa a situacao onde ocorreu
 * algum problema ao verificar um JWT.
 *
 * @author Roberto Perillo
 * @version 1.0 04/10/2016
 */
public class JwtTokenException extends AuthenticationException {
	
	private static final long serialVersionUID = 1L;
	
	public JwtTokenException(final String message) {
        super(message);
    }

    public JwtTokenException(final String message, final Throwable cause) {
        super(message, cause);
    }
}