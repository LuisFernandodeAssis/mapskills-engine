/*
 * @(#)SceneService.java 1.0 09/01/2017
 *
 * Copyright (c) 2016, Fatec-Jessen Vidal. All rights reserved.Fatec-Jessen Vidal 
 * proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.domain.scene;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.sp.fatec.mapskills.domain.answerevent.AnswerEvent;
import br.gov.sp.fatec.mapskills.domain.answerevent.AnswerEventRepository;
import br.gov.sp.fatec.mapskills.infrastructure.RepositoryService;

@Service
public class SceneService implements RepositoryService {
	
	@Autowired
	private SceneRepository sceneRepo;
	
	@Autowired
	private AnswerEventRepository answerRepo;

	@Override
	public void deleteAll() {
		sceneRepo.deleteAll();
		answerRepo.deleteAll();
	}
	/**
	 * @param id
	 * @return
	 * 		uma cena pelo seu id
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
	 * salva a cena gerando um index v�lido, para aquele tema
	 * @param scene
	 */
	public Scene save(final Scene scene) {
		if(scene.getIndex() < 0) {
			final int index = nextIndex(scene.getGameThemeId());
			scene.putIndex(index);			
		}
		return sceneRepo.save(scene);
	}
	/**
	 * realiza uma atualiza��o da ordem das cenas
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
	 * M�todo que recupera todas as cenas de um tema
	 * @return
	 */
	public Collection<Scene> findAllByGameThemeId(final long gameThemeId) {
		return sceneRepo.findAllByGameThemeIdOrderByIndexAsc(gameThemeId);
	}
	
	/**
	 * M�todo que recupera o pr�ximo index da cena v�lida, de determinado tema 
	 * @param themeId
	 * @return
	 */
	public int nextIndex(final long themeId) {
		return sceneRepo.findNextIndex(themeId);
	}
	
	public void saveAnswer(final AnswerEvent answerContext) {
		answerRepo.save(answerContext);
	}
	/**
	 * M�todo que retorna um resultset da base de dados com os resultados
	 * de uma aluno.
	 * @param studentId
	 * @return
	 */
	public List<Object[]> getResultByStudentId(final long studentId) {
		return answerRepo.findResultViewByStudentId(studentId);
	}
	
	public Collection<Scene> findAllNotAnsweredByStudent(final long studentId) {
		return sceneRepo.findAllNotAnsweredByStudent(studentId);
	}
	
}
