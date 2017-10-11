/*
 * @(#)DashboardApplicationServices.java 1.0 1 17/09/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.application;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import br.gov.sp.fatec.mapskills.dashboard.DashboardServices;
import br.gov.sp.fatec.mapskills.dashboard.GlobalStudentsIndicator;
import br.gov.sp.fatec.mapskills.dashboard.InstitutionStudentsIndicator;
import lombok.AllArgsConstructor;

/**
 * A classe {@link DashboardApplicationServices}
 *
 * @author Marcelo
 * @version 1.0 17/09/2017
 */
@ApplicationServices
@AllArgsConstructor
public class DashboardApplicationServices {
	
	private final DashboardServices services;
	
	@Transactional(readOnly = true)
	@PreAuthorize("isAdmin()")
	public List<GlobalStudentsIndicator> getGlobalStudentIndicators(final String yearSemester) {
		return services.getGlobalStudentIndicators(yearSemester);
	}
	
	@Transactional(readOnly = true)
	@PreAuthorize("isAdmin()")
	public List<InstitutionStudentsIndicator> getInstitutionStudentIndicators(final String yearSemester, final String level) {
		return services.getInstitutionStudentIndicators(yearSemester, level);
	}

}
