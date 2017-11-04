/*
 * @(#)JwtSignatureVerifier.java 1.0 27/01/2017
 *
 * Copyright (c) 2017, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.authentication.jwt;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Component;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.SignedJWT;

@Component
public class JwtSignatureVerifier implements JwtVerifier {
	
	private static final Logger LOGGER = Logger.getLogger(JwtSignatureVerifier.class.getName());
	private final JWSVerifier verifier;
	
	public JwtSignatureVerifier(final String secret) throws JOSEException {
        super();
        this.verifier = new MACVerifier(secret);
    }

	@Override
	public void verify(final JWT jwt) {
		final SignedJWT signedJwt = (SignedJWT) jwt;
        try {
            if (!signedJwt.verify(verifier)) {
                throw new JwtTokenException("Invalid signature.");
            }
        } catch (final JOSEException exception) {
        	LOGGER.log(Level.SEVERE, exception.getMessage(), exception);
            throw new JwtTokenException("The JWT signature could not be verified.");
        }        
	}
}