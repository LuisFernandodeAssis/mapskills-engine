/*
 * @(#)ReportFilter.java 1.0 18/03/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.dashboard;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import br.gov.sp.fatec.mapskills.domain.institution.InstitutionLevel;
import br.gov.sp.fatec.mapskills.restapi.serializer.ReportFilterDeserializer;
import lombok.Getter;

/**
 * 
 * A classe {@link ReportFilter} representa os filtros
 * possiveis para geracao de relatorio.
 *
 * @author Marcelo
 * @version 1.0 18/03/2017
 */
@Getter
@JsonDeserialize(using = ReportFilterDeserializer.class)
public class ReportFilter {
	
	private final InstitutionLevel levelInstitution;
	private final String institutionCode;
	private final String courseCode;
	private final String startDate;
	private final String endDate;
	
	public ReportFilter(final String levelInstitution, final String institutionCode,
			final String courseCode, final String startDate, final String endDate) {
		
		this.levelInstitution = InstitutionLevel.withLevel(levelInstitution);
		this.institutionCode = institutionCode;
		this.courseCode = courseCode;
		this.startDate = startDate;
		this.endDate = endDate;		
	}	
}