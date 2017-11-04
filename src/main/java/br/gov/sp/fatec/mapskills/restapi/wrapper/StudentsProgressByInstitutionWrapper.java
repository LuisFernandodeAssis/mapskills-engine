/*
 * @(#)StudentsProgressByCourseWrapper.java 1.0 01/03/2017
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi.wrapper;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.gov.sp.fatec.mapskills.report.entity.CourseStudentsIndicator;
import br.gov.sp.fatec.mapskills.restapi.serializer.StudentsProgressByInstitutionSerializer;
/**
 * 
 * A classe {@link StudentsProgressByInstitutionWrapper} contem
 * a lista de array de objetos que sao os resultados da consulta
 * da VIEW <i>INSTITUTION_STUDENTS_PROGRESS_VIEW</i> que retorna
 * a quantidade de alunos que finalizaram e não finalizaram o jogo
 * de todos os cursos de uma instituicao.
 *
 * @author Marcelo
 * @version 1.0 01/03/2017
 */
@JsonSerialize(using = StudentsProgressByInstitutionSerializer.class)
public class StudentsProgressByInstitutionWrapper {
	
	private final List<CourseStudentsIndicator> indicators = new LinkedList<>();
	
	public StudentsProgressByInstitutionWrapper(final List<CourseStudentsIndicator> indicators) {
		this.indicators.addAll(indicators);
	}
	
	public List<CourseStudentsIndicator> getIndicators() {
		return Collections.unmodifiableList(indicators);
	}
}