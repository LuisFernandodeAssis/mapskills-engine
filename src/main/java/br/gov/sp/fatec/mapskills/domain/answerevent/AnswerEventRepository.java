/*
 * @(#)AnswerEventRepository.java 1.0 19/03/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.domain.answerevent;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
/**
 * 
 * A classe {@link AnswerEventRepository}
 *
 * @author Marcelo
 * @version 1.0 19/03/2017
 */
public interface AnswerEventRepository extends CrudRepository<AnswerEvent, Long> {
// Alternativa para trabalha com objeto, ao inv�s de List<Object[]>
	
//	@Query(value = "SELECT new br.gov.sp.fatec.mapskills.restapi.wrapper.Result(s.type, s.description, SUM(a.skillValue))"
//			+ " FROM AnswerEvent a INNER JOIN Skill s"
//			+ " ON a.skillId = s.id WHERE a.studentId = ?1 GROUP BY s.type, s.description ORDER BY s.type ASC")
//	public List<Result> findResultSkillByStudentId(final long studentId);
	/**
	 * query que retorna resultado de um view
	 * criada para renderizacao do grafico de radar
	 */
	@Query(value="SELECT * FROM RADAR_RESULT_VIEW RADAR WHERE RADAR.USER_ID = ?1", nativeQuery = true)
	public List<Object[]> findResultViewByStudentId(final long studentId);
	  
}
