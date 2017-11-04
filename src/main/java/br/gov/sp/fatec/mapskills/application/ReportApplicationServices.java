/*
 * @(#)DashboardApplicationServices.java 1.0 1 17/09/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.application;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import br.gov.sp.fatec.mapskills.report.ReportFacade;
import br.gov.sp.fatec.mapskills.report.entity.CourseStudentsIndicator;
import br.gov.sp.fatec.mapskills.report.entity.GlobalStudentsIndicator;
import br.gov.sp.fatec.mapskills.report.entity.InstitutionStudentsIndicator;
import lombok.AllArgsConstructor;

/**
 * A classe {@link ReportApplicationServices}
 *
 * @author Marcelo
 * @version 1.0 17/09/2017
 */
@ApplicationServices
@AllArgsConstructor
public class ReportApplicationServices {
	
	private final ReportFacade facade;
	
	@PreAuthorize("isAdmin()")
	public List<GlobalStudentsIndicator> getGlobalStudentIndicators(final String yearSemester) {
		return facade.getGlobalStudentIndicators(yearSemester);
	}
	
	@PreAuthorize("isAdmin()")
	public List<InstitutionStudentsIndicator> getInstitutionStudentIndicators(final String yearSemester, final String level) {
		return facade.getInstitutionStudentIndicators(yearSemester, level);
	}

	@PreAuthorize("isAdmin()")
	public List<CourseStudentsIndicator> getCourseStudentsIndicators(String institutionCode, String yearSemester) {
		return facade.getCourseStudentsIndicators(institutionCode, yearSemester);
	}
}