/*
 * @(#)StudentResultWrapper.java 1.0 1 11/12/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.restapi.wrapper;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.gov.sp.fatec.mapskills.restapi.serializer.StudentResultSerializer;
import br.gov.sp.fatec.mapskills.studentresult.StudentResult;

/**
 * A classe {@link StudentResultWrapper}
 *
 * @author Marcelo
 * @version 1.0 11/12/2017
 */
@JsonSerialize(using = StudentResultSerializer.class)
public class StudentResultWrapper extends SingleWrapper<StudentResult> {

	public StudentResultWrapper(final StudentResult object) {
		super(object);
	}
}