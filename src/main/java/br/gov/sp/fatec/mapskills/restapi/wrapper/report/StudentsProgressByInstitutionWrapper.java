/*
 * @(#)StudentsProgressByCourseWrapper.java 1.0 01/03/2017
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi.wrapper.report;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.gov.sp.fatec.mapskills.restapi.serializer.StudentsProgressByInstitutionSerializer;
/**
 * 
 * A classe {@link StudentsProgressByInstitutionWrapper} contem
 * a lista de array de objetos que sao os resultados da consulta
 * da VIEW <i>INSTITUTION_STUDENTS_PROGRESS_VIEW</i> que retorna
 * a quantidade de alunos que finalizaram e n�o finalizaram o jogo
 * de todos os cursos de uma instituicao.
 *
 * @author Marcelo
 * @version 1.0 01/03/2017
 */
@JsonSerialize(using = StudentsProgressByInstitutionSerializer.class)
public class StudentsProgressByInstitutionWrapper {
	
	private final List<Object[]> resultSet = new ArrayList<>();
	
	public StudentsProgressByInstitutionWrapper(final List<Object[]> resultSet) {
		this.resultSet.clear();
		this.resultSet.addAll(resultSet);
	}
	
	public List<Object[]> getResultSet() {
		return Collections.unmodifiableList(resultSet);
	}

}
