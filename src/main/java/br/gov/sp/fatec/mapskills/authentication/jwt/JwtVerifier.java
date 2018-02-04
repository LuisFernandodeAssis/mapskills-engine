/*
 * @(#)JwtVerifier.java 1.0 27/01/2017
 *
 * Copyright (c) 2017, Fatec Jessen Vidal. All rights reserved. Fatec Jessen Vidal
 * proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.authentication.jwt;

import com.nimbusds.jwt.JWT;

/**
 * A interface <code>JwtVerifier</code> defini o metodo a ser implementado pelos
 * objetos que efetuam verificacoes em um JWT.
 *
 * @author Roberto Perillo
 * @version 1.0 09/10/2016
 */
@FunctionalInterface
public interface JwtVerifier {
	
	/**
     * Efetua uma verificacao em um JWT.
     *
     * @param JWT
     *            O JWT a ser verificado.
     * @throws JwtTokenException
     *             Caso a verificacao falhe.
     */
	void verify(final JWT jwt);
}