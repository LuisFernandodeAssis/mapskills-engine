/*
 * @(#)SceneService.java 1.0 09/01/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.domain.theme;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.sp.fatec.mapskills.domain.studentquestioncontext.StudentQuestionContext;
import br.gov.sp.fatec.mapskills.domain.studentquestioncontext.StudentQuestionContextRepository;
import lombok.AllArgsConstructor;

/**
 * 
 * A classe {@link SceneService}
 *
 * @author Marcelo
 * @version 1.0 09/01/2017
 */
@Service
@AllArgsConstructor
public class SceneService {
	
	private final SceneRepository sceneRepo;

	/**
	 * exclui uma cena
	 * @param id
	 */
	public void delete(final long id) {
		sceneRepo.delete(id);
	}
	
	/**
	 * realiza uma atualizacao da ordem das cenas
	 * @param scenes
	 */
	@Transactional
	public void updateIndex(final List<Scene> scenes) {
		sceneRepo.save(scenes);
	}

	/**
	 * Metodo que retorna um resultset da base de dados com os resultados
	 * de uma aluno.
	 */
	public List<Object[]> getResultByStudentId(final long studentId) {
		return answerRepo.findResultViewByStudentId(studentId);
	}
	
	public List<Scene> findAllNotAnsweredByStudent(final long studentId) {
		return sceneRepo.findAllNotAnsweredByStudent(studentId);
	}
	
}