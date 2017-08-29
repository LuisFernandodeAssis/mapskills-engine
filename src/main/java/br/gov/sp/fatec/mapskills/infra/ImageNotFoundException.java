/*
 * @(#)ImageNotFoundException.java 1.0 04/06/2017
 *
 * Copyright (c) 2017, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.infra;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.gov.sp.fatec.mapskills.domain.MapSkillsException;
/**
 * 
 * A classe {@link ImageNotFoundException} e lancada
 * quando uma requisicao de uma imagem no servidor
 * nao e encontrada.
 *
 * @author Marcelo
 * @version 1.0 04/06/2017
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ImageNotFoundException extends MapSkillsException {

	/****/
	private static final long serialVersionUID = 1L;
	
	public ImageNotFoundException(final String message, final Throwable arg1) {
		super(message, arg1);
	}

}
