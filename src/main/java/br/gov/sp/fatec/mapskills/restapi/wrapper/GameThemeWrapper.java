/*
 * @(#)GameThemeWrapper.java 1.0 24/12/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.restapi.wrapper;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import br.gov.sp.fatec.mapskills.domain.theme.GameTheme;
import br.gov.sp.fatec.mapskills.restapi.serializer.GameThemeDeserializer;

/**
 * A classe {@link GameThemeWrapper} encapsula um Tema de Jogo
 * apos a deserializacao para que seja cadastrado ou atualizado.
 *
 * @author Marcelo
 * @version 1.0 24/12/2016
 */
@JsonDeserialize(using = GameThemeDeserializer.class)
public class GameThemeWrapper {

	private final GameTheme gameTheme;
	
	public GameThemeWrapper(final GameTheme gameTheme) {
		this.gameTheme = gameTheme;
	}
	
	public GameTheme getGameTheme() {
		return gameTheme;
	}
}