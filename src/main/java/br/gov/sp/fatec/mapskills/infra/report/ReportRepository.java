/*
 * @(#)ReportRepository.java 1.0 18/03/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.infra.report;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
/**
 * 
 * A classe {@link ReportRepository} trata das consultas a base de dados
 * para geracao dos relatorios e graficos.
 *
 * @author Marcelo
 * @version 1.0 18/03/2017
 */
public interface ReportRepository extends Repository<ReportDefaultData, Long>, JpaSpecificationExecutor<ReportDefaultData> {
	/**
	 * Recupera o valor das competencias de um aluno.
	 * @param studentId
	 * @return
	 */
	@Query(value="SELECT VIEW.TOTAL FROM SKILL LEFT JOIN RADAR_RESULT_VIEW VIEW ON SKILL.SKI_TYPE = VIEW.SKILL_TYPE "
			+ "WHERE VIEW.USER_ID = ?1 OR VIEW.USER_ID IS NULL ORDER BY SKILL.SKI_ID;", nativeQuery = true)
	List<Object> findAllSkillsByStudentId(final long studentId);

}
