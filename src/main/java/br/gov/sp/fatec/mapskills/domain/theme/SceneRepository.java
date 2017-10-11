/*
 * @(#)SceneRepository.java 1.0 09/01/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.domain.theme;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
/**
 * 
 * A interface {@link SceneRepository}
 *
 * @author Marcelo
 * @version 1.0 09/01/2017
 */
public interface SceneRepository extends CrudRepository<Scene, Long> {
		
	/**
	 * BUSCA TODAS CENAS ORDENADAS E AINDA NÃO RESPONDIDAS POR UM
	 * DETERMINADO ALUNO DE UMA INSTITUIÇÃO QUE TEM UM TEMA ATIVO
	 * 
	 * @param studentId
	 * @return lista de cenas
	 */
	@Query("SELECT scene FROM Scene scene "
			+ "INNER JOIN GameTheme theme ON scene = theme.scenes "
			+ "INNER JOIN Institution ins ON theme = ins.gameTheme "
			+ "INNER JOIN Student stu ON stu.course.institution = ins "
			+ "WHERE scene.id > "
			+ "	(SELECT COALESCE(MAX(ctx.sceneIndex + 1), 0) FROM StudentQuestionContext ctx "
			+ "		INNER JOIN Student stud ON ctx.studentId = stud.id "
			+ "		WHERE ctx.studentId = ?1) "
			+ "AND stu.completed = FALSE "
			+ "AND stu.id = ?1 "
			+ "ORDER BY scene.index ASC")
	List<Scene> findAllNotAnsweredByStudent(final Long studentId);
	
}