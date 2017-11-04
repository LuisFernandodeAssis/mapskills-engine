/*
 * @(#)JwtVerifier.java 1.0 27/01/2017
 *
 * Copyright (c) 2017, Fatec Jessen Vidal. All rights reserved. Fatec Jessen Vidal
 * proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.authentication.jwt;

import com.nimbusds.jwt.JWT;

@FunctionalInterface
public interface JwtVerifier {
	
	public void verify(final JWT jwt);

}