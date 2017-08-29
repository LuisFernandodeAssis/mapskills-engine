/* @(#)GameThemeDeserializer.java 1.0 08/01/2017
 *
 * Copyright (c) 2016, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi.serializer;

import com.fasterxml.jackson.databind.JsonNode;

import br.gov.sp.fatec.mapskills.domain.theme.GameTheme;
import br.gov.sp.fatec.mapskills.restapi.wrapper.GameThemeWrapper;
/**
 * 
 * A classe {@link GameThemeDeserializer} e responsavel
 * por deserializar um tema de jogo para que seja
 * cadastrado ou atualizado.
 *
 * @author Marcelo
 * @version 1.0 08/01/2017
 */
public class GameThemeDeserializer extends DefaultJsonDeserializer<GameThemeWrapper> {

	@Override
	protected GameThemeWrapper deserialize(final JsonNode node) {
		return new GameThemeWrapper(new GameTheme(jsonUtil.getFieldTextValue(node, "name")));
	}
	
}
