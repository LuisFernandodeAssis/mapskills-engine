/*
 * @(#)InstitutionNotFoundException.java 1.0 07/01/2017
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.domain.institution;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.gov.sp.fatec.mapskills.domain.MapSkillsException;
/**
 * A classe <code>InstitutionNotFoundException</code> eh lancada quando
 * nenhuma instituicao eh encontrada na base de dados da aplicacao.
 * 
 * @author Marcelo
 *
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class InstitutionNotFoundException extends MapSkillsException {

	private static final long serialVersionUID = 1L;
	
	public InstitutionNotFoundException(final long institutionId) {
		super("instituicao de ID " + institutionId + " nao encontrado.");
	}

}
