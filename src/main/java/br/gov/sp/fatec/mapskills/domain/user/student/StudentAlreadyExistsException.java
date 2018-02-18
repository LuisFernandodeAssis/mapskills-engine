/*
 * @(#)StudentAlreadyExistsException.java 1.0 1 18/02/2018
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.domain.user.student;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.gov.sp.fatec.mapskills.domain.MapSkillsException;

/**
 * A classe {@link StudentAlreadyExistsException}
 *
 * @author Marcelo
 * @version 1.0 18/02/2018
 */
@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class StudentAlreadyExistsException extends MapSkillsException {

	private static final long serialVersionUID = 1L;

	public StudentAlreadyExistsException(final String message) {
		super(message);
	}
}