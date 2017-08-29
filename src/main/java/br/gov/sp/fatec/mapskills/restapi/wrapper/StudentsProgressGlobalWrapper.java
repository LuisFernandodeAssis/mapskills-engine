/*
 * @(#)StudentsProgressGlobalWrapper.java 1.0 01/03/2017
 *
 * Copyright (c) 2017, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi.wrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.gov.sp.fatec.mapskills.restapi.serializer.StudentsProgressGlobalSerializer;
/**
 * 
 * A classe {@link StudentsProgressGlobalWrapper} encapsula
 * a lista de resultset retornados da consulta da view
 * <i>ADMIN_GLOBAL_STUDENTS_PROGRESS_VIEW</i>
 * colunas: YEAR_SEMESTER, LEVEL, NOT_FINALIZED, FINALIZED e TOTAL.
 *
 * @author Marcelo
 * @version 1.0 01/03/2017
 */
@JsonSerialize(using = StudentsProgressGlobalSerializer.class)
public class StudentsProgressGlobalWrapper {
	
	private final List<Object[]> resultSet = new ArrayList<>();
	
	public StudentsProgressGlobalWrapper(final List<Object[]> resultSet) {
		this.resultSet.clear();
		this.resultSet.addAll(resultSet);
	}
	
	public List<Object[]> getResultSet() {
		return Collections.unmodifiableList(resultSet);
	}

}
