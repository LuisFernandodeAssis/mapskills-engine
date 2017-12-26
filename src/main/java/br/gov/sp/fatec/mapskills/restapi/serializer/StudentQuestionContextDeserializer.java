/* @(#)StudentQuestionContextDeserializer.java 1.0 13/01/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi.serializer;

import com.fasterxml.jackson.databind.JsonNode;

import br.gov.sp.fatec.mapskills.domain.studentquestioncontext.StudentQuestionContext;
import br.gov.sp.fatec.mapskills.restapi.wrapper.StudentQuestionContextWrapper;
/**
 * 
 * A classe {@link StudentQuestionContextDeserializer} eh responsavel
 * por deserializar um <i>POST</i> feito pelo aluno na escolha
 * de uma alternativa de uma questao.
 *
 * @author Marcelo
 * @version 1.0 22/04/2017
 */
public class StudentQuestionContextDeserializer extends AbstractJsonDeserializer<StudentQuestionContextWrapper> {

	@Override
	protected StudentQuestionContextWrapper deserialize(final JsonNode node) {
		return new StudentQuestionContextWrapper(new StudentQuestionContext(
				getFieldLongValue(node, SerializationKey.SCENE_ID),
				getFieldIntegerValue(node, SerializationKey.SCENE_INDEX),
				getFieldLongValue(node, SerializationKey.STUDENT_ID),
				getFieldLongValue(node, SerializationKey.SKILL_ID),
				getFieldIntegerValue(node, SerializationKey.SKILL_VALUE)),
				getFieldIntegerValue(node, SerializationKey.REMAINING_QUESTIONS));
	}
}