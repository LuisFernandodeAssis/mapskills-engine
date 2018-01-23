/*
 * @(#)QuestionListSerializer.java 1.0 01/11/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi.serializer;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import br.gov.sp.fatec.mapskills.domain.theme.Alternative;
import br.gov.sp.fatec.mapskills.domain.theme.Question;
import br.gov.sp.fatec.mapskills.domain.theme.Scene;

/**
 * 
 * A classe {@link SceneSerializer} eh responsavel por 
 * serializar todo um contexto de cenas que o jogo possui.
 *
 * @author Marcelo
 * @version 1.0 01/11/2016
 */
@Component
public class SceneSerializer extends AbstractSerializer<Scene> {
	
	@Value("${rest.filemanager.get.url}")
	private String url;
	
	@Override
	public void serialize(final Scene scene, final Enum<?> arg1, final JsonWriter writer) throws IOException {
		writer.writeNumberField(SerializationKey.ID, scene.getId());
		writer.writeNumberField(SerializationKey.INDEX, scene.getIndex());
		writer.writeStringField(SerializationKey.TEXT, scene.getText());
		writer.writeObjectFieldStart(SerializationKey.BACKGROUND);
		writer.writeStringField(SerializationKey.FILENAME, url.replace("{filename}", scene.getImageName()));
		writer.writeEndObject();
		this.questionGenerator(scene.getQuestion(), writer);
	}
	
	/**
	 * Metodo responsavel por serializar a questão da cena.
	 */
	private void questionGenerator(final Question question, final JsonWriter writer) throws IOException {
		if (ObjectUtils.isEmpty(question)) {
			writer.writeNullField(SerializationKey.QUESTION);
			return;
		}
		writer.writeObjectFieldStart(SerializationKey.QUESTION);
		writer.writeNumberField(SerializationKey.ID, question.getId());
		writer.writeNumberField(SerializationKey.SKILL_ID, question.getSkillId());
		writer.writeArrayFieldStart(SerializationKey.ALTERNATIVES);
		for(final Alternative alternative : question.getAlternatives()) {
			writer.writeStartObject();
			alternativeGenerator(alternative, writer);
			writer.writeEndObject();
		}
		writer.writeEndArray();
		writer.writeEndObject();
	}
	
	/**
	 * Metodo responsavel por serializar as alternativas da questao.
	 */
	private void alternativeGenerator(final Alternative alternative, final JsonWriter writer) throws IOException {
		writer.writeNumberField(SerializationKey.ID, alternative.getId());
		writer.writeStringField(SerializationKey.DESCRIPTION, alternative.getDescription());
		writer.writeNumberField(SerializationKey.SKILL_VALUE, alternative.getSkillValue());
	}	
}