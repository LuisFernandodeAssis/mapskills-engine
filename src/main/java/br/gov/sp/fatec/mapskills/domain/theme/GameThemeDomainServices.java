/*
 * @(#)GameThemeDomainServices.java 1.0 1 02/09/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.domain.theme;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import br.gov.sp.fatec.mapskills.domain.event.DomainEvent;
import br.gov.sp.fatec.mapskills.domain.event.DomainEventsNotifier;
import br.gov.sp.fatec.mapskills.domain.studentquestioncontext.StudentQuestionContext;
import br.gov.sp.fatec.mapskills.domain.studentquestioncontext.StudentQuestionContextRepository;
import br.gov.sp.fatec.mapskills.restapi.wrapper.FileContextWrapper;
import br.gov.sp.fatec.mapskills.restapi.wrapper.SceneWrapper;
import lombok.AllArgsConstructor;

/**
 * A classe {@link GameThemeDomainServices} apresenta os servicos da camada de
 * dominio para {@link GameTheme}
 *
 * @author Marcelo
 * @version 1.0 02/09/2017
 */
@Component
@AllArgsConstructor
public class GameThemeDomainServices {
		
	private final GameThemeRepository repository;
	private final StudentQuestionContextRepository answerRepository;
	private final DomainEventsNotifier notifier;
	
	public GameTheme createGameTheme(final GameTheme newGameTheme) {
		return repository.save(newGameTheme);
	}
	
	public Scene createScene(final Long gameThemeId, final SceneWrapper sceneWrapper) {
		final GameTheme theme = repository.findOne(gameThemeId);
		final Scene newScene = sceneWrapper.getScene();
		theme.addScene(newScene);
		repository.save(theme);
		notifier.notifyListeners(new SceneWasCreatedEvent(new FileContextWrapper(sceneWrapper.getBase64(), newScene.getImageName())));
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
		final DomainEvent event = gameTheme.deleteScene(sceneId);
		repository.save(gameTheme);
		notifier.notifyListeners(event);
	}
	
	public void updateSceneIndexes(final Long themeId, final List<Scene> updateScenes) {
		final GameTheme gameTheme = repository.findOne(themeId);
		gameTheme.updateSceneIndexes(updateScenes);
		repository.save(gameTheme);
	}
	
	public List<Scene> getScenesNotAnswered(final Long studentId) {
		final Optional<GameTheme> theme = repository.findByStudentId(studentId);
		if (theme.isPresent()) {
			final Optional<StudentQuestionContext> lastContext = answerRepository.findFirstByStudentIdOrderBySceneIndexDesc(studentId);
			return theme.get().getScenesNotAnswered(lastContext);
		}
		return Collections.emptyList();
	}

	public void updateGameThemeStatus(final Long themeId, final boolean status) {
		final GameTheme gameTheme = repository.findOne(themeId);
		gameTheme.updateStatus(status);
		repository.save(gameTheme);
	}

	public void updateScene(final Long themeId, final Long sceneId, final SceneWrapper sceneWrapper) {
		final GameTheme gameTheme = repository.findOne(themeId);
		final DomainEvent event = gameTheme.updateScene(sceneId, sceneWrapper);
		repository.save(gameTheme);
		notifier.notifyListeners(event);
	}
}