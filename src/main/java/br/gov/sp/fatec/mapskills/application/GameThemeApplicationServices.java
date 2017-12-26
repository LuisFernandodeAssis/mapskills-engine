/*
 * @(#)GameThemeApplicationServices.java 1.0 1 02/09/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.application;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import br.gov.sp.fatec.mapskills.domain.theme.GameTheme;
import br.gov.sp.fatec.mapskills.domain.theme.GameThemeDomainServices;
import br.gov.sp.fatec.mapskills.domain.theme.Scene;
import br.gov.sp.fatec.mapskills.restapi.wrapper.SceneWrapper;
import lombok.AllArgsConstructor;

/**
 * A classe {@link GameThemeApplicationServices}
 *
 * @author Marcelo
 * @version 1.0 02/09/2017
 */
@ApplicationServices
@AllArgsConstructor
public class GameThemeApplicationServices {
	
	private final GameThemeDomainServices domainServices;
	
	@Transactional
	@PreAuthorize("isFullyAuthenticated()")
	public GameTheme createGameTheme(final GameTheme newTheme) {
		return domainServices.createGameTheme(newTheme);
	}
	
	@Transactional
	@PreAuthorize("isFullyAuthenticated()")
	public Scene createScene(final Long gameThemeId, final SceneWrapper sceneWrapper) {
		return domainServices.createScene(gameThemeId, sceneWrapper);
	}
	
	@PreAuthorize("isFullyAuthenticated()")
	public List<GameTheme> getAllGameThemes(final boolean onlyActives) {
		return domainServices.getAllGameThemes(onlyActives);
	}
	
	@PreAuthorize("isFullyAuthenticated()")
	public List<Scene> getAllScenesByThemeId(final Long themeId) {
		return domainServices.getAllScenesByThemeId(themeId);
	}
	
	@Transactional
	@PreAuthorize("isFullyAuthenticated()")
	public void deleteQuestion(final Long themeId, final Long sceneId) {
		domainServices.deleteQuestion(themeId, sceneId);
	}
	
	@Transactional
	@PreAuthorize("isFullyAuthenticated()")
	public void deleteScene(final Long themeId, final Long sceneId) {
		domainServices.deleteScene(themeId, sceneId);
	}
	
	@Transactional
	@PreAuthorize("isFullyAuthenticated()")
	public void updateSceneIndexes(final Long themeId, final List<Scene> updateScenes) {
		domainServices.updateSceneIndexes(themeId, updateScenes);
	}

	@Transactional
	@PreAuthorize("isFullyAuthenticated()")
	public void updateGameThemeStatus(final Long themeId, final boolean status) {
		domainServices.updateGameThemeStatus(themeId, status);
	}

	@Transactional
	@PreAuthorize("isFullyAuthenticated()")
	public void updateScene(final Long themeId, final Long sceneId, final SceneWrapper sceneWrapper) {
		domainServices.updateScene(themeId, sceneId, sceneWrapper);
	}
}