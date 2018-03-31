/*
 * @(#)DefaultAuthenticationFailureHandler.java 1.0 27/01/2017
 *
 * Copyright (c) 2017, Fatec Jessen Vidal. All rights reserved. Fatec Jessen Vidal
 * proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.gov.sp.fatec.mapskills.restapi.ErrorResponse;

/**
 * A classe <code>DefaultAuthenticationFailureHandler</code> representa a
 * implementação de <code>AuthenticationFailureHandler</code> que trata
 * possíveis problemas de autenticação na aplicação.
 *
 * @author Roberto Perillo
 * @version 1.0 09/12/2015
 */
@Component
public class DefaultAuthenticationFailureHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException exception) throws IOException, ServletException {
		//response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getOutputStream().print(new ObjectMapper().writeValueAsString(new ErrorResponse(HttpStatus.UNAUTHORIZED, exception.getMessage())));
	}
}