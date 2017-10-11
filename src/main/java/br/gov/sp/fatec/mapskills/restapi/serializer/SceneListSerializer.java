/*
 * @(#)QuestionListSerializer.java 1.0 01/11/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;

import br.gov.sp.fatec.mapskills.domain.theme.Alternative;
import br.gov.sp.fatec.mapskills.domain.theme.Question;
import br.gov.sp.fatec.mapskills.domain.theme.Scene;
import br.gov.sp.fatec.mapskills.restapi.wrapper.SceneListWrapper;

/**
 * 
 * A classe {@link SceneListSerializer} eh responsavel por 
 * serializar todo um contexto de cenas que o jogo possui.
 *
 * @author Marcelo
 * @version 1.0 01/11/2016
 */
public class SceneListSerializer extends AbstractJsonSerializer<SceneListWrapper> {

	@Override
	public void serialize(final SceneListWrapper scenesList, final JsonGenerator generator)	throws IOException {

		generator.writeStartArray();
		for(final Scene scene : scenesList.getScenes()) {
			generator.writeStartObject();
			this.sceneGenerator(generator, scene);
			generator.writeEndObject();
		}
		generator.writeEndArray();
		
	}
	
	/**
	 * Metodo responsavel por serializar a cena do jogo
	 * 
	 * @param generator
	 * @param scene
	 * @throws IOException
	 */
	private void sceneGenerator(final JsonGenerator generator, final Scene scene) throws IOException {
		generator.writeNumberField("id", scene.getId());
		generator.writeNumberField("index", scene.getIndex());
		generator.writeStringField("text", scene.getText());
		generator.writeObjectFieldStart("background");
		generator.writeStringField("filename", scene.getUrlBackground());
		generator.writeEndObject();
		if(scene.getQuestion() != null) {
			this.questionGenerator(generator, scene.getQuestion());
		} else {
			generator.writeNullField("question");
		}
	}
	
	/**
	 * Metodo responsavel por serializar a questão da cena.
	 * 
	 * @param generator
	 * @param question
	 * @throws IOException
	 */
	private void questionGenerator(final JsonGenerator generator, final Question question) throws IOException {
		generator.writeObjectFieldStart("question");
		generator.writeNumberField("id", question.getId());
		generator.writeNumberField("skillId", question.getSkillId());
		generator.writeArrayFieldStart("alternatives");
		for(final Alternative alternative : question.getAlternatives()) {
			generator.writeStartObject();
			alternativeGenerator(generator, alternative);
			generator.writeEndObject();
		}
		generator.writeEndArray();
		generator.writeEndObject();
	}
	
	/**
	 * Metodo responsavel por serializar as alternativas da questao.
	 * 
	 * @param generator
	 * @param alternatives
	 * @throws IOException
	 */
	private void alternativeGenerator(final JsonGenerator generator, final Alternative alternative) throws IOException {
			generator.writeNumberField("id", alternative.getId());
			generator.writeStringField("description", alternative.getDescription());
			generator.writeNumberField("skillValue", alternative.getSkillValue());
	}
	
}