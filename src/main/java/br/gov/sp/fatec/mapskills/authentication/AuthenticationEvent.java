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

/**
 * A classe <code>AuthenticationEvent</code> representa o evento de
 * autenticação realizada com sucesso na aplicação. Pelo fato de que os
 * <i>listeners</i> desse evento podem lidar com a requisição HTTP, instâncias
 * dessa classe também tem os objetos que representam a requisição e a resposta
 * HTTP.
 *
 * @author Roberto Perillo
 * @version 1.0 05/02/2016
 */
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