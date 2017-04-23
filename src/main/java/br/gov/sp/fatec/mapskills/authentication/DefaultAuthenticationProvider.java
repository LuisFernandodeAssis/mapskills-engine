/*
 * @(#)DefaultAuthenticationProvider.java 1.0 27/01/2017
 *
 * Copyright (c) 2017, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import br.gov.sp.fatec.mapskills.domain.user.User;
import br.gov.sp.fatec.mapskills.domain.user.UserService;
/**
 * 
 * A classe {@link DefaultAuthenticationProvider} responsavel
 * por realizar as autenticacoes dos usuarios na aplicacao.
 *
 * @author Marcelo
 * @version 1.0 27/01/2017
 */
@Component
public class DefaultAuthenticationProvider implements AuthenticationProvider {
		
	private UserService userService;

	@Override
	public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
		
		final User user = userService.findByUsername(authentication.getName());
		
		userService.authenticate(user, authentication.getCredentials().toString());
	
		return new PreAuthenticatedAuthentication(user);
		
	}

	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}
	
	@Autowired
	public void setUserService(final UserService userService) {
		this.userService = userService;
	}
	
}
