/*
 * @(#)AuthenticationEvent.java 1.0 27/01/2017
 *
 * Copyright (c) 2017, Fatec Jessen Vidal. All rights reserved. Fatec Jessen Vidal
 * proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.authentication;

import java.security.Principal;

import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;

import br.gov.sp.fatec.mapskills.domain.user.User;

public class AuthenticationEvent extends AuthenticationSuccessEvent {

	private static final long serialVersionUID = 1L;
	private final transient HttpServletResponse response;
	
	public AuthenticationEvent(final HttpServletResponse response, final Authentication authentication) {
        super(authentication);
        this.response = response;
    }
	
	@Override
    public PreAuthenticatedAuthentication getAuthentication() {
        return (PreAuthenticatedAuthentication) super.getAuthentication();
    }

    public String getUsername() {
        return getUser().getName();
    }

    public HttpServletResponse getResponse() {
        return response;
    }
    
    public User getUserDomain() {
        return getAuthentication().getPrincipal();
    }

    private Principal getUser() {
        return getAuthentication().getPrincipal();
    }  
}