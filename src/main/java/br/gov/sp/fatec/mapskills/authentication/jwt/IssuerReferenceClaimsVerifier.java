/*
 * @(#)IssuerReferenceClaimsVerifier.java 1.0 27/01/2017
 *
 * Copyright (c) 2017, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.authentication.jwt;

import java.text.ParseException;

import org.springframework.stereotype.Component;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;

@Component
public class IssuerReferenceClaimsVerifier implements JwtVerifier {

	/** {@inheritDoc} */
    @Override
    public void verify(final JWT jwt) {
        final JWTClaimsSet claims;
        try {
            claims = jwt.getJWTClaimsSet();
        } catch (final ParseException exception) {
            throw new JwtTokenException("Invalid JWT.");
        }
        final String issuerReference = "https://mapskills.fatec.sp.gov.br";
        final String issuer = claims.getIssuer();
        if (!issuerReference.equals(issuer)) {
            throw new JwtTokenException("Invalid issuer");
        }
    }
}