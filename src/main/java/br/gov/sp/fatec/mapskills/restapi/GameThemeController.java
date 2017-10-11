/*
 * @(#)GameThemeController.java 1.0 1 02/09/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.restapi;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.gov.sp.fatec.mapskills.application.GameThemeApplicationServices;
import br.gov.sp.fatec.mapskills.domain.theme.GameTheme;
import br.gov.sp.fatec.mapskills.restapi.wrapper.GameThemeListWrapper;
import br.gov.sp.fatec.mapskills.restapi.wrapper.GameThemeWrapper;
import br.gov.sp.fatec.mapskills.restapi.wrapper.SceneListWrapper;
import br.gov.sp.fatec.mapskills.restapi.wrapper.SceneWrapper;
import lombok.AllArgsConstructor;

/**
 * A classe {@link GameThemeController}
 *
 * @author Marcelo
 * @version 1.0 02/09/2017
 */
@RestController
@AllArgsConstructor
public class GameThemeController {
	
	//public static final String BASE_PATH = "/admin";
	
	private final GameThemeApplicationServices applicationServices;
	
	/**
	 * 
	 * Expoe endpoint para que seja realizado o cadastro de um novo tema na aplicacao.
	 */
	@PostMapping(value = "/game/theme")
	public GameThemeWrapper createTheme(@RequestBody final GameThemeWrapper themeWrapper) {
		final GameTheme themeCreated = applicationServices.createGameTheme(themeWrapper.getGameTheme());
		return new GameThemeWrapper(themeCreated);
	}
	
	/***
	 * 
	 * Expoe endpoint para que retorne todos temas cadastrados na aplicacao.
	 */
	@GetMapping(value = "/game/themes")
	public GameThemeListWrapper getAllThemes() {
		final List<GameTheme> themes = applicationServices.getAllGameThemes();
		return new GameThemeListWrapper(themes); 
	}
	
	/**
	 * 
	 * Expoe endpoint para que seja criada uma nova cena para um tema do jogo na aplicacao.
	 */
	@PostMapping(value = "/game/{id}/scene")
	public void createScene(@PathVariable("id") final Long gameThemeId, @RequestBody final SceneWrapper sceneWrapper) {
		
		applicationServices.createScene(gameThemeId, sceneWrapper);
		
	}
	
	/**
	 * 
	 * Expoe endpoint para que retorne todas as cenas de um tema de jogo contido na aplicacao.
	 */
	@GetMapping(value = "/game/theme/{themeId}")
	public SceneListWrapper getAllScenesByThemeId(@PathVariable("themeId") final Long themeId) {

		return new SceneListWrapper(applicationServices.getAllScenesByThemeId(themeId));

	}
	
	/**
	 * 
	 * Expoe endpoint para
	 */
	@DeleteMapping(value = "/game/{themeId}/scene/{sceneId}/question")//alterar end point no front
	public void deleteQuestion(@PathVariable("themeId") final Long themeId,
			@PathVariable("sceneId") final Long sceneId) {
		
		applicationServices.deleteQuestion(themeId, sceneId);

	}
	
	/**
	 * 
	 * Expoe endpoint para
	 */
	@DeleteMapping(value = "/game/{themeId}/scene/{sceneId}")//alterar end point no front
	public void deleteScene(@PathVariable("themeId") final Long themeId,
			@PathVariable("sceneId") final Long sceneId) {
		
		applicationServices.deleteScene(themeId, sceneId);
		
	}
	
	/**
	 * 
	 * Expoe endpoint para realiza atualizacao das ordens das cenas de um tema.
	 */
	@PutMapping(value = "/game/{themeId}/scenes")//alterar end point no front
	public void updateIndexScenes(@PathVariable("themeId") final Long themeId,
			@RequestBody final SceneListWrapper sceneListWrapper) {
		applicationServices.updateSceneIndexes(themeId, sceneListWrapper.getScenes());
	}

}