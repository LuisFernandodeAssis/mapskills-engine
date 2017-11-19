/*
 * @(#)SingleWrapper.java 1.0 1 18/11/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.restapi.wrapper;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.gov.sp.fatec.mapskills.restapi.serializer.SingleSerializer;
import lombok.Getter;

/**
 * A classe {@link SingleWrapper}
 *
 * @author Marcelo
 * @version 1.0 20/09/2017
 */
@Getter
@JsonSerialize(using = SingleSerializer.class)
public class SingleWrapper<T> {

	private final T object;
	
	public SingleWrapper(final T object) {
		this.object = object;
	}
}