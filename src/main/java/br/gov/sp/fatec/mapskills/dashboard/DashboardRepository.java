/*
 * @(#)DashboardRepository.java 1.0 1 17/09/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.dashboard;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * A classe {@link DashboardRepository}
 *
 * @author Marcelo
 * @version 1.0 17/09/2017
 */
public interface DashboardRepository extends PagingAndSortingRepository<StudentIndicator, Long> {
	
	/**
	 * Retorna todos os resultados da quantidade de alunos que
	 * finalizaram e não finalizaram sumarizado de maneira global.
	 * Ou seja a contabilização dos alunos ETEC e FATEC
	 * 
	 * @param yearSemester Ano/Semestre a ser pesquisada
	 * @return Os indicadores global para o ano/semestre pesquisado.
	 */
	//@Query(value = "SELECT * FROM ADMIN_GLOBAL_STUDENTS_PROGRESS_VIEW RESULT WHERE RESULT.YEAR_SEMESTER = ?1", nativeQuery = true)
	@Query("SELECT result FROM GlobalStudentsIndicator result WHERE result.yearSemester = ?1")
	List<GlobalStudentsIndicator> findGlobalStudentsIndicator(final String yearSemester);
	
	/**
	 * Retorna a quantidade de alunos que finalizaram e nao finalizaram
	 * o jogo por nivel de instituicao (superior e tecnico) de um periodo
	 * (concatenacao do ano mais o semestre ex.: primeiro semestre do de ano 2017 
	 * ficaria 171) com as sequintes colunas:
	 * YEAR_SEMESTER, INS_CODE, LEVEL, NOT_FINALIZED, FINALIZED, TOTAL 
	 * 
	 * @param yearSemester Ano/Semestre a ser pesquisada.
	 * @param level Grau da instituicao a ser pesquisada.
	 * @return Os indicadores das instituicoes recuperadas.
	 */
	//@Query(value="SELECT * FROM ADMIN_LEVEL_STUDENTS_PROGRESS_VIEW RESULT WHERE RESULT.LEVEL = ?1 AND RESULT.YEAR_SEMESTER = ?2", nativeQuery = true)
	@Query("SELECT result FROM InstitutionStudentsIndicator result WHERE result.yearSemester = ?1 AND result.level = ?2")
	List<InstitutionStudentsIndicator> findInstitutionStudentsIndicator(final String yearSemester, final String level);

}