/*
 * @(#)SceneDeserializer.java 1.0 01/11/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi.serializer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;

import br.gov.sp.fatec.mapskills.domain.skill.Skill;
import br.gov.sp.fatec.mapskills.domain.skill.SkillRepository;
import br.gov.sp.fatec.mapskills.domain.theme.Alternative;
import br.gov.sp.fatec.mapskills.domain.theme.Question;
import br.gov.sp.fatec.mapskills.domain.theme.Scene;
import br.gov.sp.fatec.mapskills.restapi.wrapper.SceneWrapper;
import lombok.AllArgsConstructor;
/**
 * 
 * A classe {@link SceneDeserializer} e responsavel
 * por deserializar um <i>POST</i> de uma cena
 * para que seja cadastrada ou atualizada.
 *
 * @author Marcelo
 * @version 1.0 01/11/2016
 */
@Component
@AllArgsConstructor
public class SceneDeserializer extends AbstractJsonDeserializer<SceneWrapper> {

	private final SkillRepository skillRepository;
	
	@Override
	protected SceneWrapper deserialize(final JsonNode node) {
        final String fileImageBase64 = getFileBase64(node);
        final String filename = getFilename(node);
        		
        final Question question = buildQuestion(get(node, SerializationKey.QUESTION));

        final Scene scene = new Scene(getFieldIntegerValue(node, SerializationKey.INDEX),
        		getFieldTextValue(node, SerializationKey.TEXT), filename, question);
        
		return new SceneWrapper(scene, fileImageBase64, filename);
	}
	
	public Question buildQuestion(final JsonNode node) {
		if(isNull(node)) {
			return null;
		}
		final List<Alternative> alternatives = buildAlternatives(get(node, SerializationKey.ALTERNATIVES));
		final Question question = new Question(alternatives, getSkillFromNode(node));
		return question;
	}
	
	/**
	 * Metodo que recupera o nome do arquivo que representa
	 * a imagem relacionada a cena, ignorando o endereço da url
	 * presente no objeto.
	 * 
	 * @param node
	 * 		Objeto Json.
	 * @return
	 * 		O nome do arquivo.
	 */
	public String getFilename(final JsonNode node) {
		final JsonNode backgroundNode = get(node, SerializationKey.BACKGROUND);
		if(has(backgroundNode, SerializationKey.FILENAME)) {
			final String sourceFile = getFieldTextValue(backgroundNode, SerializationKey.FILENAME);
			final int sliceIndex = sourceFile.lastIndexOf('/');
			return sourceFile.substring(sliceIndex + 1);
		}		
		return null;
	}
	
	private List<Alternative> buildAlternatives(final JsonNode node) {
		final List<Alternative> alternatives = new ArrayList<>(4);
		for (int index = 0; index < 4; index++) {
			final Alternative alternative = new Alternative(
					getFieldLongValue(node.get(index), SerializationKey.ID),
					getFieldTextValue(node.get(index), SerializationKey.DESCRIPTION),
					getFieldIntegerValue(node.get(index), SerializationKey.SKILL_VALUE));

			alternatives.add(alternative);
		}
		return alternatives;
	}
	
	private String getFileBase64(final JsonNode node) {
		final JsonNode backgroundNode = get(node, SerializationKey.BACKGROUND);
		if(has(backgroundNode, SerializationKey.BASE_64)) {
			return getFieldTextValue(backgroundNode, SerializationKey.BASE_64);
		}		
		return null;
	}
	
	private Skill getSkillFromNode(final JsonNode node) {
		final Long id = getFieldLongValue(node, SerializationKey.SKILL_ID);
		return skillRepository.findOne(id);
	}	
}