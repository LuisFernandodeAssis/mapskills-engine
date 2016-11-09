/*
 * @(#)GameThemeRepository.java 1.0 04/11/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved. Fatec Jessen Vidal
 * proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.domain.theme;

import org.springframework.data.repository.CrudRepository;

public interface GameThemeRepository extends CrudRepository<GameTheme, Integer> {
	
	public GameTheme findById(final int id);

}