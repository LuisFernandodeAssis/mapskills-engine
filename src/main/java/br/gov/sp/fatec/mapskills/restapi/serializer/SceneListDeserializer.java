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

import br.gov.sp.fatec.mapskills.domain.skill.Skill;
import br.gov.sp.fatec.mapskills.domain.skill.SkillRepository;
import br.gov.sp.fatec.mapskills.domain.theme.Alternative;
import br.gov.sp.fatec.mapskills.domain.theme.Question;
import br.gov.sp.fatec.mapskills.domain.theme.Scene;
import br.gov.sp.fatec.mapskills.restapi.wrapper.SceneListWrapper;
import lombok.AllArgsConstructor;
/**
 * 
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
	
	private final SkillRepository skillRepository;

	@Override
	protected SceneListWrapper deserialize(final JsonNode node) {
        final List<Scene> scenes = new LinkedList<>();
        node.forEach(currentNode -> {
        	scenes.add(buildScene(currentNode));
        });
		return new SceneListWrapper(scenes);
	}	
	
	private List<Alternative> buildAlternatives(final JsonNode node) {
		final List<Alternative> alternatives = new LinkedList<>();		
		node.forEach(currentNode -> {
			final Alternative alternative = new Alternative(
					getFieldLongValue(currentNode, SerializationKey.ID),
					getFieldTextValue(currentNode, SerializationKey.DESCRIPTION),
					getFieldIntegerValue(currentNode, SerializationKey.SKILL_VALUE));
			alternatives.add(alternative);
		});
		return alternatives;
	}
	
	/**
	 * Metodo responsavel por construir uma cena, a partir de um JSON.
	 */
	private Scene buildScene(final JsonNode nodeCurrent) {
		return new Scene(this.getIndexFromNode(getFieldIntegerValue(nodeCurrent, SerializationKey.INDEX)),
				getFieldTextValue(nodeCurrent, SerializationKey.TEXT),
				getFieldTextValue(get(nodeCurrent, SerializationKey.BACKGROUND), SerializationKey.FILENAME),
				buildQuestion(get(nodeCurrent, SerializationKey.QUESTION)));
	}
	
	private Question buildQuestion(final JsonNode node) {
		if(node.isNull()) {
			return null;
		}
		return new Question(buildAlternatives(get(node, SerializationKey.ALTERNATIVES)),
				getSkillFromNode(node));
	}
	
	private int getIndexFromNode(final Integer index) {
		return index == null ? -1 : index;
	}
	
	private Skill getSkillFromNode(final JsonNode node) {
		final Long id = getFieldLongValue(node, SerializationKey.SKILL_ID);
		return skillRepository.findOne(id);
	}
}