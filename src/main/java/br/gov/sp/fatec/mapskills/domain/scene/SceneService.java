/*
 * @(#)SceneService.java 1.0 09/01/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.domain.scene;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.sp.fatec.mapskills.domain.answerevent.AnswerEvent;
import br.gov.sp.fatec.mapskills.domain.answerevent.AnswerEventRepository;
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
	private final AnswerEventRepository answerRepo;

	/**
	 * @param id
	 * @return uma cena pelo seu id
	 */
	public Scene findById(final long id) {
		return sceneRepo.findOne(id);
	}
	/**
	 * exclui uma cena
	 * @param id
	 */
	public void delete(final long id) {
		sceneRepo.delete(id);
	}
	/**
	 * salva a cena gerando um index valido, para aquele tema
	 * @param scene
	 */
	public Scene save(final Scene scene) {
		if(scene.getIndex() == null || scene.getIndex() < 0) {
			final int index = nextIndex(scene.getGameThemeId());
			scene.setIndex(index);			
		}
		return sceneRepo.save(scene);
	}
	/**
	 * realiza uma atualizacao da ordem das cenas
	 * @param scenes
	 */
	@Transactional
	public void updateIndex(final Collection<Scene> scenes) {
		sceneRepo.save(scenes);
	}
	
	public void save(final Collection<Scene> scenes) {
		for(final Scene scene : scenes) {
			this.save(scene);
		}
	}
	/**
	 * Metodo que recupera todas as cenas de um tema
	 */
	public Collection<Scene> findAllByGameThemeId(final long gameThemeId) {
		return sceneRepo.findAllByGameThemeIdOrderByIndexAsc(gameThemeId);
	}
	
	/**
	 * Metodo que recupera o proximo index da cena valida, de determinado tema.
	 */
	public int nextIndex(final long themeId) {
		return sceneRepo.findNextIndex(themeId);
	}
	
	public void saveAnswer(final AnswerEvent answerContext) {
		answerRepo.save(answerContext);
	}
	/**
	 * Metodo que retorna um resultset da base de dados com os resultados
	 * de uma aluno.
	 */
	public List<Object[]> getResultByStudentId(final long studentId) {
		return answerRepo.findResultViewByStudentId(studentId);
	}
	
	public Collection<Scene> findAllNotAnsweredByStudent(final long studentId) {
		return sceneRepo.findAllNotAnsweredByStudent(studentId);
	}
	
}
