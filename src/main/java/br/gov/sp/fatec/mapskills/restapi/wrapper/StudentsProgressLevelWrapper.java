/*
 * @(#)StudentsProgressLevelWrapper.java 1.0 01/03/2017
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi.wrapper;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.gov.sp.fatec.mapskills.report.entity.InstitutionStudentsIndicator;
import br.gov.sp.fatec.mapskills.restapi.serializer.StudentsProgressLevelSerializer;
/**
 * 
 * A classe {@link StudentsProgressLevelWrapper} contem o
 * result set que representa a quantidade de alunos que
 * finalzaram e nao finalizaram o jogo por nivel de instituicao
 * (superior e tecnico).
 *
 * @author Marcelo
 * @version 1.0 01/03/2017
 */
@JsonSerialize(using = StudentsProgressLevelSerializer.class)
public class StudentsProgressLevelWrapper {
	
	private final List<InstitutionStudentsIndicator> indicatorResults = new LinkedList<>();
	
	public StudentsProgressLevelWrapper(final List<InstitutionStudentsIndicator> indicatorResults) {
		this.indicatorResults.addAll(indicatorResults);
	}
	
	public List<InstitutionStudentsIndicator> getIndicatorResults() {
		return Collections.unmodifiableList(indicatorResults);
	}
}