/*
 * @(#)JwtSignatureVerifier.java 1.0 27/01/2017
 *
 * Copyright (c) 2017, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.authentication.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.SignedJWT;

/**
 * A classe <code>JwtSignatureVerifier</code> verifica se a assinatura presente
 * em um JWT e valida, de forma a verificar a integridade do JWT.
 *
 * @author Roberto Perillo
 * @version 1.0 10/10/2016
 */
@Component
public class JwtSignatureVerifier implements JwtVerifier {
	
	private final Logger logger = LoggerFactory.getLogger(JwtSignatureVerifier.class);
	private final JWSVerifier verifier;
	
	public JwtSignatureVerifier(final String secret) {
        super();
        try {
            this.verifier = new MACVerifier(secret);
        } catch (final JOSEException exception) {
            throw new IllegalArgumentException("The given secret is invalid.", exception);
        }
    }

	/** {@inheritDoc} */
	@Override
	public void verify(final JWT jwt) {
		final SignedJWT signedJwt = (SignedJWT) jwt;
        try {
            if (!signedJwt.verify(verifier)) {
                throw new JwtTokenException("Invalid signature.");
            }
        } catch (final JOSEException exception) {
        	logger.error("A assinatura JWT não pôde ser verificada.", exception);
            throw new JwtTokenException("The JWT signature could not be verified.", exception);
        }        
	}
}