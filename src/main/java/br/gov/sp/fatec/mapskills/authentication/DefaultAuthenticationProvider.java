/*
 * @(#)DefaultAuthenticationProvider.java 1.0 27/01/2017
 *
 * Copyright (c) 2017, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.authentication;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import br.gov.sp.fatec.mapskills.application.UserApplicationServices;
import br.gov.sp.fatec.mapskills.domain.user.User;
import br.gov.sp.fatec.mapskills.domain.user.UserNotFoundException;
import lombok.AllArgsConstructor;

/**
 * A classe {@link DefaultAuthenticationProvider} responsavel
 * por realizar as autenticacoes dos usuarios na aplicacao.
 *
 * @author Marcelo
 * @version 1.0 27/01/2017
 */
@Component
@AllArgsConstructor
public class DefaultAuthenticationProvider implements AuthenticationProvider {
		
	private final UserApplicationServices userService;

	@Override
	public Authentication authenticate(final Authentication authentication) {
		try {
			final User user = userService.findUserByUsername(authentication.getName());			
			userService.authenticate(user, authentication.getCredentials().toString());	
			return new PreAuthenticatedAuthentication(user);		
		} catch (final UserNotFoundException exception) {
			throw new BadCredentialsException("username/password invalid", exception);
		}		
	}

	@Override
	public boolean supports(final Class<?> arg0) {
		return true;
	}
}