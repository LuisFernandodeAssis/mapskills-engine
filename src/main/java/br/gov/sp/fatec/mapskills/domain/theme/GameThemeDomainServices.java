/*
 * @(#)GameThemeDomainServices.java 1.0 1 02/09/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.domain.theme;

import java.util.List;

import org.springframework.stereotype.Component;

import br.gov.sp.fatec.mapskills.domain.studentquestioncontext.StudentQuestionContext;
import br.gov.sp.fatec.mapskills.infra.FileManagerApplicationServices;
import br.gov.sp.fatec.mapskills.restapi.wrapper.SceneWrapper;
import lombok.AllArgsConstructor;

/**
 * A classe {@link GameThemeDomainServices}
 *
 * @author Marcelo
 * @version 1.0 02/09/2017
 */
@Component
@AllArgsConstructor
public class GameThemeDomainServices {
		
	private final GameThemeRepository repository;
	private final FileManagerApplicationServices fileManagerServices;	
	
	public GameTheme createGameTheme(final GameTheme newGameTheme) {
		return repository.save(newGameTheme);
	}
	
	public Scene createScene(final Long gameThemeId, final SceneWrapper sceneWrapper) {
		fileManagerServices.saveImage(sceneWrapper);
		final GameTheme theme = repository.findOne(gameThemeId);
		final Scene newScene = sceneWrapper.getScene();
		theme.addScene(newScene);
		repository.save(theme);
		return newScene;
	}
	
	public List<GameTheme> getAllGameThemes(final boolean onlyActives) {
		if(onlyActives) {
			return repository.findAllByActive(onlyActives);
		}
		return repository.findAll();
	}
	
	public List<Scene> getAllScenesByThemeId(final Long themeId) {
		final GameTheme gameTheme = repository.findOne(themeId);
		return gameTheme.getScenes();
	}
	
	public void deleteQuestion(final Long themeId, final Long sceneId) {
		final GameTheme gameTheme = repository.findOne(themeId);
		gameTheme.deleteQuestion(sceneId);
		repository.save(gameTheme);
	}
	
	public void deleteScene(final Long themeId, final Long sceneId) {
		final GameTheme gameTheme = repository.findOne(themeId);
		gameTheme.deleteScene(sceneId);
		repository.save(gameTheme);
	}
	
	public void updateSceneIndexes(final Long themeId, final List<Scene> updateScenes) {
		final GameTheme gameTheme = repository.findOne(themeId);
		gameTheme.updateSceneIndexes(updateScenes);
		repository.save(gameTheme);
	}
	
	public List<Scene> getScenesNotAnswered(final Long studentId, final List<StudentQuestionContext> context) {
		final GameTheme theme = repository.findByStudentId(studentId);
		return theme.getScenesNotAnswered(context);
	}
}