/*
 * @(#)GameThemeService.java 1.0 04/11/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.domain.theme;

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
	 * Metodo que retorna todos temas cadastrados na aplicacao.
	 * 
	 * @return lista
	 */
	public List<GameTheme> findAllThemes() {
		return themeRepo.findAll();
	}
}