/*
 * @(#)DashboardController.java 1.0 1 17/09/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.restapi;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.gov.sp.fatec.mapskills.application.ReportApplicationServices;
import br.gov.sp.fatec.mapskills.report.entity.CourseStudentsIndicator;
import br.gov.sp.fatec.mapskills.report.entity.GlobalStudentsIndicator;
import br.gov.sp.fatec.mapskills.report.entity.InstitutionStudentsIndicator;
import br.gov.sp.fatec.mapskills.restapi.wrapper.StudentsProgressByInstitutionWrapper;
import br.gov.sp.fatec.mapskills.restapi.wrapper.StudentsProgressGlobalWrapper;
import br.gov.sp.fatec.mapskills.restapi.wrapper.StudentsProgressLevelWrapper;
import lombok.AllArgsConstructor;

/**
 * A classe {@link ReportController} expoe servicos
 * para acesso aos indicadores gerados pela aplicacao.
 *
 * @author Marcelo
 * @version 1.0 17/09/2017
 */
@RestController
@AllArgsConstructor
public class ReportController {
	
	private final ReportApplicationServices applicationServices;
	
	/**
	 * 
	 * Expoe endpoint para
	 */
	@GetMapping("/report/global")
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
	@GetMapping("/report/institution")
	public StudentsProgressLevelWrapper getLevelStudentsProgress(
			@RequestParam(value = "yearSemester", required = false) final String yearSemester,
			@RequestParam(value = "level", required = true) final String level) {
		
		final List<InstitutionStudentsIndicator> indicatorResult = applicationServices.getInstitutionStudentIndicators(yearSemester, level);
		return new StudentsProgressLevelWrapper(indicatorResult);		
	}
	
	/**
	 * Endpoint que recupera os indicadores de alunos que finalizaram
	 * e nao finalizaram o jogo.
	 * indicadores >>> numero de alunos.
	 */
	@GetMapping("/report/institution/{code}/progress")
	public StudentsProgressByInstitutionWrapper getStudentsProgress(
			@PathVariable("code") final String institutionCode,
			@RequestParam(value = "yearSemester", required = false) final String yearSemester) {
		final List<CourseStudentsIndicator> courseIndicators = applicationServices.getCourseStudentsIndicators(institutionCode, yearSemester);
		return new StudentsProgressByInstitutionWrapper(courseIndicators);
	}
}