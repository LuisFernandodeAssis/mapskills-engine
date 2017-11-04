/*
 * @(#)StudentsProgressGlobalWrapper.java 1.0 01/03/2017
 *
 * Copyright (c) 2017, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi.wrapper;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.gov.sp.fatec.mapskills.report.entity.GlobalStudentsIndicator;
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
	
	private List<GlobalStudentsIndicator> indicatorResults = new LinkedList<>();
	
	public StudentsProgressGlobalWrapper(final List<GlobalStudentsIndicator> indicatorResults) {
		this.indicatorResults.addAll(indicatorResults);
	}
	
	public List<GlobalStudentsIndicator> getIndicatorResults() {
		return Collections.unmodifiableList(indicatorResults);
	}
}