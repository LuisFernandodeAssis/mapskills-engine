/*
 * @(#)Result.java 1.0 05/01/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.infra.report;

import lombok.Getter;

/**
 * 
 * A classe {@link Result} representa um resultado de
 * uma competencia utilizado na renderizacao de grafico
 * de radar.
 *
 * @author Marcelo
 * @version 1.0 05/01/2017
 */
@Getter
public class Result {
	
	private final String type;
	private final String description;
	private final long value;
	
	public Result(final String type, final String description, final long value) {
		this.type = type;
		this.description = description;
		this.value = value;
	}
}