/*
 * @(#)RestException.java 1.0 1 10/12/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.restapi.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.gov.sp.fatec.mapskills.domain.MapSkillsException;

/**
 * A classe {@link RestException}
 *
 * @author Marcelo
 * @version 1.0 10/12/2017
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class RestException extends MapSkillsException {

	private static final long serialVersionUID = 1L;

	public RestException(final String message, final Throwable cause) {
		super(message, cause);
	}
}