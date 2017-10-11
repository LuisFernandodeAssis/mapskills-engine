/*
 * @(#)DashboardController.java 1.0 1 17/09/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.restapi;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.gov.sp.fatec.mapskills.application.DashboardApplicationServices;
import br.gov.sp.fatec.mapskills.dashboard.GlobalStudentsIndicator;
import br.gov.sp.fatec.mapskills.dashboard.InstitutionStudentsIndicator;
import br.gov.sp.fatec.mapskills.restapi.wrapper.StudentsProgressGlobalWrapper;
import br.gov.sp.fatec.mapskills.restapi.wrapper.StudentsProgressLevelWrapper;
import lombok.AllArgsConstructor;

/**
 * A classe {@link DashboardController} expoe servicos
 * para acesso aos indicadores gerados pela aplicacao.
 *
 * @author Marcelo
 * @version 1.0 17/09/2017
 */
@RestController
@AllArgsConstructor
public class DashboardController {
	
	private final DashboardApplicationServices applicationServices;
	
	/**
	 * 
	 * Expoe endpoint para
	 */
	@GetMapping(value = "/dashboard/global")
	public StudentsProgressGlobalWrapper getGlobalStudentsProgress(
			@RequestParam(value = "yearSemester", required = false) final String yearSemester) {
		
		final List<GlobalStudentsIndicator> indicatorResult = applicationServices.getGlobalStudentIndicators(yearSemester);
		return new StudentsProgressGlobalWrapper(indicatorResult);
	}
	
	/**
	 * 
	 * Expoe endpoint para
	 */
	//TODO MUDAR CHAMADA DA URI NO FRONT
	@GetMapping(value = "/dashboard/institution")
	public StudentsProgressLevelWrapper getLevelStudentsProgress(
			@RequestParam(value = "yearSemester", required = false) final String yearSemester,
			@RequestParam(value = "level", required = true) final String level) {
		
		final List<InstitutionStudentsIndicator> indicatorResult = applicationServices.getInstitutionStudentIndicators(yearSemester, level);
		
		return new StudentsProgressLevelWrapper(indicatorResult);
		
	}
}