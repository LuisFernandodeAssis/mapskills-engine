/* @(#)AnswerDeserializer.java 1.0 13/01/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi.serializer;

import com.fasterxml.jackson.databind.JsonNode;

import br.gov.sp.fatec.mapskills.domain.answerevent.AnswerEvent;
import br.gov.sp.fatec.mapskills.restapi.wrapper.AnswerWrapper;
/**
 * 
 * A classe {@link AnswerDeserializer} eh responsavel
 * por deserializar um <i>POST</i> feito pelo aluno na escolha
 * de uma alternativa de uma questao.
 *
 * @author Marcelo
 * @version 1.0 22/04/2017
 */
public class AnswerDeserializer extends DefaultJsonDeserializer<AnswerWrapper> {

	@Override
	protected AnswerWrapper deserialize(final JsonNode node) {
		return new AnswerWrapper(AnswerEvent.builder()
				.sceneId(jsonUtil.getFieldLongValue(node, "sceneId"))
				.sceneIndex(jsonUtil.getFieldIntegerValue(node, "sceneIndex"))
				.studentId(jsonUtil.getFieldLongValue(node, "studentId"))
				.skillId(jsonUtil.getFieldLongValue(node, "skillId"))
				.skillValue(jsonUtil.getFieldIntegerValue(node, "skillValue"))
				.build());
	}

}
