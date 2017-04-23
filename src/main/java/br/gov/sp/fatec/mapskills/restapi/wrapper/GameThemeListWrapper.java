/*
 * @(#)GameThemeListWrapper.java 1.0 24/12/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi.wrapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.gov.sp.fatec.mapskills.domain.theme.GameTheme;
import br.gov.sp.fatec.mapskills.restapi.serializer.GameThemeListDeserializer;
import br.gov.sp.fatec.mapskills.restapi.serializer.GameThemeListSerializer;
/**
 * 
 * A classe {@link GameThemeListWrapper} contem todos temas criados
 * pelo perfil administrador, para que sejam serializados ou deserializados.
 *
 * @author Marcelo
 * @version 1.0 24/12/2016
 */
@JsonSerialize(using = GameThemeListSerializer.class)
@JsonDeserialize(using = GameThemeListDeserializer.class)
public class GameThemeListWrapper {

	private final Collection<GameTheme> gameThemes = new ArrayList<>();
	
	public GameThemeListWrapper(final Collection<GameTheme> gameThemes) {
		this.gameThemes.addAll(gameThemes);
	}
	
	public Collection<GameTheme> getGameThemes() {
		return Collections.unmodifiableCollection(gameThemes);
	}
}
