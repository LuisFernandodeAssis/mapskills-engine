/*
 * @(#)JwtAuthenticationManager.java 1.0 28/01/2017
 *
 * Copyright (c) 2017, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.authentication.jwt;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

/**
 * A classe <code>JwtAuthenticationManager</code> e o
 * <code>AuthenticationManager</code> responsavel por verificar se um JWT,
 * enviado como <i>header</i> em uma chamada HTTP, e valido e, a partir dai,
 * extrair as informacoes do usuario e retornar um objeto
 * <code>Authentication</code>, que representa o usuario autenticado.
 *
 * @author Roberto Perillo
 * @version 1.0 03/10/2016
 */
@Component
public class JwtAuthenticationManager implements AuthenticationManager {
	
	private final Logger logger = LoggerFactory.getLogger(JwtAuthenticationManager.class);
	
	/** A lista de objetos que efetuam verificacoes no JWT */
	private final List<JwtVerifier> verifiersList = new LinkedList<>();
	private final UserRepository repository;
    
    @Autowired
    public JwtAuthenticationManager(final List<JwtVerifier> verifiersList, final UserRepository repository) {
    	this.verifiersList.addAll(verifiersList);
        this.repository = repository;
    }

	@Override
	public Authentication authenticate(final Authentication auth) {
		final String token = String.valueOf(auth.getPrincipal());
        final JWT jwt;
        final JWTClaimsSet claims;

        try {
            jwt = JWTParser.parse(token);
            claims = jwt.getJWTClaimsSet();
        } catch (final ParseException exception) {
            throw new JwtTokenException("The given JWT could not be parsed.", exception);
        }
        verifiersList.forEach(jwtVerifier -> jwtVerifier.verify(jwt));
		try {
			final String username = claims.getStringClaim("username");
			final User user = repository.findByUsername(username);		
			return new PreAuthenticatedAuthentication(user);
		} catch (final ParseException exception) {
			logger.warn("O usuário do jwt não foi encontrado.", exception);
			throw new JwtTokenException("The user from jwt not found.", exception);
		}        
	}
}