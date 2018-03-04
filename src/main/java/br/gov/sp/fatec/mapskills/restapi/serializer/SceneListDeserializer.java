/*
 * @(#)SceneListDeserializer.java 1.0 08/01/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.restapi.serializer;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;

import br.gov.sp.fatec.mapskills.domain.theme.Scene;
import br.gov.sp.fatec.mapskills.restapi.wrapper.SceneListWrapper;
import lombok.AllArgsConstructor;

/** 
 * A classe {@link SceneListDeserializer} e responsavel
 * por deserializar uma lista de cenas de um tema para
 * que seja cadastrados ou atualizados.
 *
 * @author Marcelo
 * @version 1.0 08/01/2017
 */
@Component
@AllArgsConstructor
public class SceneListDeserializer extends AbstractJsonDeserializer<SceneListWrapper> {

	private final SceneDeserializer sceneDeserializer;

	@Override
	protected SceneListWrapper deserialize(final JsonNode node) {
        final List<Scene> scenes = new LinkedList<>();
        node.forEach(currentNode -> scenes.add(buildScene(currentNode)));
		return new SceneListWrapper(scenes);
	}	
	
	/**
	 * Metodo responsavel por construir uma cena, a partir de um JSON.
	 */
	private Scene buildScene(final JsonNode nodeCurrent) {
		return new Scene(getFieldLongValue(nodeCurrent, SerializationKey.ID),
				this.getIndexFromNode(getFieldIntegerValue(nodeCurrent, SerializationKey.INDEX)),
				getFieldTextValue(nodeCurrent, SerializationKey.TEXT),
				sceneDeserializer.getFilename(nodeCurrent),
				sceneDeserializer.buildQuestion(get(nodeCurrent, SerializationKey.QUESTION)));
	}
	
	private int getIndexFromNode(final Integer index) {
		return index == null ? -1 : index;
	}
}