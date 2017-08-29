/*
 * @(#)SceneDeserializer.java 1.0 01/11/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi.serializer;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

import br.gov.sp.fatec.mapskills.domain.scene.Alternative;
import br.gov.sp.fatec.mapskills.domain.scene.Question;
import br.gov.sp.fatec.mapskills.domain.scene.Scene;
import br.gov.sp.fatec.mapskills.restapi.wrapper.SceneWrapper;
/**
 * 
 * A classe {@link SceneDeserializer} e responsavel
 * por deserializar um <i>POST</i> de uma cena
 * para que seja cadastrada ou atualizada.
 *
 * @author Marcelo
 * @version 1.0 01/11/2016
 */
public class SceneDeserializer extends DefaultJsonDeserializer<SceneWrapper> {
	
	private static final String IP_SERVER = "http://localhost:8585/mapskills/";
	private static final String BACKGROUND = "background";
	
	@Override
	protected SceneWrapper deserialize(final JsonNode node) {
		final String[] background = verifyBackground(node);    
        final String fileImageBase64 = background[0];
        final String filename = background[1];
        		
        final Question question = this.buildQuestion(node.get("question"));

        final Scene scene = new Scene(null, null, jsonUtil.getFieldTextValue(node, "text"), IP_SERVER + "images/" + filename,
        		question, jsonUtil.getFieldLongValue(node, "gameThemeId"));

        this.setIdAndIndex(node, scene);
        
		return new SceneWrapper(scene, fileImageBase64, filename);
	}
	
	private Question buildQuestion(final JsonNode node) {
		if(node == null || node.isNull()) {
			return null;
		}
		final List<Alternative> alternatives = buildAlternatives(node.get("alternatives"));
		final Question question = new Question(null, alternatives, this.getSkillIdFromNode(node));
		question.setId(jsonUtil.getFieldLongValue(node, "id"));
		return question;
	}
	
	private List<Alternative> buildAlternatives(final JsonNode node) {
		final List<Alternative> alternatives = new ArrayList<>(4);
		for(int index = 0; index < 4; index++ ) {
			final Alternative alternative = new Alternative(
					jsonUtil.getFieldLongValue(node.get(index), "id"),
					jsonUtil.getFieldTextValue(node.get(index), "description"),
					jsonUtil.getFieldIntegerValue(node.get(index), "skillValue"));

			alternatives.add(alternative);
		}
		return alternatives;
	}
	
	private String[] verifyBackground(final JsonNode node) {
		final String[] background = new String[2];
		if(!node.has(BACKGROUND)) {
			return new String[2];
		}
		if(jsonUtil.has(node.get(BACKGROUND), "base64")) {
			background[0] = jsonUtil.getFieldTextValue(node.get(BACKGROUND), "base64");
        }
        if(jsonUtil.has(node.get(BACKGROUND), "filename")) {
        	final String filename = jsonUtil.getFieldTextValue(node.get(BACKGROUND), "filename");
        	final int lastIndex = filename.lastIndexOf('/');
        	background[1] = filename.substring(lastIndex + 1);
        }
        return background;
	}
	
	private long getSkillIdFromNode(final JsonNode node) {
		return jsonUtil.getFieldLongValue(node, "skillId");
	}
	
	private void setIdAndIndex(final JsonNode node, final Scene scene) {
		scene.setId(jsonUtil.getFieldLongValue(node, "id"));
		scene.setIndex(jsonUtil.getFieldIntegerValue(node, "index"));
	}
	
}
