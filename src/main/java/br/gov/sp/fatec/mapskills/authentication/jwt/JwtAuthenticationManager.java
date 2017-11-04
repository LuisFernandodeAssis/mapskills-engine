/*
 * @(#)JwtAuthenticationManager.java 1.0 28/01/2017
 *
 * Copyright (c) 2017, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.authentication.jwt;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;

import br.gov.sp.fatec.mapskills.authentication.PreAuthenticatedAuthentication;
import br.gov.sp.fatec.mapskills.domain.user.User;
import br.gov.sp.fatec.mapskills.domain.user.UserRepository;

@Component
public class JwtAuthenticationManager implements AuthenticationManager {
	
	private static final Logger LOGGER = Logger.getLogger(JwtAuthenticationManager.class.getName());
	
	private final UserRepository repository;
    private final List<JwtVerifier> verifiersList = new ArrayList<>();
    
    @Autowired
    public JwtAuthenticationManager(final UserRepository repository) {
        super();
        this.repository = repository;
    }

	@Override
	public Authentication authenticate(final Authentication auth) {
		final String token = String.valueOf(auth.getPrincipal()).substring(6).trim();
        final JWT jwt;
        final JWTClaimsSet claims;

        try {
            jwt = JWTParser.parse(token);
            claims = jwt.getJWTClaimsSet();
        } catch (final ParseException exception) {
            throw new JwtTokenException("The given JWT could not be parsed.");
        }

        for (final JwtVerifier verifier : verifiersList) {
            verifier.verify(jwt);
        }

        String username = null;
		try {
			username = claims.getStringClaim("username");
		} catch (final ParseException e) {
			LOGGER.warning(e.getMessage());
			throw new JwtTokenException("The user from jwt not found.");
		}
        
        final User user = repository.findByUsername(username);
		
        return new PreAuthenticatedAuthentication(user);
	}
	
	@Resource
    public void setVerifiersList(final List<JwtVerifier> verifiersList) {
        this.verifiersList.addAll(verifiersList);
    }
}