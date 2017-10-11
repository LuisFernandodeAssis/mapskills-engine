/*
 * @(#)GameThemeService.java 1.0 04/11/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.domain.theme;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
/**
 * 
 * A classe {@link GameThemeService} contem todas as regras de negocio
 * que diz respeito a classe <code>GameTheme</code>.
 *
 * @author Marcelo
 * @version 1.0 04/11/2016
 */
@Service
@AllArgsConstructor
public class GameThemeService {
	
	private final GameThemeRepository themeRepo;
	private final SceneRepository sceneRepo;
	
	/**
	 * Realiza busca de um tema por seu id.
	 * 
	 * @param id
	 */
	public GameTheme findById(final Long id) {
		return themeRepo.findOne(id);
	}
	
	/**
	 * Realiza persistencia de um tema caso nao exista.
	 * 
	 * @param theme
	 */
	public GameTheme save(final GameTheme theme) {
		return themeRepo.save(theme);			
	}
	
	/**
	 * Realiza persistencia de uma lista de temas
	 * verificando se ja estão cadastrados.
	 * 
	 * @param themes
	 */
	public Collection<GameTheme> save(final Collection<GameTheme> themes) {
		final Collection<GameTheme> themesSaved = new ArrayList<>(themes.size());
		for(final GameTheme theme : themes) {
			themesSaved.add(this.save(theme));	
		}
		return themesSaved;
	}
	
	/**
	 * Metodo que retorna todos temas cadastrados na aplicacao.
	 * 
	 * @return lista
	 */
	public Collection<GameTheme> findAllThemes() {
		final List<GameTheme> themes = new ArrayList<>();
		for(final GameTheme theme : themeRepo.findAll()) {
			themes.add(theme);
		}
		return themes;
	}
	
	public Collection<GameTheme> findAllThemesActivated() {
		return themeRepo.findAllByActive(true);
	}
	
}