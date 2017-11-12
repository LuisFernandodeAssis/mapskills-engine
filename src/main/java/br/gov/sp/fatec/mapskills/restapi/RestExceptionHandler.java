/*
 * @(#)RestExceptionHandler.java 1.0 1 02/11/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.restapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.gov.sp.fatec.mapskills.domain.MapSkillsException;

/**
 * A classe {@link RestExceptionHandler} e responsavel
 * por interceptar uma excecao lancada pela aplicacao,
 * retornando-a formatada no corpo da resposta.
 *
 * @author Marcelo
 * @version 1.0 28/10/2017
 */
@RestControllerAdvice
public class RestExceptionHandler {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@ExceptionHandler(MapSkillsException.class)
    protected ResponseEntity<ErrorResponse> mapSkillsExceptionHandle(final MapSkillsException ex) {
		final ResponseStatus status = ex.getClass().getAnnotation(ResponseStatus.class);
        final ErrorResponse responseBody = new ErrorResponse(status.value(), ex.getMessage());
        logger.error("Excecao: ", ex);
		return new ResponseEntity<>(responseBody, status.value());
    }
	
	@ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<ErrorResponse> accessDeniedExceptionHandle(final AccessDeniedException ex) {
        final ErrorResponse responseBody = new ErrorResponse(HttpStatus.FORBIDDEN, ex.getMessage());
        logger.error("ACCESS DENIED EXCEPTION HANDLE", ex);
		return new ResponseEntity<>(responseBody, HttpStatus.FORBIDDEN);
    }
	
	@ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> internalServerErrorHandle(final Exception ex) {
        final ErrorResponse responseBody = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        logger.error("Aconteceu um problema não tratado pelo dominio", ex);
		return new ResponseEntity<>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}