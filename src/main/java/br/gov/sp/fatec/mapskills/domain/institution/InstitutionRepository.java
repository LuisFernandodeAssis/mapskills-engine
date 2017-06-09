/*
 * @(#)InstitutionRepository.java 1.0 01/11/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.domain.institution;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * 
 * A classe {@link InstitutionRepository} e responsavel por realizar as
 * transacionalidades referentes as instituticoes.
 *
 * @author Marcelo
 * @version 1.0 01/11/2016
 */
public interface InstitutionRepository extends CrudRepository<Institution, Long> {
	
	public Institution findById(final long id);
	public Institution findByCode(final String code);
	public Institution findByCnpj(final String cnpj);
	
	@Query("SELECT ins.gameThemeId FROM Institution ins WHERE ins.code = ?1")
	public Long findGameThemeIdByCode(final String code);
	
	/**
	 * retorna todos os resultados da quantidade de alunos que
	 * finalizaram e n�o finalizaram de uma maneira geral por curso de uma
	 * determinada instituicao.
	 * colunas: ANO_SEMESTRE, INS_CODE, CRS_CODE, CURSO, NAO_FINALIZADOS e FINALIZADOS
	 * @param institutionCode
	 * @param yearSemester
	 * @return
	 */
	@Query(value="SELECT * FROM INSTITUTION_STUDENTS_PROGRESS_VIEW RESULT "
			+ "WHERE RESULT.INS_CODE = ?1 AND RESULT.ANO_SEMESTRE = ?2", nativeQuery = true)
	public List<Object[]> findStudentsProgressByInstitution(final String institutionCode, final String yearSemester);
	
	/**
	 * retorna todos os resultados da quantidade de alunos que
	 * finalizaram e n�o finalizaram sumarizado de maneira global.
	 * Ou seja a contabiliza��o dos alunos ETEC e FATEC
	 * @param yearSemester
	 * @return
	 */
	@Query(value="SELECT * FROM ADMIN_GLOBAL_STUDENTS_PROGRESS_VIEW RESULT "
			+ "WHERE RESULT.YEAR_SEMESTER = ?1", nativeQuery = true)
	public List<Object[]> findGlobalStudentsProgress(final String yearSemester);
	
	/**
	 * retorna a quantidade de alunos que finalizaram e nao finalizaram
	 * o jogo por nivel de instituicao (superior e tecnico) de um periodo
	 * (concatenacao do ano mais o semestre ex.: primeiro semestre do de ano 2017 
	 * ficaria 171) com as sequintes colunas:
	 * YEAR_SEMESTER, INS_CODE, LEVEL, NOT_FINALIZED, FINALIZED, TOTAL 
	 * @param level
	 * @param yearSemester
	 * @return
	 */
	@Query(value="SELECT * FROM ADMIN_LEVEL_STUDENTS_PROGRESS_VIEW RESULT "
			+ "WHERE RESULT.LEVEL = ?1 AND RESULT.YEAR_SEMESTER = ?2", nativeQuery = true)
	public List<Object[]> findLevelStudentsProgress(final String level, final String yearSemester);

}
