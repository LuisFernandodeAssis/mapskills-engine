/*
 * @(#)ResponseWriterAuthenticationListener.java 1.0 1 05/02/2016
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.authentication;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.gov.sp.fatec.mapskills.restapi.RestException;
import br.gov.sp.fatec.mapskills.restapi.wrapper.SingleWrapper;

/**
 * A classe {@link ResponseWriterAuthenticationListener} eh responsavel por
 * retornar os dados do usuario logado com sucesso na aplicacao. Na pratica, os
 * dados do usuario sao escritos em formato JSON no objeto
 * <code>OutputStream</code> do objeto <code>HttpServletResponse</code>.
 *
 * @author Roberto Perillo
 * @version 1.0 05/02/2016
 */
@Component
public class ResponseWriterAuthenticationListener implements AuthenticationListener {

	/** {@inheritDoc} */
	@Override
	public void onAuthenticationSuccess(final AuthenticationEvent event) {	
		try {
			final String json = new ObjectMapper().writeValueAsString(new SingleWrapper<>(event.getUserDomain()));
			event.getResponse().setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "http://localhost:3000");
			event.getResponse().setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
			event.getResponse().getOutputStream().print(json);
		} catch (final IOException exception) {
			throw new RestException("Problema ao serializar dados do usuario", exception);
		}
	}
}