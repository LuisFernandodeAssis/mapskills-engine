/*
 * @(#)SceneListDeserializer.java 1.0 08/01/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi.serializer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

import br.gov.sp.fatec.mapskills.domain.scene.Alternative;
import br.gov.sp.fatec.mapskills.domain.scene.Question;
import br.gov.sp.fatec.mapskills.domain.scene.Scene;
import br.gov.sp.fatec.mapskills.restapi.wrapper.SceneListWrapper;
/**
 * 
 * A classe {@link SceneListDeserializer} e responsavel
 * por deserializar uma lista de cenas de um tema para
 * que seja cadastrados ou atualizados.
 *
 * @author Marcelo
 * @version 1.0 08/01/2017
 */
public class SceneListDeserializer extends DefaultJsonDeserializer<SceneListWrapper> {

	@Override
	protected SceneListWrapper deserialize(final JsonNode node) {
		final int sizeArray = node.size();
        final Collection<Scene> scenes = new ArrayList<>(sizeArray);
        for(int i = 0; i < sizeArray; i++) {
        	final JsonNode nodeCurrent = node.get(i);
        	scenes.add(buildScene(nodeCurrent));
        }
		return new SceneListWrapper(scenes);
	}
	
	private Question buildQuestion(final JsonNode node) {
		if(node.isNull()) {
			return null;
		}
		return new Question(jsonUtil.getFieldLongValue(node, "id"),
				buildAlternatives(node.get("alternatives")),
				jsonUtil.getFieldLongValue(node, "skillId"));
	}
	
	private List<Alternative> buildAlternatives(final JsonNode node) {
		final List<Alternative> alternatives = new ArrayList<>(4);
		for(int i = 0; i < 4; i++) {
			final Alternative alternative = new Alternative(
					jsonUtil.getFieldLongValue(node.get(i), "id"),
					jsonUtil.getFieldTextValue(node.get(i), "description"),
					jsonUtil.getFieldIntegerValue(node.get(i), "skillValue"));
			alternatives.add(alternative);
		}
		return alternatives;
	}
	/**
	 * Metodo responsavel por construir uma cena, a partir
	 * de uma JSON.
	 */
	private Scene buildScene(final JsonNode nodeCurrent) {
		return new Scene(
				jsonUtil.getFieldLongValue(nodeCurrent, "id"),
				this.getIndexFromNode(jsonUtil.getFieldIntegerValue(nodeCurrent, "index")),
				jsonUtil.getFieldTextValue(nodeCurrent, "text"),
				jsonUtil.getFieldTextValue(nodeCurrent.get("background"), "filename"),
				this.buildQuestion(jsonUtil.get(nodeCurrent, "question")),
				jsonUtil.getFieldLongValue(nodeCurrent, "gameThemeId"));
	}
	
	private int getIndexFromNode(final Integer index) {
		return index == null ? -1 : index;
	}

}
