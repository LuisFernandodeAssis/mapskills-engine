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
	
	Institution findByCode(final String code);
	List<Institution> findAll();
	
	@Query("SELECT ins.gameThemeId FROM Institution ins WHERE ins.code = ?1")
	Long findGameThemeIdByCode(final String code);
	
	/**
	 * retorna todos os resultados da quantidade de alunos que
	 * finalizaram e não finalizaram de uma maneira geral por curso de uma
	 * determinada instituicao.
	 * colunas: ANO_SEMESTRE, INS_CODE, CRS_CODE, CURSO, NAO_FINALIZADOS e FINALIZADOS
	 * 
	 * @param institutionCode
	 * @param yearSemester
	 * @return
	 */
	@Query(value="SELECT * FROM INSTITUTION_STUDENTS_PROGRESS_VIEW RESULT "
			+ "WHERE RESULT.INS_CODE = ?1 AND RESULT.ANO_SEMESTRE = ?2", nativeQuery = true)
	List<Object[]> findStudentsProgressByInstitution(final String institutionCode, final String yearSemester);
	
	
	
	
	
}