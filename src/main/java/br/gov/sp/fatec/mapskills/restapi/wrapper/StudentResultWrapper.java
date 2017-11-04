/*
 * @(#)StudentResultWrapper.java 1.0 03/01/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi.wrapper;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.gov.sp.fatec.mapskills.report.entity.StudentResult;
import br.gov.sp.fatec.mapskills.restapi.serializer.StudentResultSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 
 * A classe {@link StudentResultWrapper} encapsula
 * o contexto de resultado do aluno para atualizacao
 * atraves da integracao com servico de relatorio.
 *
 * @author Marcelo
 * @version 1.0 03/01/2017
 */
@JsonSerialize(using = StudentResultSerializer.class)
@AllArgsConstructor
@Getter
public class StudentResultWrapper {
	
	private final StudentResult studentResult;	
}