/*
 * @(#)StudentInvalidException.java 1.0 11/02/2017
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved. Fatec Jessen Vidal
 * proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.domain.user.student;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.gov.sp.fatec.mapskills.domain.MapSkillsException;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class StudentInvalidException extends MapSkillsException {

	private static final long serialVersionUID = 1L;
	
	public StudentInvalidException(final Throwable arg1) {
		super("Problema encontrado nos dados do aluno", arg1);
	}

}