/* @(#)GameThemeListDeserializer.java 1.0 08/01/2017
 *
 * Copyright (c) 2016, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi.serializer;

import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

import br.gov.sp.fatec.mapskills.domain.theme.GameTheme;
import br.gov.sp.fatec.mapskills.restapi.wrapper.GameThemeListWrapper;
/**
 * 
 * A classe {@link GameThemeListDeserializer} e responsavel
 * por deserializar uma lista de temas de jogos para
 * que seja cadastrada ou atualizada.
 *
 * @author Marcelo
 * @version 1.0 08/01/2017
 */
public class GameThemeListDeserializer extends DefaultJsonDeserializer<GameThemeListWrapper> {

	@Override
	protected GameThemeListWrapper deserialize(final JsonNode node) {
		final List<GameTheme> gameThemes = new LinkedList<>();
        final int sizeArray = node.size();
        for(int i = 0; i < sizeArray; i++) {
        	final JsonNode nodeCurrent = node.get(i);
        	final GameTheme theme = new GameTheme(jsonUtil.getFieldTextValue(nodeCurrent, "name"));
        	theme.setId(jsonUtil.getFieldLongValue(nodeCurrent, "id"));
        	theme.setActive(jsonUtil.getFieldBooleanValue(nodeCurrent, "active"));
        	gameThemes.add(theme);
        }
        
		return new GameThemeListWrapper(gameThemes);
	}

}
